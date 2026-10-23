// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 009-get_method_body.json
// Method: com.xiaomi.fit.fitness.persist.db.utils.YRNDatabaseMappings.resolveDailyReportSpec
    private final HealthSummarySpec resolveDailyReportSpec(String rnDataType) {
        String strNormalize = normalize(rnDataType);
        if (Intrinsics.areEqual(strNormalize, normalize("calories"))) {
            return dailyReportSpec("CALORIE");
        }
        if (Intrinsics.areEqual(strNormalize, normalize("energy"))) {
            return dailyReportSpec("ENERGY");
        }
        if (Intrinsics.areEqual(strNormalize, normalize("heart_rate"))) {
            return dailyReportSpec("HR");
        }
        if (Intrinsics.areEqual(strNormalize, normalize("intensity"))) {
            return dailyReportSpec("MH_STRENGTH");
        }
        if (Intrinsics.areEqual(strNormalize, normalize("sleep"))) {
            return dailyReportSpec("SLEEP");
        }
        if (Intrinsics.areEqual(strNormalize, normalize("stress"))) {
            return dailyReportSpec("STRESS");
        }
        if (Intrinsics.areEqual(strNormalize, normalize("valid_stand"))) {
            return dailyReportSpec("VALID_STAND");
        }
        if (Intrinsics.areEqual(strNormalize, normalize("steps"))) {
            return dailyReportSpec("STEP");
        }
        if (Intrinsics.areEqual(strNormalize, normalize("spo2"))) {
            return dailyReportSpec("SPO2");
        }
        return null;
    }
