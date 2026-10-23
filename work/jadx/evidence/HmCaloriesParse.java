// JADX 1.5.6 decompiled class from Android APK 3.59.1; source: work/jadx/010-get_class_source.json
package com.xiaomi.fit.fitness.device.hm.parse.repo;

import com.xiaomi.fit.data.common.log.FitnessLogUtils;
import com.xiaomi.fit.data.common.util.FitnessDateUtils;
import com.xiaomi.fit.fitness.device.hm.api.ParseResultItem;
import com.xiaomi.fit.fitness.device.hm.parse.repo.HmCaloriesParse;
import com.xiaomi.fit.fitness.device.hm.parse.utils.HMParseUtilKt;
import com.xiaomi.fit.fitness.export.api.FitnessDataRecorder;
import com.xiaomi.fit.fitness.export.data.item.CaloriesItem;
import com.xiaomi.fit.fitness.export.di.FitnessDataExtKt;
import com.xiaomi.hm.health.bt.sdk.data.PaiData;
import com.xiaomi.hm.health.dataprocess.StepsInfo;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes10.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0096@¢\u0006\u0002\u0010\u000fR\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lcom/xiaomi/fit/fitness/device/hm/parse/repo/HmCaloriesParse;", "Lcom/xiaomi/fit/fitness/device/hm/parse/repo/IHmDataParseByCategory;", "<init>", "()V", "sendDataRecord", "Lcom/xiaomi/fit/fitness/export/api/FitnessDataRecorder;", "getSendDataRecord", "()Lcom/xiaomi/fit/fitness/export/api/FitnessDataRecorder;", "sendDataRecord$delegate", "Lkotlin/Lazy;", "parse", "Lcom/xiaomi/fit/fitness/device/hm/api/ParseResultItem;", "sid", "", "data", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fitness-sync_chinaProductRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHmCaloriesParse.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HmCaloriesParse.kt\ncom/xiaomi/fit/fitness/device/hm/parse/repo/HmCaloriesParse\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,77:1\n37#2:78\n36#2,3:79\n*S KotlinDebug\n*F\n+ 1 HmCaloriesParse.kt\ncom/xiaomi/fit/fitness/device/hm/parse/repo/HmCaloriesParse\n*L\n65#1:78\n65#1:79,3\n*E\n"})
public final class HmCaloriesParse implements IHmDataParseByCategory {

    /* JADX INFO: renamed from: sendDataRecord$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy sendDataRecord = LazyKt.lazy(new Function0() { // from class: jjd
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return HmCaloriesParse.sendDataRecord_delegate$lambda$0();
        }
    });

    private final FitnessDataRecorder getSendDataRecord() {
        return (FitnessDataRecorder) this.sendDataRecord.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FitnessDataRecorder sendDataRecord_delegate$lambda$0() {
        return FitnessDataExtKt.getInstance(FitnessDataRecorder.INSTANCE);
    }

    @Override // com.xiaomi.fit.fitness.device.hm.parse.repo.IHmDataParseByCategory
    @Nullable
    public Object parse(@NotNull String str, @NotNull String str2, @NotNull Continuation<? super ParseResultItem> continuation) {
        int i;
        int i2;
        String str3 = "time";
        try {
            JSONObject jSONObject = new JSONObject(str2);
            int iOptInt = jSONObject.optInt("tz");
            try {
                long jOptLong = jSONObject.optLong(PaiData.LAST_SYNC_TIME);
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("stages");
                long jOptLong2 = jSONObject.optLong("time");
                FitnessLogUtils.i(HMParseUtilKt.TAG, "HmCaloriesParse parseCalories stages.length=" + (jSONArrayOptJSONArray != null ? Boxing.boxInt(jSONArrayOptJSONArray.length()) : null) + "**time=" + jOptLong2);
                if (jSONArrayOptJSONArray == null) {
                    return new ParseResultItem(true, iOptInt, jOptLong);
                }
                int length = jSONArrayOptJSONArray.length();
                int i3 = 0;
                while (i3 < length) {
                    JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i3);
                    int iOptInt2 = jSONObjectOptJSONObject.optInt(str3);
                    int iOptInt3 = jSONObjectOptJSONObject.optInt(StepsInfo.KEY_CALORIES);
                    int i4 = iOptInt3 / 10;
                    int i5 = iOptInt3 % 10;
                    ArrayList arrayList = new ArrayList();
                    int i6 = 0;
                    while (i6 < 10) {
                        int i7 = length;
                        String str4 = str3;
                        long j = ((long) (iOptInt2 * 600)) + jOptLong2 + ((long) (i6 * 60));
                        if (i5 > 0) {
                            i5--;
                            i2 = i4 + 1;
                        } else {
                            i2 = i4;
                        }
                        if (i2 > 0) {
                            CaloriesItem caloriesItem = new CaloriesItem(j, i2);
                            arrayList.add(caloriesItem);
                            FitnessLogUtils.d(HMParseUtilKt.TAG, "HmDailyDataParse parseCalories item=" + caloriesItem);
                        }
                        i6++;
                        length = i7;
                        str3 = str4;
                    }
                    String str5 = str3;
                    int i8 = length;
                    FitnessDataRecorder sendDataRecord = getSendDataRecord();
                    int tzOffsetInSecBy15min = FitnessDateUtils.getTzOffsetInSecBy15min(iOptInt);
                    CaloriesItem[] caloriesItemArr = (CaloriesItem[]) arrayList.toArray(new CaloriesItem[0]);
                    if (!sendDataRecord.recordCalorie(str, tzOffsetInSecBy15min, (CaloriesItem[]) Arrays.copyOf(caloriesItemArr, caloriesItemArr.length))) {
                        return new ParseResultItem(false, 0, 0L, 6, null);
                    }
                    i3++;
                    length = i8;
                    str3 = str5;
                }
                return new ParseResultItem(true, iOptInt, jOptLong);
            } catch (Exception e) {
                e = e;
                i = iOptInt;
                FitnessLogUtils.e(HMParseUtilKt.TAG, "HmCaloriesParse " + e.getMessage(), e);
                return new ParseResultItem(true, i, 0L);
            }
        } catch (Exception e2) {
            e = e2;
            i = 0;
        }
    }
}
