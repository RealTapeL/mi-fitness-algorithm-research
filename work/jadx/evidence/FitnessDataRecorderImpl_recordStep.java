// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 029-get_method_body-FitnessDataRecorderImpl-recordStep.json
    public final boolean recordStep(@NotNull String sid, int zoneOffsetInSec, @NotNull List<StepItem> stepItemList) {
        Intrinsics.checkNotNullParameter(sid, "sid");
        Intrinsics.checkNotNullParameter(stepItemList, "stepItemList");
        return recordDailyData$default(this, "steps", sid, zoneOffsetInSec, stepItemList, false, false, 48, null);
    }
