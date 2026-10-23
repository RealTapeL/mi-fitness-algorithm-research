# 小米手环 8 Pro 数据链路：Android 3.59.1 静态证据

## 范围与证据标记

本轮检查迁移后的 `项目目录`。APK 为 `com.mi.health` 3.59.1（versionCode 359001）。方法体由现有 `jadx-headless-mcp-0.7.1-all.jar` 定点提取，JADX 1.5.6、Temurin JDK 17.0.20；每次只保留 `com.xiaomi.fit.fitness`、`com.mi.health.step` 和数据库桥包，当前每个过滤索引保留约 1000 个类（原始 DEX 类数 49984）。没有重新下载 APK。

- **静态观察**：APK/JS/导出 CSV 中能直接看到的代码或字段。
- **推断**：由调用关系或字段映射推得，但没有算法或运行态证据闭合。
- **未知**：当前材料没有建立的连接；不能由相关性补成因果。
- 既有 REA 结果仍记录 macOS `SwiftBridging` 启动失败；这些失败结果没有作为成功的 REA 分析或算法证据纳入本仓库。

## 结论速览

| 项目 | 状态 | 现阶段结论 |
|---|---|---|
| 日步数、距离、步行热量分时 | 静态观察 | `HmDailyDataParse.parseStep` 解码分钟数组，以数组中的步数为基础按日总步数比例分摊日距离、`stepCal`；非零步数分钟生成 `StepItem`。底层计步和热量公式未见。 |
| 日常心率 | 静态观察 | `HmDailyDataParse.parseDailyHr` 每分钟读编码记录第 4 字节，只接受 1–254，生成时间戳并交给 `recordDailyHr`。 |
| 手动心率 | 静态观察 | `HmManualHrParse.parse` 取 Base64 `heartRateData` 的首字节和 `generatedTime`，调用 `recordManualHr`。 |
| 日热量 | 静态观察 | `HmCaloriesParse.parse` 将每阶段 calories 整数均分到 10 个一分钟项，余数从前项开始分配；它做分时，不计算 kcal。 |
| 运动报告热量 | 静态观察 | `HmSportReportParse.parse` 将 JSON 的 `calorie` 直接放进 `SportBasicReport.calories`，并映射心率、步数、距离等字段后 `recordSportReport`。 |
| 日汇总查询 | 静态观察 | `FitnessDbQueryBridge.queryFitnessSummaryData` 按 `YRNDatabaseMappings.resolveHealthSummarySpec` 解析表和类型，随后调 `buildSummarySql`、`querySummaryRows`、`mapSummaryRowToFitnessRecord`。SQL 为行查询/过滤/排序，没有求和或平均。 |
| 页面最终数值 | 部分静态观察 / 未闭合 | JS Repo 把 `steps`、`calories`、`heart_rate` 映射到查询 API；`stepsGoalCollector` 读取 `value.steps`/`value.goal`。当前没有闭合到首页具体文本组件或手环 8 Pro 的实际记录实例。 |

## 解析与生成边界

### 1. Huami/HM 类解析器分派

**静态观察**：`HuaMiDataParseImpl.getParseInstance(HuaMiParseType)` 按类型选择 `HmManualHrParse`、`HmDailyDataParse`、`HmCaloriesParse`、`HmSportReportParse`、`HmSportRecordParse` 等；`dataParse(HuaMiParseType, sid, data)` 再调用所选解析器的 `parse(sid, data, continuation)`。证据见 [`HuaMiDataParseImpl_getParseInstance.java`](./jadx/evidence/HuaMiDataParseImpl_getParseInstance.java) 和 [`HuaMiDataParseImpl_dataParse.java`](./jadx/evidence/HuaMiDataParseImpl_dataParse.java)。

**未知**：定点 `get_xrefs_to_method(HuaMiDataParseImpl.dataParse)` 在该索引中返回 0 个直接静态引用。动态/接口分派可能不形成直接引用；当前没有闭合“Mi Band 8 Pro 接收的 BLE/GATT 数据 → 这个分派器”的调用点。

### 2. 步数与日常心率

**静态观察**：`HmDailyDataParse.parse(String sid, String data, ...)` 读 JSON `tz`、`lastSyncTime`、`metadata`，取得首个 metadata 项的 `date` 和 JSON payload，换算当天零点，然后顺序调用 `parseActiveStage`、睡眠解析、`parseRestHr`、`parseStep`、`parseDailyHr`。完整方法体见 [`HmDailyDataParse_parse.java`](./jadx/evidence/HmDailyDataParse_parse.java)。

