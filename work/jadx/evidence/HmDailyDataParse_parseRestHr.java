// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 027-get_method_body-HmDailyDataParse-parseRestHr.json
    private final boolean parseRestHr(JSONObject slp, String sid, long timeStamp) {
        try {
            int iOptInt = slp.optInt("rhr");
            if (iOptInt <= 0 || HMParseUtilKt.isSupportSyncRestHr(sid)) {
                FitnessLogUtils.i(HMParseUtilKt.TAG, "HmDailyDataParse parseRestHr value=" + iOptInt);
                return true;
            }
            RestHrItem restHrItem = new RestHrItem(timeStamp, iOptInt);
            boolean zRecordRestHr = getSendDataRecord().recordRestHr(sid, this.tzInsec, restHrItem);
            FitnessLogUtils.i(HMParseUtilKt.TAG, "HmDailyDataParse parseRestHr result=" + zRecordRestHr + "**item=" + restHrItem);
            return zRecordRestHr;
        } catch (Exception e) {
            e.printStackTrace();
            FitnessLogUtils.e(HMParseUtilKt.TAG, "parseRestHr exception=" + Unit.INSTANCE);
            return true;
        }
    }
