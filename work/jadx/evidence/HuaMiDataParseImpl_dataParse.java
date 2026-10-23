// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 055-get_method_body-HuaMiDataParseImpl-dataParse.json
    @Override // com.xiaomi.fit.fitness.device.hm.api.IHuamiDataParse
    @Nullable
    public Object dataParse(@NotNull HuaMiParseType huaMiParseType, @NotNull String str, @NotNull String str2, @NotNull Continuation<? super ParseResultItem> continuation) {
        FitnessLogUtils.i(HMParseUtilKt.TAG, "dataParse type=" + huaMiParseType.name() + "**data=" + str2);
        return getParseInstance(huaMiParseType).parse(str, str2, continuation);
    }
