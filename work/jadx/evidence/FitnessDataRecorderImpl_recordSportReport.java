// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 081-get_method_body-FitnessDataRecorderImpl-recordSportReport.json
    @Override // com.xiaomi.fit.fitness.export.api.FitnessDataRecorder
    public boolean recordSportReport(@NotNull String sid, int zoneOffsetInSec, @NotNull SportBasicReport sportReport) {
        Intrinsics.checkNotNullParameter(sid, "sid");
        Intrinsics.checkNotNullParameter(sportReport, "sportReport");
        return FitnessDBDataUpdater.INSTANCE.getInstance().recordSportReport(sid, sportReport);
    }
