// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 032-get_method_body-FitnessDataRecorderImpl-recordManualHr.json
    public final boolean recordManualHr(@NotNull String sid, int zoneOffsetInSec, @NotNull List<HrItem> hrItemList) {
        Intrinsics.checkNotNullParameter(sid, "sid");
        Intrinsics.checkNotNullParameter(hrItemList, "hrItemList");
        return recordDailyData$default(this, "single_heart_rate", sid, zoneOffsetInSec, hrItemList, false, false, 48, null);
    }