**静态观察**：`HmDailyDataParse.parseStep(JSONObject, sid, timeStamp)`：

- 读取 `StepsInfo.KEY_STEP_INFO.steps`、`realDis`（非正时回退 `distance`）和 `stepCal`。
- 只看 `data[0]`，Base64 解出区间数组；区间每点的步数来自 `HMParseUtilKt.byteToInt(value[(length * i) + 2])`。
- 用 `intervalSteps / daySteps` 的比例拆分日距离与 `stepCal`；随后把整数除法余数按遍历顺序补给有步数的分钟。
- 生成时间 `timeStamp + (start + i) * 60`，仅当该分钟步数非零时创建 `StepItem(time, steps, distance, calories)`，最后调用 `FitnessDataRecorder.recordStep`。

源码摘录：

```java
float ratio = intervalSteps / daySteps;
int intervalDistance = (int) (dayDistance * ratio);
int intervalCalories = (int) (ratio * dayStepCal);
...
StepItem item = new StepItem(time, intervalSteps, adjustedDistance, adjustedCalories);
sendDataRecord.recordStep(sid, tzInsec, items);
```

实际反编译变量名见 [`HmDailyDataParse_parseStep.java`](./jadx/evidence/HmDailyDataParse_parseStep.java)。这段代码能验证“App 如何把收到的日总量拆成分钟项”，没有给出传感器如何判定一步或产生 `daySteps/dayStepCal`。

**静态观察**：`HmDailyDataParse.parseDailyHr(JSONObject, sid, timeStamp)` 对 `data[0].value` 解码，按 `start`/`stop` 每分钟读取 `value[(length * i) + 3]`；仅 `1 <= bpm < 255` 建立 `HrItem(time, bpm)`，最后调用 `recordDailyHr`。见 [`HmDailyDataParse_parseDailyHr.java`](./jadx/evidence/HmDailyDataParse_parseDailyHr.java)。

**静态观察**：`parseRestHr` 从 `rhr` 取值；`rhr <= 0` 或 `HMParseUtilKt.isSupportSyncRestHr(sid)` 时不写这一项，否则调用 `recordRestHr`。

### 3. 手动心率、日热量与运动报告

**静态观察**：`HmManualHrParse.parse(String sid, String data, ...)` 遍历 JSON 数组，取 `generatedTime`、`timeZone`、`lastSyncTime` 和 Base64 `heartRateData`，用其首字节创建 `HrItem`，以 `recordManualHr` 写入。记录器将类型标成 `single_heart_rate`；日常心率类型是 `heart_rate`。

**静态观察**：`HmCaloriesParse.parse` 中 `calories / 10` 为每分钟基值，`calories % 10` 给前面的分钟各加 1；时间以阶段时间为基准，每分钟加 60 秒。只对正值生成 `CaloriesItem`，然后调 `recordCalorie`。原始整类和方法体见 [`HmCaloriesParse.java`](./jadx/evidence/HmCaloriesParse.java)。

**静态观察**：`HmSportReportParse.parse` 直接从字段填充 `SportBasicReport`：`calorie` → `setCalories`，`total_step` → `setSteps`，`distance` → `setDistance`，`avg_heart_rate`、`max_heart_rate`、`min_heart_rate` → 对应 HR 字段，`run_time` → duration；随后调用 `recordSportReport`。未见热量公式。见 [`HmSportReportParse_parse.java`](./jadx/evidence/HmSportReportParse_parse.java)。

**静态观察**：新格式的 `FitnessDataParser.parse(FitnessDataId, byte[])` 依据 `isSchemaVersionSupport(dataId)` 在 `schemaParse` 和 `nativeParse` 间分支；`DailyReportParser.setDailyData` 将 `DataValueItem` 类型 0/1/2 分别填入 steps/calories/hr，其他类型填 HR 极值、压力、血氧、训练负荷等字段。这里是解析/赋值，不含这些数值的计算公式。见 [`FitnessDataParser_parse.java`](./jadx/evidence/FitnessDataParser_parse.java)、[`DailyReportParser_setDailyData.java`](./jadx/evidence/DailyReportParser_setDailyData.java)。

## 记录器与落库

**静态观察**：`FitnessDataRecorderImpl.recordStep` 调 `recordDailyData$default(..., "steps", ...)`；`recordDailyHr` 使用 `"heart_rate"`，`recordManualHr` 使用 `"single_heart_rate"`，`recordCalorie` 使用 `"calories"`。`recordDailyData` 将 `DailyRecordItem` 变成含 dbTime/timestamp、zoneOffset 的 `DailyRecordItemModel`，再调用 `FitnessDBDataUpdater.recordDailyRecord`。

