# 我逆向小米运动健康：步行距离按步数比例分，阶段热量切成十份

运动健康 App 上的数字经常精确到分钟，卡路里甚至精确到个位。看久了，很容易把它们当成手环刚刚测出来的事实。

我翻了小米运动健康 Android 3.59.1 的代码，发现有些分钟数据不是独立测量值，而是把日总量或阶段总量按规则拆出来的。

先交代边界：以下是 APK 静态反编译结果。我们还没有闭合“手环 8 Pro 的传感器数据一定经过这些解析器”这条调用链，所以不能把这些发现直接说成 8 Pro 固件里的计步、心率或热量算法。代码能证明的是 App 收到某些数据后怎样整理；传感器最初怎样产生这些数据，目前仍未知。

## 一、步行距离和步行热量：按步数比例分摊，再处理整数余数

`HmDailyDataParse.parseStep` 读入日总步数、日距离、`stepCal`，再解码分钟步数。设 `S` 是日总步数，`D` 是日距离字段，`C` 是日步行热量字段，`sᵢ` 是第 i 分钟步数。代码先按比例分摊，再转成整数：

```text
Dᵢ_base = int(D × sᵢ / S)
Cᵢ_base = int(C × sᵢ / S)
```

反编译代码的核心片段是：

```java
float ratio = intervalSteps / daySteps;
int intervalDistance = (int) (dayDistance * ratio);
int intervalCalories = (int) (ratio * dayStepCal);
```

完整方法见 [`HmDailyDataParse.parseStep`](work/jadx/evidence/HmDailyDataParse_parseStep.java)。

每分钟结果都被转成整数，分完以后可能还剩余数。代码会计算：

```text
R_D = D - ΣDᵢ_base
R_C = C - ΣCᵢ_base
```

随后，代码先用全部时间槽数算出余数的商和模。遍历分钟时，只在有步数的分钟上加商；正的模从前往后逐个补给这些活动分钟。下面是把 JADX 混淆变量名换成语义名后的简写，原始变量对应关系见完整方法：

```java
int distanceRemainder = dayDistance - allocatedDistance;
int distancePerSlot = distanceRemainder / slotCount;
int distanceExtra = distanceRemainder % slotCount;
if (minuteSteps != 0) {
    minuteDistance += distancePerSlot;
    if (distanceExtra > 0) {
        minuteDistance++;
        distanceExtra--;
    }
}
```

举个只说明取整的例子：日总步数 `10,000`，距离字段 `5,999`，`stepCal=299`，某分钟走了 `50` 步。比例是 `50 / 10,000`，这一分钟先分到：

```text
距离：int(5,999 × 50 / 10,000) = 29
热量：int(299 × 50 / 10,000) = 1
```

后面还有余数修正。这里的单位只沿用代码字段；这段源码没有定义物理单位，不能仅凭它断言距离一定是米、热量一定是千卡。

**静态观察：**分钟距离和 `stepCal` 是从日总量按步数比例分配出来的。

**推断：**如果页面用这些分钟项画曲线，距离和步行热量曲线会跟着步数分布走；整数余数还会让部分分钟多出一个内部整数单位。

**未知：**手环 8 Pro 的数据是否经过这个解析器、页面具体展示哪组分钟记录，目前没有闭合。

这不等于日总距离一定不准。更值得追问的是：**如果分钟值是按步数比例推出来的，App 有没有让用户知道这是分摊结果？** 走路时步幅和步速会变，这段代码没有体现这些差异。分钟图画得再细，也不会因此变成逐分钟实测。

## 二、阶段热量：10 分钟的数值，均匀拆成 10 份

`HmCaloriesParse.parse` 对每个阶段的热量做除法和取余。设阶段热量为 `C_stage`：

```text
q = C_stage / 10
r = C_stage % 10

Cₖ = q + 1，当 k < r
Cₖ = q，    当 k ≥ r
```

只对正值创建 `CaloriesItem`。下面按原逻辑整理成伪代码，实际实现见链接中的反编译方法：

```java
int perMinute = stageCalories / 10;
int remainder = stageCalories % 10;

for (int minute = 0; minute < 10; minute++) {
    int value = perMinute;
    if (remainder > 0) {
        remainder--;
        value++;
    }
    if (value > 0) {
        record(new CaloriesItem(time, value));
    }
}
```

