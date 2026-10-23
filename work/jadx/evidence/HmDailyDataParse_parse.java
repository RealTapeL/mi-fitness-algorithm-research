// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 024-get_method_body-HmDailyDataParse-parse.json
    /* JADX WARN: Code duplicated, block: B:34:0x00b8  */
    @Override // com.xiaomi.fit.fitness.device.hm.parse.repo.IHmDataParseByCategory
    @Nullable
    public Object parse(@NotNull String str, @NotNull String str2, @NotNull Continuation<? super ParseResultItem> continuation) {
        Exception exc;
        Integer numBoxInt;
        long jOptLong = 0;
        int i = 0;
        z = false;
        z = false;
        z = false;
        z = false;
        boolean z = false;
        try {
            JSONObject jSONObject = new JSONObject(str2);
            int iOptInt = jSONObject.optInt("tz");
            try {
                jOptLong = jSONObject.optLong(PaiData.LAST_SYNC_TIME);
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("metadata");
                if (jSONArrayOptJSONArray != null) {
                    try {
                        numBoxInt = Boxing.boxInt(jSONArrayOptJSONArray.length());
                    } catch (Exception e) {
                        exc = e;
                    }
                } else {
                    numBoxInt = null;
                }
                FitnessLogUtils.i(HMParseUtilKt.TAG, "HmDailyDataParse metadata length=" + numBoxInt);
                try {
                    if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() < 1) {
                        return new ParseResultItem(true, iOptInt, jOptLong);
                    }
                    this.tzInsec = FitnessDateUtils.getTzOffsetInSecBy15min(iOptInt);
                    JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(0);
                    JSONObject jSONObject2 = new JSONObject(jSONObjectOptJSONObject.optString(b.d));
                    String strOptString = jSONObjectOptJSONObject.optString("date");
                    FitnessDateUtils fitnessDateUtils = FitnessDateUtils.INSTANCE;
                    Intrinsics.checkNotNull(strOptString);
                    long jConvertDateStrToZeroTime = fitnessDateUtils.convertDateStrToZeroTime(strOptString, "yyyy-MM-dd");
                    JSONObject jSONObjectOptJSONObject2 = jSONObject2.optJSONObject(StepsInfo.KEY_STEP_INFO);
                    JSONObject jSONObjectOptJSONObject3 = jSONObject2.optJSONObject(SleepInfo.KEY_SLEEP_INFO);
                    Intrinsics.checkNotNull(jSONObjectOptJSONObject2);
                    if (parseActiveStage(jSONObjectOptJSONObject2, str, jConvertDateStrToZeroTime)) {
                        Intrinsics.checkNotNull(jSONObjectOptJSONObject3);
                        if (parseNightSleep(jSONObjectOptJSONObject3, str, iOptInt, jConvertDateStrToZeroTime)) {
                            if (parseDaySleep(jSONObjectOptJSONObject3, str, iOptInt, jConvertDateStrToZeroTime) && parseRestHr(jSONObjectOptJSONObject3, str, jConvertDateStrToZeroTime)) {
                                Intrinsics.checkNotNull(jSONObjectOptJSONObject);
                                if (parseStep(jSONObjectOptJSONObject, str, jConvertDateStrToZeroTime) && parseDailyHr(jSONObjectOptJSONObject, str, jConvertDateStrToZeroTime)) {
                                    z = true;
                                }
                            }
                        }
                    }
                    FitnessLogUtils.i(HMParseUtilKt.TAG, "HmDailyDataParse parseResult=" + z + "**time=" + jConvertDateStrToZeroTime);
                    return new ParseResultItem(z, iOptInt, jOptLong);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
            }
            exc = e;
            i = iOptInt;
        } catch (Exception e4) {
            exc = e4;
        }
        FitnessLogUtils.e(HMParseUtilKt.TAG, "HmDailyDataParse " + exc.getMessage(), exc);
        return new ParseResultItem(true, i, jOptLong);
    }
