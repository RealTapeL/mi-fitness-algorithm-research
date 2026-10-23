// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 073-get_method_body-FitnessDBDataUpdater-recordDailyRecord.json
    public final boolean recordDailyRecord(@NotNull String persistKey, @NotNull String sid, @NotNull List<DailyRecordItemModel> itemList, boolean allowSwitchProcess, boolean allowUpdate) {
        Intrinsics.checkNotNullParameter(persistKey, "persistKey");
        Intrinsics.checkNotNullParameter(sid, "sid");
        Intrinsics.checkNotNullParameter(itemList, "itemList");
        if (itemList.isEmpty()) {
            return true;
        }
        if (!allowSwitchProcess || !checkForceRemoteService()) {
            return DailyRecordDaoUtils.INSTANCE.recordDailyRecordToDB(persistKey, sid, itemList, allowUpdate);
        }
        FitnessLogUtils.i(TAG, "mRemoteService#recordDailyRecordToDB");
        a mRemoteService = getMRemoteService();
        Intrinsics.checkNotNull(mRemoteService);
        return mRemoteService.recordDailyRecordToDB(persistKey, sid, itemList, allowUpdate);
    }
