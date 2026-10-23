// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 007-get_method_body.json
// Method: com.xiaomi.fit.fitness.persist.db.utils.FitnessDbQueryBridge.queryFitnessSummaryData.2.invokeSuspend
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            final String string;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FitnessDbQueryBridge fitnessDbQueryBridge = FitnessDbQueryBridge.INSTANCE;
            Logger.i(FitnessDbQueryBridge.TAG, "queryFitnessSummaryData input=" + fitnessDbQueryBridge.describeFitnessQuery(this.$q));
            String dataType = this.$q.getDataType();
            if (dataType != null && (string = StringsKt.trim((CharSequence) dataType).toString()) != null) {
                if (StringsKt.isBlank(string)) {
                    string = null;
                }
                if (string != null) {
                    YRNDatabaseMappings.HealthSummarySpec healthSummarySpecResolveHealthSummarySpec = YRNDatabaseMappings.INSTANCE.resolveHealthSummarySpec(this.$q.getTableName(), string);
                    if (healthSummarySpecResolveHealthSummarySpec == null) {
                        throw new IllegalArgumentException("unsupported summary query: table=" + this.$q.getTableName() + ", dataType=" + string);
                    }
                    Logger.i(FitnessDbQueryBridge.TAG, "queryFitnessSummaryData spec=" + healthSummarySpecResolveHealthSummarySpec);
                    if (Intrinsics.areEqual(healthSummarySpecResolveHealthSummarySpec.getTable(), "red_dot")) {
                        HomeDataType homeDataType = healthSummarySpecResolveHealthSummarySpec.getHomeDataType();
                        if (homeDataType == null) {
                            throw new IllegalArgumentException("red_dot query requires mapped data type");
                        }
                        List<RedDotEntity> listQueryRedDotListSync = FitnessSummaryDatabase.INSTANCE.getInstance().getRedDotDao().queryRedDotListSync(homeDataType, this.$q.getStartTimeSec(), this.$q.getEndTimeSec());
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listQueryRedDotListSync, 10));
                        for (RedDotEntity redDotEntity : listQueryRedDotListSync) {
                            arrayList.add(new FitnessRecord(redDotEntity.getSid(), string, redDotEntity.getTimeInZero(), redDotEntity.getTimeInZero(), "UTC", 0, "{}", null, 128, null));
                        }
                        FitnessQueryResult fitnessQueryResult = new FitnessQueryResult(arrayList);
                        Logger.i(FitnessDbQueryBridge.TAG, "queryFitnessSummaryData redDotResult=" + FitnessDbQueryBridge.INSTANCE.describeFitnessResult(fitnessQueryResult));
                        return fitnessQueryResult;
                    }
                    SupportSQLiteDatabase readableDatabase = FitnessSummaryDatabase.INSTANCE.getInstance().getOpenHelper().getReadableDatabase();
                    String str = this.$q.getSort() == Sort.ASCEND ? "ASC" : "DESC";
                    List listListOf = CollectionsKt.listOf((Object[]) new String[]{"timeInZero", "timeIn0TZ", FitnessDBConstants.COL_TIME_0_TZ, "time_in_zero", "time_in_0_tz", "time"});
                    String strBuildSummarySql = fitnessDbQueryBridge.buildSummarySql(readableDatabase, healthSummarySpecResolveHealthSummarySpec.getTable(), listListOf, this.$q.getStartTimeSec(), this.$q.getEndTimeSec(), str, fitnessDbQueryBridge.normalizeLimit(this.$q.getLimit()), this.$q.getSid(), healthSummarySpecResolveHealthSummarySpec.getDataTypeValue(), healthSummarySpecResolveHealthSummarySpec.getViewTagValue());
                    Logger.i(FitnessDbQueryBridge.TAG, "queryFitnessSummaryData sql=" + FitnessDbQueryBridge.shortSql$default(fitnessDbQueryBridge, strBuildSummarySql, 0, 2, null));
                    final String strPickFirstExistingColumn = fitnessDbQueryBridge.pickFirstExistingColumn(readableDatabase, healthSummarySpecResolveHealthSummarySpec.getTable(), listListOf);
                    if (strPickFirstExistingColumn == null) {
                        return new FitnessQueryResult(CollectionsKt.emptyList());
                    }
                    FitnessQueryResult fitnessQueryResult2 = new FitnessQueryResult(fitnessDbQueryBridge.querySummaryRows(readableDatabase, strBuildSummarySql, new Function1() { // from class: com.xiaomi.fit.fitness.persist.db.utils.a
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            return FitnessDbQueryBridge.AnonymousClass2.invokeSuspend$lambda$2(string, strPickFirstExistingColumn, (Cursor) obj2);
                        }
                    }));
                    Logger.i(FitnessDbQueryBridge.TAG, "queryFitnessSummaryData result=" + fitnessDbQueryBridge.describeFitnessResult(fitnessQueryResult2));
                    return fitnessQueryResult2;
                }
            }
            throw new IllegalArgumentException("summary query requires dataType");
        }
