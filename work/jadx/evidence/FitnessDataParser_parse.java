// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 074-get_method_body-FitnessDataParser-parse.json
    @Nullable
    public final FitnessParseResult parse(@NotNull FitnessDataId dataId, @NotNull byte[] data) {
        FitnessParseResult fitnessParseResultNativeParse;
        Intrinsics.checkNotNullParameter(dataId, "dataId");
        Intrinsics.checkNotNullParameter(data, "data");
        FitnessLogUtils.i(TAG, "parse dataId(" + dataId + ")");
        if (data.length < dataId.getServerDataIdLen() + 1) {
            return new FitnessParseResult.Builder().dataInvalidVersion(true).build();
        }
        FitnessDataBaseParser dataParserInstance = getDataParserInstance(dataId);
        if (dataParserInstance == null) {
            FitnessLogUtils.w(TAG, "no support dataParser " + dataId);
            return null;
        }
        if (isSchemaVersionSupport(dataId)) {
            FitnessLogUtils.i(TAG, "parse schemaParse");
            fitnessParseResultNativeParse = schemaParse(dataId, dataParserInstance, data);
        } else {
            FitnessLogUtils.i(TAG, "parse nativeParse");
            fitnessParseResultNativeParse = nativeParse(dataId, dataParserInstance, data);
        }
        if ((fitnessParseResultNativeParse != null ? fitnessParseResultNativeParse.getAllDaySleepReport() : null) != null) {
            splitAllDaySleepBytes(data, fitnessParseResultNativeParse.getAllDaySleepReport());
        } else {
            FitnessParserSDK.INSTANCE.getSchemaEnvironment$fitness_schema_parser_release().debugExportParseResult(dataId, fitnessParseResultNativeParse);
        }
        if (fitnessParseResultNativeParse != null) {
            fitnessParseResultNativeParse.setDataIdVersion(Integer.valueOf(dataId.getVersion()));
        }
        FitnessLogUtils.d(TAG, "load game mode sport schema data test - sportRecord:" + (fitnessParseResultNativeParse != null ? fitnessParseResultNativeParse.getSportRecord() : null) + " , sportReport:" + (fitnessParseResultNativeParse != null ? fitnessParseResultNativeParse.getSportReport() : null));
        return fitnessParseResultNativeParse;
    }
