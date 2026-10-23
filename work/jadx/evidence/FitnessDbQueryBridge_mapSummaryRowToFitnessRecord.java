// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 041-get_method_body-FitnessDbQueryBridge-mapSummaryRowToFitnessRecord.json
    /* JADX INFO: Access modifiers changed from: private */
    public final FitnessRecord mapSummaryRowToFitnessRecord(Cursor cursor, String rnDataType, String timeColumn) {
        long longOrZero = getLongOrZero(cursor, timeColumn);
        int intOrZero = getIntOrZero(cursor, pickColumnNameFromCursor(cursor, CollectionsKt.listOf((Object[]) new String[]{"zoneOffsetSec", CloudContract.COL_ZONE_OFFSET, "zone_offset", "zone_offset_sec"})));
        String stringOrNull = getStringOrNull(cursor, pickColumnNameFromCursor(cursor, CollectionsKt.listOf((Object[]) new String[]{"zoneName", "zone_name", "tzName", "tz_name"})));
        if (stringOrNull == null) {
            stringOrNull = "UTC";
        }
        String str = stringOrNull;
        String stringOrNull2 = getStringOrNull(cursor, pickColumnNameFromCursor(cursor, CollectionsKt.listOf((Object[]) new String[]{"value", "value", b.d, "data", "json"})));
        if (stringOrNull2 == null) {
            stringOrNull2 = "{}";
        }
        String str2 = stringOrNull2;
        String stringOrNull3 = getStringOrNull(cursor, pickColumnNameFromCursor(cursor, CollectionsKt.listOf((Object[]) new String[]{"sid", "sid"})));
        if (stringOrNull3 == null) {
            stringOrNull3 = "";
        }
        return new FitnessRecord(stringOrNull3, rnDataType, longOrZero, longOrZero, str, intOrZero, str2, null, 128, null);
    }
