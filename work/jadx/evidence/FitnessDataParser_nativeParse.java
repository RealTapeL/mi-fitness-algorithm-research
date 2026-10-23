// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 076-get_method_body-FitnessDataParser-nativeParse.json
    private final FitnessParseResult nativeParse(FitnessDataId dataId, FitnessDataBaseParser parser, byte[] data) {
        FitnessBinaryData nativeBinaryData = parseNativeBinaryData(dataId, data);
        if (nativeBinaryData.getDataValid() != null) {
            return parser.nativeParse(nativeBinaryData);
        }
        FitnessLogUtils.w(TAG, "no support dataHeader " + dataId);
        return new FitnessParseResult.Builder().dataInvalidVersion(true).build();
    }
