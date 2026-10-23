// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 082-get_method_body-FitnessDbQueryBridge-buildSingleDailySqlByTime.json
    /* JADX INFO: Access modifiers changed from: private */
    public final String buildSingleDailySqlByTime(String tableName, String dbKey, String sid, long startTimeUtcSec, long endTimeUtcSec, String order, int limit) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("SELECT * FROM ");
        sb.append(tableName);
        sb.append(" WHERE 1=1");
        sb.append(" AND ");
        sb.append("time");
        sb.append(" >= ");
        sb.append(startTimeUtcSec);
        sb.append(" AND ");
        sb.append("time");
        sb.append(" < ");
        sb.append(endTimeUtcSec);
        sb.append(" AND ");
        sb.append("isDeleted");
        sb.append(" = 0");
        if (sid != null && !StringsKt.isBlank(sid)) {
            sb.append(" AND ");
            sb.append("sid");
            sb.append(" = '");
            sb.append(INSTANCE.escapeSql(sid));
            sb.append("'");
        }
        if (dbKey != null && !StringsKt.isBlank(dbKey)) {
            sb.append(" AND ");
            sb.append("key");
            sb.append(" = '");
            sb.append(INSTANCE.escapeSql(dbKey));
            sb.append("'");
        }
        sb.append(" ORDER BY ");
        sb.append("time");
        sb.append(" ");
        sb.append(order);
        sb.append(" LIMIT ");
        sb.append(limit);
        return sb.toString();
    }
