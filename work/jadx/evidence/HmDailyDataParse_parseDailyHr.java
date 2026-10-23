// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 026-get_method_body-HmDailyDataParse-parseDailyHr.json
    private final boolean parseDailyHr(JSONObject jsonObject, String sid, long timeStamp) {
        boolean zRecordDailyHr;
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArrayOptJSONArray = jsonObject.optJSONArray("data");
            FitnessLogUtils.i(HMParseUtilKt.TAG, "HmDailyDataParse parseDailyHr dataArray.length=" + (jSONArrayOptJSONArray != null ? Integer.valueOf(jSONArrayOptJSONArray.length()) : null));
            if (jSONArrayOptJSONArray == null) {
                return true;
            }
            if (jSONArrayOptJSONArray.length() > 0) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(0);
                int iOptInt = jSONObjectOptJSONObject.optInt(StatConstants.PARAM_START);
                int iOptInt2 = jSONObjectOptJSONObject.optInt(BroadcastsKt.SILENT_PUSHING_STOP);
                byte[] bArrDecode = Base64.decode(jSONObjectOptJSONObject.optString("value"), 0);
                int i = iOptInt2 - iOptInt;
                int length = bArrDecode.length / (i + 1);
                if (length > 3 && i >= 0) {
                    int i2 = 0;
                    while (true) {
                        long j = timeStamp + ((long) ((iOptInt + i2) * 60));
                        int iByteToInt = HMParseUtilKt.byteToInt(bArrDecode[(length * i2) + 3]);
                        if (1 <= iByteToInt && iByteToInt < 255) {
                            arrayList.add(new HrItem(j, iByteToInt));
                        }
                        if (i2 == i) {
                            break;
                        }
                        i2++;
                    }
                }
            }
            if (arrayList.isEmpty()) {
                zRecordDailyHr = true;
            } else {
                FitnessDataRecorder sendDataRecord = getSendDataRecord();
                int i3 = this.tzInsec;
                HrItem[] hrItemArr = (HrItem[]) arrayList.toArray(new HrItem[0]);
                zRecordDailyHr = sendDataRecord.recordDailyHr(sid, i3, (HrItem[]) Arrays.copyOf(hrItemArr, hrItemArr.length));
            }
            FitnessLogUtils.d(HMParseUtilKt.TAG, "HmDailyDataParse parseDailyHr result=" + zRecordDailyHr);
            return zRecordDailyHr;
        } catch (Exception e) {
            e.printStackTrace();
            FitnessLogUtils.e(HMParseUtilKt.TAG, "parseDailyHr exception=" + Unit.INSTANCE);
            return true;
        }
    }
