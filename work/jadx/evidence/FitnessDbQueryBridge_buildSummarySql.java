// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 040-get_method_body-FitnessDbQueryBridge-buildSummarySql.json
    /* JADX INFO: Access modifiers changed from: private */
    public final String buildSummarySql(SupportSQLiteDatabase db, String table, List<String> timeColumnCandidates, long startTime, long endTime, String order, int limit, String sid, String dataTypeValue, String viewTagValue) {
        String strPickFirstExistingColumn;
        String strPickFirstExistingColumn2;
        String strPickFirstExistingColumn3;
        String strPickFirstExistingColumn4 = pickFirstExistingColumn(db, table, timeColumnCandidates);
        if (strPickFirstExistingColumn4 == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(256);
        sb.append("SELECT * FROM ");
        sb.append(table);
        sb.append(" WHERE 1=1");
        sb.append(" AND ");
        sb.append(strPickFirstExistingColumn4);
        sb.append(" >= ");
        sb.append(startTime);
        sb.append(" AND ");
        sb.append(strPickFirstExistingColumn4);
        sb.append(" < ");
        sb.append(endTime);
        FitnessDbQueryBridge fitnessDbQueryBridge = INSTANCE;
        String strPickFirstExistingColumn5 = fitnessDbQueryBridge.pickFirstExistingColumn(db, table, CollectionsKt.listOf((Object[]) new String[]{"isDeleted", "is_deleted", "isDeleted"}));
        if (strPickFirstExistingColumn5 != null) {
            sb.append(" AND ");
            sb.append(strPickFirstExistingColumn5);
            sb.append(" = 0");
        }
        if (sid != null && !StringsKt.isBlank(sid) && (strPickFirstExistingColumn3 = fitnessDbQueryBridge.pickFirstExistingColumn(db, table, CollectionsKt.listOf((Object[]) new String[]{"sid", "sid"}))) != null) {
            sb.append(" AND ");
            sb.append(strPickFirstExistingColumn3);
            sb.append(" = '");
            sb.append(fitnessDbQueryBridge.escapeSql(sid));
            sb.append("'");
        }
        if (dataTypeValue != null && !StringsKt.isBlank(dataTypeValue) && (strPickFirstExistingColumn2 = fitnessDbQueryBridge.pickFirstExistingColumn(db, table, CollectionsKt.listOf((Object[]) new String[]{"dataType", "data_type", "dataType"}))) != null) {
            sb.append(" AND ");
            sb.append(strPickFirstExistingColumn2);
            sb.append(" = '");
            sb.append(fitnessDbQueryBridge.escapeSql(dataTypeValue));
            sb.append("'");
        }
        if (viewTagValue != null && !StringsKt.isBlank(viewTagValue) && (strPickFirstExistingColumn = fitnessDbQueryBridge.pickFirstExistingColumn(db, table, CollectionsKt.listOf((Object[]) new String[]{CloudContract.COL_VIEW_TAG, "view_tag", CloudContract.COL_VIEW_TAG}))) != null) {
            sb.append(" AND ");
            sb.append(strPickFirstExistingColumn);
            sb.append(" = '");
            sb.append(fitnessDbQueryBridge.escapeSql(viewTagValue));
            sb.append("'");
        }
        sb.append(" ORDER BY ");
        sb.append(strPickFirstExistingColumn4);
        sb.append(" ");
        sb.append(order);
        sb.append(" LIMIT ");
        sb.append(limit);
        return sb.toString();
    }
