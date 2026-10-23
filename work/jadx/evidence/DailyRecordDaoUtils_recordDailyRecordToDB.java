// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 090-get_method_body-DailyRecordDaoUtils-recordDailyRecordToDB.json
    public final boolean recordDailyRecordToDB(@NotNull String persistKey, @NotNull String sid, @NotNull List<DailyRecordItemModel> recordList, boolean allowUpdate) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String message;
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(persistKey, "persistKey");
        Intrinsics.checkNotNullParameter(sid, "sid");
        Intrinsics.checkNotNullParameter(recordList, "recordList");
        Class<? extends DailyRecordBaseEntity> recordEntityClass = DailyRecordDaoRepo.INSTANCE.getRecordEntityClass(persistKey);
        boolean zInsertDailyRecordToDb = false;
        if (recordEntityClass == null) {
            FitnessLogUtils.w(TAG, "no record entity class " + persistKey);
            return false;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<DailyRecordItemModel> it = recordList.iterator();
        while (it.hasNext()) {
            DailyRecordItemModel dailyRecordItemModelReprocessModelRecordIfNeed = reprocessModelRecordIfNeed(persistKey, sid, it.next());
            if (dailyRecordItemModelReprocessModelRecordIfNeed != null) {
                DailyRecordBaseEntity dailyRecordBaseEntityNewInstance = recordEntityClass.getConstructor(String.class, String.class, Long.TYPE).newInstance(persistKey, sid, Long.valueOf(dailyRecordItemModelReprocessModelRecordIfNeed.getTime()));
                dailyRecordBaseEntityNewInstance.setFromLocalRecord(dailyRecordItemModelReprocessModelRecordIfNeed);
                Intrinsics.checkNotNull(dailyRecordBaseEntityNewInstance);
                arrayList.add(dailyRecordBaseEntityNewInstance);
            }
        }
        try {
            zInsertDailyRecordToDb = insertDailyRecordToDb(persistKey, arrayList, allowUpdate);
        } catch (SQLiteException e) {
            if (Build.VERSION.SDK_INT != 30 || (message = e.getMessage()) == null || !StringsKt.contains$default((CharSequence) message, (CharSequence) "exists", false, 2, (Object) null)) {
                throw e;
            }
            Logger.i(TAG, "insertAll SQLiteException " + e);
        }
        FitnessLogUtils.i(TAG, "recordDailyRecordToDB: persistKey=" + persistKey + ", sid=" + sid + ", recordSize=" + arrayList.size());
        if (zInsertDailyRecordToDb) {
            DailyRecordBaseDao<? extends DailyRecordBaseEntity> dailyRecordDao = DailyRecordDaoRepo.getDailyRecordDao(persistKey);
            if (dailyRecordDao != null) {
                str = persistKey;
                str2 = sid;
                SchemaDbChangeCapture.INSTANCE.recordDailyUpsert$fitness_data_core_chinaProductRelease(str2, str, dailyRecordDao.getTableName(), allowUpdate, arrayList);
            } else {
                str = persistKey;
                str2 = sid;
            }
            try {
                SyncLatencyTracker.INSTANCE.onDataInserted(str, str2, recordList);
            } catch (Exception e2) {
                FitnessLogUtils.e(TAG, "SyncLatencyTracker exception!! " + e2);
            }
        }
        return zInsertDailyRecordToDb;
    }
