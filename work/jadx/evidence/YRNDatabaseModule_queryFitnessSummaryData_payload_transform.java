// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 085-get_method_body-YRNDatabaseModule-queryFitnessSummaryData$lambda$5.json
    /* JADX INFO: Access modifiers changed from: private */
    public static final Object queryFitnessSummaryData$lambda$5(YRNDatabaseModule yRNDatabaseModule, boolean z, FitnessDbQueryBridge.FitnessQueryResult result) {
        Intrinsics.checkNotNullParameter(result, "result");
        List<FitnessDbQueryBridge.FitnessRecord> data = result.getData();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(data, 10));
        Iterator<T> it = data.iterator();
        while (it.hasNext()) {
            arrayList.add(yRNDatabaseModule.toFitnessPayload((FitnessDbQueryBridge.FitnessRecord) it.next(), z, true));
        }
        return arrayList;
    }