**静态观察**：`FitnessDBDataUpdater.recordDailyRecord` 在列表非空时，默认走 `DailyRecordDaoUtils.recordDailyRecordToDB`；当 `allowSwitchProcess` 为真且 `checkForceRemoteService()` 成立时，改走 Binder `mRemoteService.recordDailyRecordToDB`。`checkForceRemoteService()` 条件是当前为主进程且远程 service 已连接。这是本地进程/服务分流，不能据此称为云端计算。`DailyRecordDaoUtils.recordDailyRecordToDB` 依据 persistKey 创建实体、设置本地记录，再转交 `insertDailyRecordToDb`。该方法通过 `DailyRecordDaoRepo.getDailyRecordDao(persistKey)` 取 DAO；列表不超过 5 条或 `allowUpdate` 为真时调 `insertReplaceAll(entityList)`，否则先 `insertReplaceAll` 前 5 条，再 `insertAll(entityList)`。证据见 [`FitnessDataRecorderImpl_recordDailyData.java`](./jadx/evidence/FitnessDataRecorderImpl_recordDailyData.java)、[`FitnessDBDataUpdater_recordDailyRecord.java`](./jadx/evidence/FitnessDBDataUpdater_recordDailyRecord.java)、[`DailyRecordDaoUtils_recordDailyRecordToDB.java`](./jadx/evidence/DailyRecordDaoUtils_recordDailyRecordToDB.java)、[`DailyRecordDaoUtils_insertDailyRecordToDb.java`](./jadx/evidence/DailyRecordDaoUtils_insertDailyRecordToDb.java)。

**静态观察**：另一个直接 React Native 写入口是 `YRNDatabaseModule.insertFitnessData(@ReactMethod)` → `FitnessDbWriteBridge.insertFitnessData`。该桥在 IO dispatcher 执行：sport 表写 `sportReportDao().insertAll`；日记录用 `resolveDailyDao(dataType, tableName)`，创建实体并写入 `insertReplaceAll`。`newDailyEntity` 设置 zone、UTC 零点、`upload=false`、`deleted=false` 和 record value。这个入口的 xref 指向 `YRNDatabaseModule.insertFitnessData.3.invokeSuspend`。它与设备记录器经 `FitnessDBDataUpdater` 的链路应分别记录。

**未知**：远程 Binder `mRemoteService.recordDailyRecordToDB` 的服务实现没有在本轮提取。直接插入桥的 DAO 写入有方法体证据，但不自动证明设备解析器走过 `YRNDatabaseModule.insertFitnessData` 这个独立入口。

## 查询、汇总和 JS 消费

**静态观察**：Android `YRNDatabaseModule.queryFitnessData` 和 `queryFitnessSummaryData` 都标注 `@ReactMethod`，通过 `executeQuery` 异步执行，并把结果变为 `toFitnessPayload` 的 list。输出 payload 字段包括 `sid`、`dataType`、`time`、`timeZero`、`zoneName`、zone offset、`value`；按参数可附 source，汇总查询还可从 JSON value 提取 `valueList`。桥接方法、汇总结果转换和序列化字段见 [`YRNDatabaseModule_queryFitnessSummaryData.java`](./jadx/evidence/YRNDatabaseModule_queryFitnessSummaryData.java)、[`YRNDatabaseModule_queryFitnessSummaryData_payload_transform.java`](./jadx/evidence/YRNDatabaseModule_queryFitnessSummaryData_payload_transform.java) 和 [`YRNDatabaseModule_toFitnessPayload.java`](./jadx/evidence/YRNDatabaseModule_toFitnessPayload.java)。

**静态观察**：`FitnessDbQueryBridge` 在 IO dispatcher 查询。单记录查询 `queryFitnessData` 根据 dataType/tableName 解析 DAO，再由 `buildSingleDailySqlByTime` 过滤 `time >= start`、`time < end`、`isDeleted=0`，可选 sid/key，排序并限量；之后映射为 `FitnessRecord`。

