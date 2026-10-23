// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 005-get_method_body.json
// Method: com.xiaomi.fit.fitness.persist.db.utils.DailyRecordDaoUtils.insertDailyRecordToDb
    private final boolean insertDailyRecordToDb(String persistKey, List<? extends DailyRecordBaseEntity> entityList, boolean allowUpdate) {
        if (entityList.isEmpty()) {
            FitnessLogUtils.i(TAG, "insertDailyRecordToDb: key = " + persistKey + ", but no record");
            return true;
        }
        DailyRecordBaseDao<? extends DailyRecordBaseEntity> dailyRecordDao = DailyRecordDaoRepo.getDailyRecordDao(persistKey);
        if (dailyRecordDao == null) {
            FitnessLogUtils.w(TAG, persistKey + " not support db");
            return false;
        }
        if (entityList.size() <= 5 || allowUpdate) {
            dailyRecordDao.insertReplaceAll(entityList);
        } else {
            dailyRecordDao.insertReplaceAll(entityList.subList(0, 5));
            dailyRecordDao.insertAll(entityList);
        }
        return true;
    }
