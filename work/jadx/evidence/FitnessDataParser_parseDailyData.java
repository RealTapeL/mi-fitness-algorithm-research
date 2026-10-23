// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 075-get_method_body-FitnessDataParser-parseDailyData.json
    @Nullable
    public final FitnessParseResult parseDailyData(@NotNull byte[] byteArray, int dailyType, int fileType) {
        Intrinsics.checkNotNullParameter(byteArray, "byteArray");
        if (byteArray.length != 0) {
            FitnessDataId dailyDataId = FitnessDataHeader.INSTANCE.getDailyDataId(byteArray, dailyType, fileType);
            if (dailyDataId != null) {
                return parse(dailyDataId, byteArray);
            }
            FitnessLogUtils.w(TAG, "getDailyDataId failed");
            return null;
        }
        FitnessLogUtils.w(TAG, "data is empty, dailyType = " + dailyType + ", fileType = " + fileType);
        return null;
    }
