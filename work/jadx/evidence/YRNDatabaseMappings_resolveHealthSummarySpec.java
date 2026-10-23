// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 008-get_method_body.json
// Method: com.xiaomi.fit.fitness.persist.db.utils.YRNDatabaseMappings.resolveHealthSummarySpec
    @Nullable
    public final HealthSummarySpec resolveHealthSummarySpec(@NotNull String tableName, @NotNull String rnDataType) {
        HomeDataType homeDataTypeResolveSummaryHomeDataType;
        Intrinsics.checkNotNullParameter(tableName, "tableName");
        Intrinsics.checkNotNullParameter(rnDataType, "rnDataType");
        String strNormalizeTableName = normalizeTableName(tableName);
        int iHashCode = strNormalizeTableName.hashCode();
        if (iHashCode != -1372252646) {
            if (iHashCode == 973576630) {
                if (strNormalizeTableName.equals("rainbow") && Intrinsics.areEqual(normalize(rnDataType), normalize("goal"))) {
                    return new HealthSummarySpec("rainbow", null, null, null, 14, null);
                }
                return null;
            }
            if (iHashCode == 1083030683 && strNormalizeTableName.equals("red_dot") && (homeDataTypeResolveSummaryHomeDataType = resolveSummaryHomeDataType(rnDataType)) != null) {
                return new HealthSummarySpec("red_dot", homeDataTypeResolveSummaryHomeDataType, null, null, 12, null);
            }
            return null;
        }
        if (strNormalizeTableName.equals("daily_report")) {
            return resolveDailyReportSpec(rnDataType);
        }
        return null;
    }
