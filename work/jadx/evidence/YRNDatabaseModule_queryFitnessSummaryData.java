// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 062-get_method_body-YRNDatabaseModule-queryFitnessSummaryData.json
    @ReactMethod
    public final void queryFitnessSummaryData(@NotNull ReadableMap query, @NotNull Callback callback) {
        Intrinsics.checkNotNullParameter(query, "query");
        Intrinsics.checkNotNullParameter(callback, "callback");
        final boolean zOptBoolean = optBoolean(query, "includeSource");
        executeQuery("queryFitnessSummaryData", query, callback, new Function0() { // from class: suu
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CollectionsKt.emptyList();
            }
        }, new AnonymousClass2(this), new AnonymousClass3(null), new Function1() { // from class: tuu
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return YRNDatabaseModule.queryFitnessSummaryData$lambda$5(this.a, zOptBoolean, (FitnessDbQueryBridge.FitnessQueryResult) obj);
            }
        });
    }