**静态观察**：`FitnessDbQueryBridge.queryFitnessSummaryData` 对 `daily_report` 使用 `YRNDatabaseMappings.resolveDailyReportSpec`。该方法把输入 `rnDataType` 与 `steps`、`calories`、`heart_rate` 经 `normalize()` 后比较，命中时分别令 dataTypeValue=`STEP`、`CALORIE`、`HR`；`dailyReportSpec` 固定 table=`daily_report`、viewTag=`days`。随后从 `FitnessSummaryDatabase` 取得只读数据库，调用 `buildSummarySql`，再由 `querySummaryRows` 遍历 Cursor；映射器 `mapSummaryRowToFitnessRecord` 只抽取时间、时区、value JSON 和 sid。这一条 Android 查询链已直接闭合。关键方法体见 [`FitnessDbQueryBridge_queryFitnessSummaryData_coroutine.java`](./jadx/evidence/FitnessDbQueryBridge_queryFitnessSummaryData_coroutine.java)、[`FitnessDbQueryBridge_buildSummarySql.java`](./jadx/evidence/FitnessDbQueryBridge_buildSummarySql.java)、[`FitnessDbQueryBridge_querySummaryRows.java`](./jadx/evidence/FitnessDbQueryBridge_querySummaryRows.java)、[`FitnessDbQueryBridge_mapSummaryRowToFitnessRecord.java`](./jadx/evidence/FitnessDbQueryBridge_mapSummaryRowToFitnessRecord.java)、[`YRNDatabaseMappings_resolveHealthSummarySpec.java`](./jadx/evidence/YRNDatabaseMappings_resolveHealthSummarySpec.java)、[`YRNDatabaseMappings_resolveDailyReportSpec.java`](./jadx/evidence/YRNDatabaseMappings_resolveDailyReportSpec.java) 和 [`YRNDatabaseMappings_dailyReportSpec.java`](./jadx/evidence/YRNDatabaseMappings_dailyReportSpec.java)。`red_dot` 是该方法内另一条特殊 DAO 查询分支，与 daily_report 指标查询分开。

**推断**：`buildSummarySql` 只有 `SELECT *` 和时间范围、删除标记、sid、dataType、viewTag 等过滤/排序/limit，没有聚合表达式；因此这些 `daily_report` 行在本次读取前已写入。它们由哪个本地任务或服务端流程生成，当前仍未知。

**静态观察**：Health JS 数据库 Repo 中，`DailyTypeNativeMap` 把 `IDailyType.STEPS/CALORIES/HEART_RATE` 映射为 `steps/calories/heart_rate`，普通记录表映射为 `step_record/calorie_record/hr_record`；summary type 映射包含 `STEPS/CALORIE/HR`，对应 summary 表 `daily_report`。`StepsRepo`、`CaloriesRepo`、`HeartRateRepo` 均组合普通 daily repo 与 `withDailySummaryData`。后者计算 summary type 与 tableName 后调用 `queryFitnessSummaryData`，普通 `getRecordList` 调 `queryFitnessData`；summary 返回后还会将 native 类型映射回 JS daily type。对应片段在 [`health-fitness-database-modules.js`](./jadx/evidence/health-fitness-database-modules.js) 与 [`sport-fitness-database-modules.js`](./jadx/evidence/sport-fitness-database-modules.js)。

**静态观察**：模块 `he-552` 的 `stepsGoalCollector` 用 `getDailyRepo(IDailyType.STEPS).getSummaryRecordList({startTime,endTime,limit:0})`，读取首行 `value.steps` 和 `value.goal`。`he-537`、`he-542` 的行为检查器从普通记录中逐行累计 `value.steps`。这证明 JS 消费查询后的数值；尚未闭合到首页具体 React 组件把哪个数写成当前 UI 字符串。

**静态观察 / 未闭合**：Android `StepTimelineDataHandler.onDataReady` 从 `STEP_REPORT_MAP` 取 `DailyStepReport.stepRecords`，逐条转换为 MatrixCursor 的 `time/steps/distance/calories` 列。这是结构转换，不是日汇总求和。`DailyStepReport` 字段含 `steps/calories/distance/stepRecords`，`DailyCalorieReport` 含 `calories/calRecords`；现有类摘要显示日级 totals 和 per-record 列表，但没显示这些 totals 的计算方法。证据见 [`StepTimelineDataHandler_onDataReady.java`](./jadx/evidence/StepTimelineDataHandler_onDataReady.java)、[`DailyStepReport_class_summary.txt`](./jadx/evidence/DailyStepReport_class_summary.txt) 和 [`DailyCalorieReport_class_summary.txt`](./jadx/evidence/DailyCalorieReport_class_summary.txt)。

