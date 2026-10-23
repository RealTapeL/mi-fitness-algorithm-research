// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 056-get_method_body-FitnessDataRecorderImpl-recordDailyData.json
    private final boolean recordDailyData(String persistKey, String sid, int zoneOffsetInSec, List<? extends DailyRecordItem> itemList, boolean allowSwitchProcess, boolean allowUpdate) {
        ArrayList arrayList = new ArrayList();
        for (DailyRecordItem dailyRecordItem : itemList) {
            Long dbTime = dailyRecordItem.getDbTime();
            arrayList.add(new DailyRecordItemModel(dbTime != null ? dbTime.longValue() : dailyRecordItem.getTimestamp(), zoneOffsetInSec, dailyRecordItem));
        }
        return recordDailyData$default(this, persistKey, sid, arrayList, allowSwitchProcess, false, 16, null);
    }
