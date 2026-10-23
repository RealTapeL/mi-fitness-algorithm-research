// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 028-get_method_body-HmManualHrParse-parse.json
    @Override // com.xiaomi.fit.fitness.device.hm.parse.repo.IHmDataParseByCategory
    @Nullable
    public Object parse(@NotNull String str, @NotNull String str2, @NotNull Continuation<? super ParseResultItem> continuation) {
        int i;
        long j = 0;
        byte b2 = 0;
        try {
            JSONArray jSONArray = new JSONArray(str2);
            int length = jSONArray.length();
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                try {
                    JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
                    long jOptLong = jSONObjectOptJSONObject.optLong("generatedTime");
                    int iOptInt = jSONObjectOptJSONObject.optInt("timeZone");
                    long jOptLong2 = jSONObjectOptJSONObject.optLong(PaiData.LAST_SYNC_TIME);
                    byte[] bArrDecode = Base64.decode(jSONObjectOptJSONObject.optString("heartRateData"), b2);
                    Intrinsics.checkNotNull(bArrDecode);
                    HrItem hrItem = new HrItem(jOptLong, (bArrDecode.length == 0 ? (byte) 1 : b2) == 0 ? bArrDecode[b2] : b2);
                    JSONArray jSONArray2 = jSONArray;
                    boolean zRecordManualHr = getSendDataRecord().recordManualHr(str, FitnessDateUtils.getTzOffsetInSecBy15min(iOptInt), hrItem);
                    FitnessLogUtils.i(HMParseUtilKt.TAG, "HmManualHrParse item=" + hrItem + "**time=" + jOptLong + "**result=" + zRecordManualHr);
                    if (!zRecordManualHr) {
                        return new ParseResultItem(false, 0, 0L, 6, null);
                    }
                    if (j < jOptLong2) {
                        i3 = iOptInt;
                        j = jOptLong2;
                    }
                    i2++;
                    jSONArray = jSONArray2;
                    b2 = 0;
                } catch (Exception e) {
                    e = e;
                    i = i3;
                    FitnessLogUtils.e(HMParseUtilKt.TAG, "HmManualHrParse " + e.getMessage(), e);
                    return new ParseResultItem(true, i, j);
                }
            }
            return new ParseResultItem(true, i3, j);
        } catch (Exception e2) {
            e = e2;
            i = 0;
        }
    }
