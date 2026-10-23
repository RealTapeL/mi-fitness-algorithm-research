// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 094-get_method_body-FitnessDbQueryBridge-toFitnessRecordSingle.json
    /* JADX INFO: Access modifiers changed from: private */
    public final FitnessRecord toFitnessRecordSingle(DailyRecordBaseEntity dailyRecordBaseEntity, String str) {
        String sid = dailyRecordBaseEntity.getSid();
        if (str == null) {
            str = dailyRecordBaseEntity.getKey();
        }
        String str2 = str;
        long time = dailyRecordBaseEntity.getTime();
        long time2 = dailyRecordBaseEntity.getTime() + ((long) dailyRecordBaseEntity.getZoneOffsetInSec());
        String zoneName = dailyRecordBaseEntity.getZoneName();
        if (zoneName == null) {
            zoneName = "UTC";
        }
        String str3 = zoneName;
        int zoneOffsetInSec = dailyRecordBaseEntity.getZoneOffsetInSec();
        String recordValue = dailyRecordBaseEntity.getValue();
        if (recordValue == null) {
            recordValue = "{}";
        }
        return new FitnessRecord(sid, str2, time, time2, str3, zoneOffsetInSec, recordValue, null, 128, null);
    }