完整方法见 [`HmCaloriesParse.parse`](work/jadx/evidence/HmCaloriesParse.java)。例如阶段热量是 `7`，结果就是 `1,1,1,1,1,1,1,0,0,0`。总量仍是 7，但这不是设备逐分钟测到的代谢变化，只是把阶段总量切成了 10 份。

**静态观察：**这个方法负责分时，不负责计算热量。

**推断：**如果用分钟图判断哪一分钟“燃脂最多”，就把一种数据整理方式误当成了身体代谢的分钟级变化。

## 三、运动报告热量：解析器拿到多少，就记录多少

`HmSportReportParse.parse` 把 JSON 的 `calorie` 字段放进 `SportBasicReport.calories`，再交给记录器；它没有在这个方法里重新计算运动热量：

```java
sportBasicReport.setCalories(jsonObject.optInt("calorie"));
recordSportReport(sid, sportBasicReport);
```

证据见 [`HmSportReportParse.parse`](work/jadx/evidence/HmSportReportParse_parse.java)。这只能证明“这个解析器收到什么就记录什么”，不能证明这个数是谁算的，也不能证明它一定在手环端算。

小米对 Mi Fitness 的公开说明称，运动热量估算会参考运动时长、强度、类型、心率以及年龄、体重、性别等信息。[小米对 Mi Fitness 卡路里的说明](https://www.mi.com/global/support/faq/details/KA-492770/) 公开说明提供了输入因素，却没有给出完整公式、参数系数、误差范围和不同活动下的验证结果。

其他消费级腕戴设备的研究发现，能量消耗估值与间接测热法相比可能有明显差异；这不是对小米设备的测试，不能把具体误差套到小米身上，但足以提醒我们：腕上热量数字不能自动等同于精确能量账本。[消费级运动设备能量消耗估值研究](https://pmc.ncbi.nlm.nih.gov/articles/PMC5325221/)

**值得追问的是：用户看到“消耗 400 千卡”，有没有足够信息判断这 400 是怎样算出来、可能偏多少？** 看不到公式和误差范围时，它适合当趋势提示，不适合直接换算成“晚饭可以多吃多少”。

## 四、日常心率：读取每分钟数据点，不等于连续心电记录

`HmDailyDataParse.parseDailyHr` 从编码数据中按分钟读取一个心率字段。代码按时间间隔取字节偏移 `+3`，只保留 `1 ≤ bpm < 255` 的值，再交给 `recordDailyHr`：

```java
int bpm = byteToInt(data[(length * minute) + 3]);
if (1 <= bpm && bpm < 255) {
    items.add(new HrItem(timestamp, bpm));
}
```

证据见 [`HmDailyDataParse.parseDailyHr`](work/jadx/evidence/HmDailyDataParse_parseDailyHr.java)。这段代码展示了取值和有效范围过滤，没有显示心率如何从 PPG 光学信号中算出来，也没有显示这一步做了怎样的平滑或去噪。

小米官方说明称，8 Pro 开启智能监测后，活动时会提高心率监测频率；这仍是按一定频率得到的读数，不等于连续心电图。[8 Pro 产品说明](https://www.mi.com/uk/product/xiaomi-smart-band-8-pro/) 腕式光学心率会受手臂运动、佩戴松紧和皮肤接触影响；已有研究讨论过腕式 PPG 的运动伪影，但这些通用研究不能替代对小米 8 Pro 的独立测试。[腕式光学心率误差研究](https://pmc.ncbi.nlm.nih.gov/articles/PMC7010823/)

日常真正要小心的是过度解读：看到一次尖峰，不足以断定身体异常；没看到一个短暂峰值，也不足以证明那段时间心率一直正常。对普通人来说，多天趋势和运动时的体感，比某个单点数字更有参考价值。

## 五、睡眠分期：传感器能提供线索，不等于测到了脑电睡眠结构

小米公开说明，8 Pro 的睡眠高精度监测会结合加速度计（ACC）和 PPG 数据来分析睡眠状态、分期和评分。[8 Pro FAQ](https://www.mi.com/uk/support/faq/details/KA-64019/)

但目前提取到的 APK 代码没有闭合出 8 Pro 的具体睡眠分期公式，也没有找到该型号与多导睡眠监测（PSG）对照的验证结果。其他消费级睡眠设备与 PSG 对照的研究中，不同设备和睡眠阶段的表现并不一致；这不能直接证明小米不准，只能说明睡眠阶段数字需要针对具体设备验证。[消费级睡眠追踪设备与 PSG 对照研究](https://pmc.ncbi.nlm.nih.gov/articles/PMC8120339/)

生活里的影响可能是：明明主观上睡得不错，却因为 App 显示“深睡少”而开始担心；或者看到一个睡眠评分，就把第二天的疲劳完全归因于某个分期数字。睡眠规律和总时长值得关注，单晚的 REM、深睡分钟数不该单独承担健康判断。

## 六、宣传里的“准确度提升”，还需要可以复核的上下文

小米产品页称，8 Pro 升级后的四通道监测模组带来心率监测准确度提升 10%、血氧监测准确度提升 5%，并说明数据来自小米内部实验室。[小米 8 Pro 产品页](https://www.mi.com/au/product/xiaomi-smart-band-8-pro/)

这里有几个具体问题：准确度相对什么产品提升？误差用什么指标算？受试者有多少人？参照设备是什么？不同运动强度、肤色和佩戴松紧下结果是否一样？只给提升百分比，公众无法复核“提升”具体意味着什么，也无法知道它对自己是否适用。内部测试可以支持研发；要让宣传数字成为独立证据，还需要公开测试对象、参照方法、测试条件和误差分布。

## 对普通人，真正的风险是把估算值当成精确账本

这些代码没有证明小米算法造成了严重健康伤害。更现实的问题，是数据看起来足够精细，使用者却看不到它的来源和不确定性：

- 步数达标，不代表一天的活动强度和持续时间都够。
- 运动热量不能精确抵消晚饭热量。
- 单次心率波动不能单独说明健康异常。
- 单晚睡眠分期不适合用来给自己下结论。

小米也说明这类测量仅供个人参考，产品不用于医疗诊断。[小米 8 Pro 产品说明](https://www.mi.com/au/product/xiaomi-smart-band-8-pro/)

## 目前能下的结论

**静态观察：**Android APK 3.59.1 的 `HmDailyDataParse.parseStep` 按步数比例分配日距离和 `stepCal`；`HmCaloriesParse.parse` 把阶段热量拆成 10 个一分钟项；`HmDailyDataParse.parseDailyHr` 读取并过滤分钟心率值；`HmSportReportParse.parse` 直接记录输入的运动 `calorie` 字段。完整数据链路报告见 [`work/mi_band8_pro_analysis.md`](work/mi_band8_pro_analysis.md)。

**推断：**这些分时数据如果被页面画成曲线，曲线会包含比例分摊、整数取整或阶段均分的影响。它们不能自动被理解为逐分钟独立测量。

**未知：**手环 8 Pro 是否实际经过这些特定解析器；步数识别、PPG 心率生成、热量模型和睡眠分期的底层算法；相关数字在页面最终展示前是否还有其他处理。

所以我最想追问的不是“它是不是故意算错”，而是：**小米能不能清楚标出哪些是传感器读数、哪些是模型估算、哪些是 App 分摊出来的？对那些显示到个位和分钟的数字，又有没有公开足够的验证方法和误差范围？** 目前反编译代码已经揭开了一部分数据整理过程，但离完整复现设备健康算法还有距离。

## 数据与隐私

仓库附有 [`data/public_weekly_summary.csv`](data/public_weekly_summary.csv)，由个人导出数据中的 `daily_report` 行生成，只保留按连续 28 天汇总的步数、卡路里字段和平均心率。具体日期被替换为从 1 开始的区间序号；步数、热量、心率均值分别取整到 100、50、5 的单位。不包含账号 ID、设备 ID、姓名、性别、生日、身高、体重、GPS 或逐条时间戳。这仍是个人健康数据，按用户授权公开。

原始导出文件不进入 Git。要从自己的本地导出重建汇总，可运行 `python3 scripts/build_public_summary.py`。脚本只读取本地 CSV，不复制身份字段；原始导出仍由 `.gitignore` 排除。

## 逆向范围

- Android App：Mi Fitness 3.59.1，`com.mi.health`，versionCode 359001。
- JADX：JADX MCP 0.7.1 / JADX 1.5.6，Temurin JDK 17.0.20。
- 本仓库包含反编译方法证据和分析稿；不包含官方 APK、固件、完整提取包或原始个人导出。
- REA 在该环境中未成功完成分析；README 和分析稿不把失败的 REA 启动结果当作算法证据。
