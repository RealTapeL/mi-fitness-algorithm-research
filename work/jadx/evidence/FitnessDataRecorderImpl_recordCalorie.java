// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 033-get_method_body-FitnessDataRecorderImpl-recordCalorie.json
    public final boolean recordCalorie(@NotNull String sid, int zoneOffsetInSec, @NotNull List<CaloriesItem> caloriesItemList) {
        Intrinsics.checkNotNullParameter(sid, "sid");
        Intrinsics.checkNotNullParameter(caloriesItemList, "caloriesItemList");
        return recordDailyData$default(this, "calories", sid, zoneOffsetInSec, caloriesItemList, false, false, 48, null);
    }
