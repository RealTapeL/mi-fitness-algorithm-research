// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 031-get_method_body-FitnessDataRecorderImpl-recordDailyHr.json
    @Override // com.xiaomi.fit.fitness.export.api.FitnessDataRecorder
    public boolean recordDailyHr(@NotNull String sid, int zoneOffsetInSec, @NotNull HrItem... hrItem) {
        Intrinsics.checkNotNullParameter(sid, "sid");
        Intrinsics.checkNotNullParameter(hrItem, "hrItem");
        return recordDailyData$default(this, "heart_rate", sid, zoneOffsetInSec, ArraysKt.asList(hrItem), false, false, 48, null);
    }
