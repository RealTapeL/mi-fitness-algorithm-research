// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 061-get_method_body-YRNDatabaseModule-queryFitnessData.json
    @ReactMethod
    public final void queryFitnessData(@NotNull ReadableMap query, @NotNull Callback callback) {
        Intrinsics.checkNotNullParameter(query, "query");
        Intrinsics.checkNotNullParameter(callback, "callback");
        final boolean zOptBoolean = optBoolean(query, "includeSource");
        executeQuery("queryFitnessData", query, callback, new Function0() { // from class: gvu
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CollectionsKt.emptyList();
            }
        }, new AnonymousClass2(this), new AnonymousClass3(null), new Function1() { // from class: hvu
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return YRNDatabaseModule.queryFitnessData$lambda$2(this.a, zOptBoolean, (FitnessDbQueryBridge.FitnessQueryResult) obj);
            }
        });
    }
