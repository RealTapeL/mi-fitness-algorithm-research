// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 084-get_method_body-YRNDatabaseModule-queryFitnessData$lambda$2.json
    /* JADX INFO: Access modifiers changed from: private */
    public static final Object queryFitnessData$lambda$2(YRNDatabaseModule yRNDatabaseModule, boolean z, FitnessDbQueryBridge.FitnessQueryResult result) {
        Intrinsics.checkNotNullParameter(result, "result");
        List<FitnessDbQueryBridge.FitnessRecord> data = result.getData();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(data, 10));
        Iterator<T> it = data.iterator();
        while (it.hasNext()) {
            YRNDatabaseModule yRNDatabaseModule2 = yRNDatabaseModule;
            arrayList.add(toFitnessPayload$default(yRNDatabaseModule2, (FitnessDbQueryBridge.FitnessRecord) it.next(), z, false, 2, null));
            yRNDatabaseModule = yRNDatabaseModule2;
        }
        return arrayList;
    }