**静态观察**：`YRNDatabaseModule.runAggregateDatabase`（JADX Java 失败，保留了 smali）可按 source=`summary`/`sport`/其他选择 summary/sport/single 查询，然后调用 `RowPrep.fromRecords` 和 `AggregateExec.executeTrees(stages, rows)`。这是可选的数据库聚合流水线候选。当前两个 extracted JS bundle 中出现的是 `aggregateSportStatData`，没有找到 `aggregateDatabase` 调用；不能把这条流水线当作首页数值生成路径。Smali 输出：[`086-get_method_body-YRNDatabaseModule-runAggregateDatabase.json`](./jadx/086-get_method_body-YRNDatabaseModule-runAggregateDatabase.json)。

## 用户导出数据与 native 库

**静态观察（只描述结构，不作因果解释）**：设备来源 CSV 数据行 5 中存在 model=`lchz.watch.m67`、名称“小米手环 8 Pro”。健康中心原始 CSV 有 753,798 行，key 包括 calories=345,876、steps=187,880、heart_rate=80,699；聚合 CSV 有 14,497 行，Tag 包括 `daily_mark=7,091, daily_report=6,262, daily_fitness=1,144`。`user_fitness_data_records` 表有 865 行；带 UUID 的记录表有 0 行。原始导出文件位于 `data/`，本记录不展示含账户标识的导出文件名。

这些 CSV 证明导出中存在设备来源、时间序列和聚合类别；不能证明 Band8 Pro 的传感器算法、App 与服务端哪一方生成某条 aggregate row，也不能用字段相关性当作算法因果证据。

**静态观察**：本地检查的 `libHealthCare.so` 为 Android AArch64 ELF、API 21、NDK r20、stripped。`nm -D --defined-only --demangle` 可见 JNI `HealthCare.GetHealthScore/getHRVRmssd/getHRVScore`，以及 C++ `HRV::compute_hrv`、`Healthcare::GetHealthScore`、`HealthScoring::calcSportScore/getStepsScore` 等导出符号；命令输出见 [`libHealthCare_symbols.txt`](./jadx/evidence/libHealthCare_symbols.txt)。原生库本身不随公开仓库发布。

**推断 / 未知**：符号能证明这个 native 库暴露 HRV、健康评分和运动/步数评分相关实现；它们没有证明在计算计步/热量。库已 stripped，本轮仅检查 ELF 头与动态符号，未反汇编函数体，也未建立 APK JNI 调用到具体业务字段的链路。

## 算法归属判断

### 手机 APK 可直接验证

- 已验证 App 对 `HmDailyDataParse` 收到的步数时间序列进行解码、过滤、分时，并按日总步数比例分配距离与 `stepCal`。
- 已验证 App 对 `HmDailyDataParse` 收到的心率字节做每分钟取值与 1–254 的有效值过滤。
- 已验证 App 对 `HmCaloriesParse` 的阶段 calories 做 10 分钟均分，并在 `HmSportReportParse` 中读取设备提供的 workout calories。
- 已验证记录器用 `steps`、`heart_rate`、`single_heart_rate`、`calories` 等 persistKey 记录，且普通/summary 查询如何筛选、排序、映射、序列化到 JS。

### 仅能判断输入已给定，公式仍未知

- 设备怎样从加速度计/陀螺仪信号识别步伐，怎样给出 `daySteps`。
- 设备怎样从 PPG 产生心率，如何校准/平滑/剔除噪声。
- `dayStepCal`、独立 stage calories、运动报告 `calorie` 的能量模型和人体参数。
- 汇总表 row 的生成地点与服务端/本地计算职责。
- `lchz.watch.m67` 对应的设备同步/parser 类型，以及这次导出记录和此处 HM parser 的对应关系。
- 首页/步数页/心率页最终显示哪个 repo 字段、是否还有额外换算或缓存。

## 本轮产生的可读证据文件

方法体快照位于 [`work/jadx/evidence`](./jadx/evidence/)，原始 MCP JSON 保留在 [`work/jadx`](./jadx/)。方法快照第一行记录对应原始 MCP 文件；所有代码是 JADX 输出，不等同于原始 Kotlin/Java 源码。

### 工具和版本

- APK: `com.mi.health` 3.59.1 / 359001。
- JADX MCP: `jadx-headless-mcp-0.7.1-all.jar`; JAR CLI 声明使用 JADX 1.5.6。
- Java: Temurin JDK 17.0.20。
- 仅静态读取；未运行 APK、未连设备、未抓 BLE，也未运行测试。
