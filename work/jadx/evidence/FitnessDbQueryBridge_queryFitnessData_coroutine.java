// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 060-get_method_body-FitnessDbQueryBridge$AnonymousClass2-invokeSuspend.json
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            String string;
            DailyRecordBaseDao<? extends DailyRecordBaseEntity> dailyRecordBaseDao;
            String str;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FitnessDbQueryBridge fitnessDbQueryBridge = FitnessDbQueryBridge.INSTANCE;
            Logger.i(FitnessDbQueryBridge.TAG, "queryFitnessData input=" + fitnessDbQueryBridge.describeFitnessQuery(this.$q));
            String dataType = this.$q.getDataType();
            if (dataType == null || (string = StringsKt.trim((CharSequence) dataType).toString()) == null || StringsKt.isBlank(string)) {
                string = null;
            }
            if (string == null) {
                DailyRecordBaseDao<? extends DailyRecordBaseEntity> dailyRecordBaseDaoResolveDaoByTableName = YRNDatabaseMappings.INSTANCE.resolveDaoByTableName(this.$q.getTableName());
                if (dailyRecordBaseDaoResolveDaoByTableName == null) {
                    throw new IllegalArgumentException("no database table for tableName: " + this.$q.getTableName());
                }
                dailyRecordBaseDao = dailyRecordBaseDaoResolveDaoByTableName;
                str = null;
            } else {
                String strResolveHealthSinglePersistKey = YRNDatabaseMappings.INSTANCE.resolveHealthSinglePersistKey(string);
                if (strResolveHealthSinglePersistKey == null) {
                    throw new IllegalArgumentException("unsupported dataType: " + string);
                }
                DailyRecordBaseDao<? extends DailyRecordBaseEntity> dailyRecordDao = DailyRecordDaoRepo.getDailyRecordDao(strResolveHealthSinglePersistKey);
                if (dailyRecordDao == null) {
                    throw new IllegalArgumentException("no database table for dataType: " + string);
                }
                dailyRecordBaseDao = dailyRecordDao;
                str = strResolveHealthSinglePersistKey;
            }
            String tableName = dailyRecordBaseDao.getTableName();
            fitnessDbQueryBridge.requireTableName(this.$q.getTableName(), tableName);
            String strBuildSingleDailySqlByTime = fitnessDbQueryBridge.buildSingleDailySqlByTime(tableName, str, this.$q.getSid(), this.$q.getStartTimeSec(), this.$q.getEndTimeSec(), this.$q.getSort() == Sort.ASCEND ? "ASC" : "DESC", fitnessDbQueryBridge.normalizeLimit(this.$q.getLimit()));
            Logger.i(FitnessDbQueryBridge.TAG, "queryFitnessData mappedKey=" + str + " table=" + tableName + " sql=" + FitnessDbQueryBridge.shortSql$default(fitnessDbQueryBridge, strBuildSingleDailySqlByTime, 0, 2, null));
            Iterable dailyRecord = dailyRecordBaseDao.getDailyRecord(new SimpleSQLiteQuery(strBuildSingleDailySqlByTime));
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(dailyRecord, 10));
            Iterator it = dailyRecord.iterator();
            while (it.hasNext()) {
                arrayList.add(FitnessDbQueryBridge.INSTANCE.toFitnessRecordSingle((DailyRecordBaseEntity) it.next(), string));
            }
            FitnessQueryResult fitnessQueryResult = new FitnessQueryResult(arrayList);
            Logger.i(FitnessDbQueryBridge.TAG, "queryFitnessData result=" + FitnessDbQueryBridge.INSTANCE.describeFitnessResult(fitnessQueryResult));
            return fitnessQueryResult;
        }
