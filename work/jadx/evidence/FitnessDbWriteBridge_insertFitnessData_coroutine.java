// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 059-get_method_body-FitnessDbWriteBridge$AnonymousClass2-invokeSuspend.json
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FitnessDbWriteBridge fitnessDbWriteBridge = FitnessDbWriteBridge.INSTANCE;
            Logger.i(FitnessDbWriteBridge.TAG, "insertFitnessData input=" + fitnessDbWriteBridge.describeWriteRequest(this.$req));
            if (fitnessDbWriteBridge.isSportTable(this.$req.getTableName())) {
                FitnessDatabase.INSTANCE.getInstance().sportReportDao().insertAll(fitnessDbWriteBridge.newSportEntity(this.$req));
                Logger.i(FitnessDbWriteBridge.TAG, "insertFitnessData sport=ok time=" + this.$req.getTime());
                return new WriteResult(1);
            }
            Pair pairResolveDailyDao = fitnessDbWriteBridge.resolveDailyDao(this.$req.getDataType(), this.$req.getTableName());
            DailyRecordBaseDao dailyRecordBaseDao = (DailyRecordBaseDao) pairResolveDailyDao.component1();
            String str = (String) pairResolveDailyDao.component2();
            dailyRecordBaseDao.insertReplaceAll(CollectionsKt.listOf(fitnessDbWriteBridge.newDailyEntity(str, this.$req)));
            Logger.i(FitnessDbWriteBridge.TAG, "insertFitnessData daily=ok persistKey=" + str + " time=" + this.$req.getTime());
            return new WriteResult(1);
        }
