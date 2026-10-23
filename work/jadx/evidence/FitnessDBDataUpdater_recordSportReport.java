// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 092-get_method_body-FitnessDBDataUpdater-recordSportReport.json
    public final boolean recordSportReport(@NotNull String sid, @NotNull SportBasicReport sportReport) {
        Intrinsics.checkNotNullParameter(sid, "sid");
        Intrinsics.checkNotNullParameter(sportReport, "sportReport");
        SportDBKey sportDBKey = DataExtKt.toSportDBKey(sportReport, sid);
        if (sportDBKey == null) {
            return false;
        }
        String sid2 = sportReport.getSid();
        Long dbKeyTime = sportReport.getDbKeyTime();
        sportReport.setDbKeyTime(null);
        sportReport.setSid(null);
        if (sportReport.getEntityStartTime() != null && sportReport.getEntityEndTime() != null) {
            Long entityStartTime = sportReport.getEntityStartTime();
            Intrinsics.checkNotNull(entityStartTime);
            sportReport.setStartTime(entityStartTime.longValue());
            Long entityEndTime = sportReport.getEntityEndTime();
            Intrinsics.checkNotNull(entityEndTime);
            sportReport.setEndTime(entityEndTime.longValue());
        }
        FitnessPersistUtils fitnessPersistUtils = FitnessPersistUtils.INSTANCE;
        sportReport.setExtraFields(fitnessPersistUtils.flatMap(sportReport.getExtraFields()));
        String json = fitnessPersistUtils.toJson(sportReport);
        sportReport.setSid(sid2);
        sportReport.setDbKeyTime(dbKeyTime);
        if (!checkForceRemoteService()) {
            return SportReportDaoUtils.INSTANCE.recordSportReportToDB(sportDBKey, json);
        }
        FitnessLogUtils.i(TAG, "mRemoteService#recordSportReportToDB");
        a mRemoteService = getMRemoteService();
        Intrinsics.checkNotNull(mRemoteService);
        return mRemoteService.recordSportReportToDB(sportDBKey, json);
    }
