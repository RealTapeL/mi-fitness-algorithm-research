// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 025-get_method_body-HmDailyDataParse-parseStep.json
    private final boolean parseStep(JSONObject jsonObject, String sid, long timeStamp) {
        boolean z;
        boolean zRecordStep;
        int i;
        int i2;
        int i3;
        boolean z2 = true;
        try {
            ArrayList arrayList = new ArrayList();
            JSONObject jSONObjectOptJSONObject = new JSONObject(jsonObject.optString(b.d)).optJSONObject(StepsInfo.KEY_STEP_INFO);
            float fOptInt = jSONObjectOptJSONObject.optInt(StepsInfo.KEY_STEPS);
            int iOptInt = jSONObjectOptJSONObject.optInt("realDis");
            if (iOptInt <= 0) {
                iOptInt = jSONObjectOptJSONObject.optInt(StepsInfo.KEY_DISTANCE);
            }
            JSONArray jSONArrayOptJSONArray = jsonObject.optJSONArray("data");
            int iOptInt2 = jSONObjectOptJSONObject.optInt("stepCal");
            FitnessLogUtils.i(HMParseUtilKt.TAG, "HmDailyDataParse parseStep stepCal=" + iOptInt2 + "**dayStep=" + fOptInt + ",dayDistance=" + iOptInt + "**dataArray.length=" + (jSONArrayOptJSONArray != null ? Integer.valueOf(jSONArrayOptJSONArray.length()) : null));
            if (jSONArrayOptJSONArray == null) {
                return true;
            }
            if (jSONArrayOptJSONArray.length() > 0) {
                JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray.optJSONObject(0);
                int iOptInt3 = jSONObjectOptJSONObject2.optInt(StatConstants.PARAM_START);
                int iOptInt4 = jSONObjectOptJSONObject2.optInt(BroadcastsKt.SILENT_PUSHING_STOP);
                byte[] bArrDecode = Base64.decode(jSONObjectOptJSONObject2.optString("value"), 0);
                int i4 = iOptInt4 - iOptInt3;
                int i5 = i4 + 1;
                int length = bArrDecode.length / i5;
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                if (i4 >= 0) {
                    int i6 = 0;
                    int i7 = 0;
                    int i8 = 0;
                    while (true) {
                        z = z2;
                        try {
                            int iByteToInt = HMParseUtilKt.byteToInt(bArrDecode[(length * i6) + 2]);
                            float f = iByteToInt / fOptInt;
                            byte[] bArr = bArrDecode;
                            int i9 = (int) (iOptInt * f);
                            int i10 = (int) (f * iOptInt2);
                            i7 += i9;
                            i8 += i10;
                            arrayList2.add(Integer.valueOf(iByteToInt));
                            arrayList3.add(Integer.valueOf(i9));
                            arrayList4.add(Integer.valueOf(i10));
                            if (i6 == i4) {
                                break;
                            }
                            i6++;
                            bArrDecode = bArr;
                            z2 = z;
                        } catch (Exception e) {
                            e = e;
                        }
                    }
                    i = i7;
                    i2 = i8;
                } else {
                    z = true;
                    i = 0;
                    i2 = 0;
                }
                int i11 = iOptInt - i;
                int i12 = iOptInt2 - i2;
                FitnessLogUtils.i(HMParseUtilKt.TAG, "HmDailyDataParse parseStep tocalCal=" + i2 + "**calInterval=" + i12 + "**totalDistance=" + i + ",interval=" + i11 + ",byteLength=" + length);
                int i13 = i11 / i5;
                int i14 = i11 % i5;
                int i15 = i12 / i5;
                int i16 = i12 % i5;
                if (i4 >= 0) {
                    int i17 = 0;
                    i3 = 0;
                    while (true) {
                        long j = timeStamp + ((long) ((iOptInt3 + i17) * 60));
                        Object obj = arrayList2.get(i17);
                        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
                        int iIntValue = ((Number) obj).intValue();
                        if (iIntValue != 0) {
                            int iIntValue2 = ((Number) arrayList3.get(i17)).intValue() + i13;
                            if (i14 > 0) {
                                i14--;
                                iIntValue2++;
                            }
                            int i18 = iIntValue2;
                            int iIntValue3 = ((Number) arrayList4.get(i17)).intValue() + i15;
                            if (i16 > 0) {
                                i16--;
                                iIntValue3++;
                            }
                            StepItem stepItem = new StepItem(j, iIntValue, i18, iIntValue3);
                            arrayList.add(stepItem);
                            i3 += iIntValue;
                            FitnessLogUtils.d(HMParseUtilKt.TAG, "HmDailyDataParse parseStep item=" + stepItem);
                        }
                        if (i17 == i4) {
                            break;
                        }
                        i17++;
                    }
                } else {
                    i3 = 0;
                }
                FitnessLogUtils.d(HMParseUtilKt.TAG, "HmDailyDataParse totalStep=" + i3);
            } else {
                z = true;
            }
            if (arrayList.isEmpty()) {
                zRecordStep = z;
            } else {
                FitnessDataRecorder sendDataRecord = getSendDataRecord();
                try {
                    int i19 = this.tzInsec;
                    StepItem[] stepItemArr = (StepItem[]) arrayList.toArray(new StepItem[0]);
                    zRecordStep = sendDataRecord.recordStep(sid, i19, (StepItem[]) Arrays.copyOf(stepItemArr, stepItemArr.length));
                } catch (Exception e2) {
                    e = e2;
                }
            }
            FitnessLogUtils.d(HMParseUtilKt.TAG, "HmDailyDataParse result=" + zRecordStep);
            return zRecordStep;
        } catch (Exception e3) {
            e = e3;
            z = z2;
        }
        e.printStackTrace();
        FitnessLogUtils.e(HMParseUtilKt.TAG, "parseStep exception=" + Unit.INSTANCE);
        return z;
    }
