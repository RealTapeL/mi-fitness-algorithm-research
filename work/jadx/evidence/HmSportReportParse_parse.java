// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 079-get_method_body-HmSportReportParse-parse.json
    /* JADX WARN: Code duplicated, block: B:47:0x023c  */
    /* JADX WARN: Code duplicated, block: B:69:0x028b A[Catch: Exception -> 0x0251, TryCatch #3 {Exception -> 0x0251, blocks: (B:52:0x024c, B:58:0x025e, B:60:0x0263, B:62:0x0271, B:64:0x0276, B:66:0x0283, B:82:0x02c4, B:84:0x02cc, B:86:0x02e6, B:88:0x02eb, B:90:0x02f3, B:92:0x0302, B:94:0x0328, B:96:0x0349, B:98:0x0359, B:100:0x037e, B:102:0x038e, B:104:0x03b3, B:106:0x03c3, B:108:0x03e8, B:110:0x03f8, B:112:0x041d, B:114:0x042d, B:116:0x0451, B:118:0x0461, B:119:0x0464, B:125:0x047e, B:127:0x04a9, B:129:0x04cc, B:131:0x04d8, B:132:0x04e1, B:126:0x0494, B:69:0x028b, B:71:0x0299, B:73:0x029e, B:75:0x02ab, B:77:0x02b0, B:79:0x02bd), top: B:146:0x0232 }] */
    /* JADX WARN: Code duplicated, block: B:70:0x0298  */
    /* JADX WARN: Code duplicated, block: B:73:0x029e A[Catch: Exception -> 0x0251, TryCatch #3 {Exception -> 0x0251, blocks: (B:52:0x024c, B:58:0x025e, B:60:0x0263, B:62:0x0271, B:64:0x0276, B:66:0x0283, B:82:0x02c4, B:84:0x02cc, B:86:0x02e6, B:88:0x02eb, B:90:0x02f3, B:92:0x0302, B:94:0x0328, B:96:0x0349, B:98:0x0359, B:100:0x037e, B:102:0x038e, B:104:0x03b3, B:106:0x03c3, B:108:0x03e8, B:110:0x03f8, B:112:0x041d, B:114:0x042d, B:116:0x0451, B:118:0x0461, B:119:0x0464, B:125:0x047e, B:127:0x04a9, B:129:0x04cc, B:131:0x04d8, B:132:0x04e1, B:126:0x0494, B:69:0x028b, B:71:0x0299, B:73:0x029e, B:75:0x02ab, B:77:0x02b0, B:79:0x02bd), top: B:146:0x0232 }] */
    /* JADX WARN: Code duplicated, block: B:74:0x02aa  */
    /* JADX WARN: Code duplicated, block: B:77:0x02b0 A[Catch: Exception -> 0x0251, TryCatch #3 {Exception -> 0x0251, blocks: (B:52:0x024c, B:58:0x025e, B:60:0x0263, B:62:0x0271, B:64:0x0276, B:66:0x0283, B:82:0x02c4, B:84:0x02cc, B:86:0x02e6, B:88:0x02eb, B:90:0x02f3, B:92:0x0302, B:94:0x0328, B:96:0x0349, B:98:0x0359, B:100:0x037e, B:102:0x038e, B:104:0x03b3, B:106:0x03c3, B:108:0x03e8, B:110:0x03f8, B:112:0x041d, B:114:0x042d, B:116:0x0451, B:118:0x0461, B:119:0x0464, B:125:0x047e, B:127:0x04a9, B:129:0x04cc, B:131:0x04d8, B:132:0x04e1, B:126:0x0494, B:69:0x028b, B:71:0x0299, B:73:0x029e, B:75:0x02ab, B:77:0x02b0, B:79:0x02bd), top: B:146:0x0232 }] */
    /* JADX WARN: Code duplicated, block: B:78:0x02bc  */
    @Override // com.xiaomi.fit.fitness.device.hm.parse.repo.IHmDataParseByCategory
    @Nullable
    public Object parse(@NotNull String str, @NotNull String str2, @NotNull Continuation<? super ParseResultItem> continuation) {
        Integer numBoxInt;
        double d;
        Float fBoxFloat;
        Integer numBoxInt2;
        Integer numBoxInt3;
        Integer numBoxInt4;
        int currentTZOffsetIn15min = FitnessDateUtils.getCurrentTZOffsetIn15min();
        long jOptLong = 0;
        try {
            JSONObject jSONObject = new JSONObject(str2);
            jOptLong = jSONObject.optLong(PaiData.LAST_SYNC_TIME);
            long jObtainLongReturnNotNull = JsonUtilKt.obtainLongReturnNotNull(jSONObject, "trackid");
            int iOptInt = jSONObject.optInt("type");
            int iSwitchHmToMiProtoSportType = HMParseUtilKt.switchHmToMiProtoSportType(iOptInt);
            FitnessDataId.Builder builder = new FitnessDataId.Builder();
            builder.setFileType(1);
            builder.setVersion(2);
            builder.setDataType(1);
            builder.setTzIn15Min(currentTZOffsetIn15min);
            builder.setTimeStamp(jObtainLongReturnNotNull);
            builder.setSportType(iSwitchHmToMiProtoSportType);
            SportBasicReport sportBasicReport = new SportBasicReport(builder.build());
            sportBasicReport.setSportType(HMParseUtilKt.matchHundredSportType(iOptInt));
            sportBasicReport.setStartTime(jObtainLongReturnNotNull);
            sportBasicReport.setEndTime(JsonUtilKt.obtainLongReturnNotNull(jSONObject, "end_time"));
            sportBasicReport.setDuration(JsonUtilKt.obtainIntReturnNotNull(jSONObject, "run_time"));
            try {
                if (!hasNotDistance(iSwitchHmToMiProtoSportType)) {
                    sportBasicReport.setDistance(JsonUtilKt.obtainInt(jSONObject, StepsInfo.KEY_DISTANCE));
                }
                Integer numObtainValidInt = JsonUtilKt.obtainValidInt(jSONObject, "target_type");
                Integer numObtainInt = JsonUtilKt.obtainInt(jSONObject, "target_value");
                if (numObtainValidInt != null && numObtainInt != null) {
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    linkedHashMap.put(numObtainValidInt, numObtainInt.toString());
                    sportBasicReport.setTargetValue(linkedHashMap);
                }
                sportBasicReport.setCalories(JsonUtilKt.obtainInt(jSONObject, "calorie"));
                sportBasicReport.setAvgHr(JsonUtilKt.obtainIntFormStr(jSONObject, "avg_heart_rate"));
                sportBasicReport.setMaxHr(JsonUtilKt.obtainInt(jSONObject, "max_heart_rate"));
                sportBasicReport.setMinHr(JsonUtilKt.obtainValidInt(jSONObject, "min_heart_rate"));
                sportBasicReport.setRiseHeight(JsonUtilKt.obtainFloat(jSONObject, "altitude_ascend"));
                sportBasicReport.setFallHeight(JsonUtilKt.obtainFloat(jSONObject, "altitude_descend"));
                sportBasicReport.setMaxHeight(JsonUtilKt.obtainFloat(jSONObject, "max_altitude"));
                sportBasicReport.setMinHeight(JsonUtilKt.obtainFloat(jSONObject, "min_altitude"));
                Float fObtainFloatFromInt = JsonUtilKt.obtainFloatFromInt(jSONObject, "te");
                sportBasicReport.setTrainEffect(fObtainFloatFromInt != null ? Boxing.boxFloat(fObtainFloatFromInt.floatValue() / 10.0f) : null);
                Float fObtainFloatFromInt2 = JsonUtilKt.obtainFloatFromInt(jSONObject, "anaerobic_te");
                sportBasicReport.setAnaerobicTrainEffect(fObtainFloatFromInt2 != null ? Boxing.boxFloat(fObtainFloatFromInt2.floatValue() / 10.0f) : null);
                sportBasicReport.setSteps(JsonUtilKt.obtainInt(jSONObject, "total_step"));
                int protoType = sportBasicReport.getProtoType();
                if (protoType == 6 || protoType == 7 || protoType == 23) {
                    sportBasicReport.setMaxCadence(JsonUtilKt.obtainValidInt(jSONObject, "max_cadence"));
                    sportBasicReport.setAvgCadence(JsonUtilKt.obtainValidInt(jSONObject, "avg_cadence"));
                } else {
                    sportBasicReport.setMaxCadence(JsonUtilKt.obtainValidInt(jSONObject, "max_frequency"));
                    sportBasicReport.setAvgCadence(JsonUtilKt.obtainValidInt(jSONObject, "avg_frequency"));
                    sportBasicReport.setAvgStride(JsonUtilKt.obtainValidInt(jSONObject, "avg_stride_length"));
                }
                sportBasicReport.setTotalClimbing(JsonUtilKt.obtainLong(jSONObject, "distance_ascend"));
                sportBasicReport.setRiseClimbDuration(JsonUtilKt.obtainValidInt(jSONObject, "climb_dis_ascend_time"));
                sportBasicReport.setFallClimbDuration(JsonUtilKt.obtainValidInt(jSONObject, "climb_dis_descend_time"));
                sportBasicReport.setStrokeCount(JsonUtilKt.obtainValidInt(jSONObject, "total_strokes"));
                sportBasicReport.setPoolWidth(JsonUtilKt.obtainValidInt(jSONObject, "swim_pool_length"));
                Integer numObtainInt2 = JsonUtilKt.obtainInt(jSONObject, "swim_style");
                sportBasicReport.setMainPosture(numObtainInt2 != null ? Boxing.boxInt(HMParseUtilKt.matchSwimType(numObtainInt2.intValue())) : null);
                sportBasicReport.setTurnCount(JsonUtilKt.obtainValidInt(jSONObject, "total_trips"));
                Double dObtainValidDouble = JsonUtilKt.obtainValidDouble(jSONObject, "avg_stroke_speed");
                sportBasicReport.setAvgStrokeFreq(dObtainValidDouble != null ? Boxing.boxInt(MathKt.roundToInt(dObtainValidDouble.doubleValue() * ((double) 60))) : null);
                Double dObtainValidDouble2 = JsonUtilKt.obtainValidDouble(jSONObject, "max_stroke_speed");
                sportBasicReport.setMaxStrokeFreq(dObtainValidDouble2 != null ? Boxing.boxInt(MathKt.roundToInt(dObtainValidDouble2.doubleValue() * ((double) 60))) : null);
                sportBasicReport.setSwingCount(JsonUtilKt.obtainInt(jSONObject, "strokes"));
                sportBasicReport.setForehandSwingCount(JsonUtilKt.obtainInt(jSONObject, "fore_hand"));
                sportBasicReport.setBackhandSwingCount(JsonUtilKt.obtainInt(jSONObject, "back_hand"));
                sportBasicReport.setServeBallCount(JsonUtilKt.obtainInt(jSONObject, "serve"));
                sportBasicReport.setTotalRestDuration(JsonUtilKt.obtainInt(jSONObject, "rope_skipping_rest_time"));
                sportBasicReport.setGroupCount(JsonUtilKt.obtainInt(jSONObject, "total_group"));
                Double dObtainDouble = JsonUtilKt.obtainDouble(jSONObject, "min_pace");
                Double dObtainDouble2 = JsonUtilKt.obtainDouble(jSONObject, "max_pace");
                Double dObtainDoubleFromStr = JsonUtilKt.obtainDoubleFromStr(jSONObject, "avg_pace");
                try {
                    if (sportBasicReport.getSportType() != 9) {
                        try {
                            if (sportBasicReport.getSportType() == 10) {
                                jOptLong = jOptLong;
                                if (dObtainDouble != null) {
                                    numBoxInt2 = Boxing.boxInt((int) (((double) 100) * dObtainDouble.doubleValue()));
                                } else {
                                    numBoxInt2 = null;
                                }
                                sportBasicReport.setMaxPace(numBoxInt2);
                                if (dObtainDouble2 != null) {
                                    numBoxInt3 = Boxing.boxInt((int) (dObtainDouble2.doubleValue() * ((double) 100)));
                                } else {
                                    numBoxInt3 = null;
                                }
                                sportBasicReport.setMinPace(numBoxInt3);
                                if (dObtainDoubleFromStr != null) {
                                    numBoxInt4 = Boxing.boxInt((int) (dObtainDoubleFromStr.doubleValue() * ((double) 100)));
                                } else {
                                    numBoxInt4 = null;
                                }
                                sportBasicReport.setAvgPace(numBoxInt4);
                            } else {
                                sportBasicReport.setMaxPace(dObtainDouble != null ? Boxing.boxInt((int) (((double) 1000) * dObtainDouble.doubleValue())) : null);
                                if (dObtainDouble2 != null) {
                                    numBoxInt = Boxing.boxInt((int) (((double) 1000) * dObtainDouble2.doubleValue()));
                                } else {
                                    numBoxInt = null;
                                }
                                sportBasicReport.setMinPace(numBoxInt);
                                sportBasicReport.setAvgPace(dObtainDoubleFromStr != null ? Boxing.boxInt((int) (dObtainDoubleFromStr.doubleValue() * ((double) 1000))) : null);
                            }
                        } catch (Exception e) {
                            e = e;
                            FitnessLogUtils.e(HMParseUtilKt.TAG, "HmSportReportParse " + e.getMessage(), e);
                            return new ParseResultItem(true, currentTZOffsetIn15min, jOptLong);
                        }
                    } else {
                        jOptLong = jOptLong;
                        if (dObtainDouble != null) {
                            numBoxInt2 = Boxing.boxInt((int) (((double) 100) * dObtainDouble.doubleValue()));
                        } else {
                            numBoxInt2 = null;
                        }
                        sportBasicReport.setMaxPace(numBoxInt2);
                        if (dObtainDouble2 != null) {
                            numBoxInt3 = Boxing.boxInt((int) (dObtainDouble2.doubleValue() * ((double) 100)));
                        } else {
                            numBoxInt3 = null;
                        }
                        sportBasicReport.setMinPace(numBoxInt3);
                        if (dObtainDoubleFromStr != null) {
                            numBoxInt4 = Boxing.boxInt((int) (dObtainDoubleFromStr.doubleValue() * ((double) 100)));
                        } else {
                            numBoxInt4 = null;
                        }
                        sportBasicReport.setAvgPace(numBoxInt4);
                    }
                    if (dObtainDouble == null || dObtainDouble.doubleValue() <= 0.0d) {
                        d = 3.6d;
                        fBoxFloat = null;
                    } else {
                        d = 3.6d;
                        fBoxFloat = Boxing.boxFloat((float) ((((double) 1) / dObtainDouble.doubleValue()) * 3.6d));
                    }
                    sportBasicReport.setMaxSpeed(fBoxFloat);
                    sportBasicReport.setAvgSpeed((dObtainDoubleFromStr == null || dObtainDoubleFromStr.doubleValue() <= 0.0d) ? null : Boxing.boxFloat((float) ((((double) 1) / dObtainDoubleFromStr.doubleValue()) * d)));
                    String strOptString = jSONObject.optString("heart_range");
                    Intrinsics.checkNotNull(strOptString);
                    List listSplit$default = StringsKt.split$default((CharSequence) strOptString, new String[]{";"}, false, 0, 6, (Object) null);
                    if (listSplit$default.size() > 5) {
                        List listSplit$default2 = StringsKt.split$default((CharSequence) listSplit$default.get(0), new String[]{","}, false, 0, 6, (Object) null);
                        sportBasicReport.setHrSmoothRelaxDuration(!listSplit$default2.isEmpty() ? Boxing.boxInt(Integer.parseInt((String) listSplit$default2.get(0))) : null);
                        List listSplit$default3 = StringsKt.split$default((CharSequence) listSplit$default.get(1), new String[]{","}, false, 0, 6, (Object) null);
                        sportBasicReport.setHrWarmUpDuration(!listSplit$default3.isEmpty() ? Boxing.boxInt(Integer.parseInt((String) listSplit$default3.get(0))) : null);
                        List listSplit$default4 = StringsKt.split$default((CharSequence) listSplit$default.get(2), new String[]{","}, false, 0, 6, (Object) null);
                        sportBasicReport.setHrFatBurningDuration(!listSplit$default4.isEmpty() ? Boxing.boxInt(Integer.parseInt((String) listSplit$default4.get(0))) : null);
                        List listSplit$default5 = StringsKt.split$default((CharSequence) listSplit$default.get(3), new String[]{","}, false, 0, 6, (Object) null);
                        sportBasicReport.setHrAerobicDuration(!listSplit$default5.isEmpty() ? Boxing.boxInt(Integer.parseInt((String) listSplit$default5.get(0))) : null);
                        List listSplit$default6 = StringsKt.split$default((CharSequence) listSplit$default.get(4), new String[]{","}, false, 0, 6, (Object) null);
                        sportBasicReport.setHrAnaerobicDuration(!listSplit$default6.isEmpty() ? Boxing.boxInt(Integer.parseInt((String) listSplit$default6.get(0))) : null);
                        List listSplit$default7 = StringsKt.split$default((CharSequence) listSplit$default.get(5), new String[]{","}, false, 0, 6, (Object) null);
                        sportBasicReport.setHrExtremeDuration(!listSplit$default7.isEmpty() ? Boxing.boxInt(Integer.parseInt((String) listSplit$default7.get(0))) : null);
                    }
                    sportBasicReport.setReserveHrZone(Boxing.boxInt(0));
                    int sportType = sportBasicReport.getSportType();
                    if (sportType == 13) {
                        sportBasicReport.setRowCount(JsonUtilKt.obtainInt(jSONObject, "rope_skipping_count"));
                        sportBasicReport.setAvgRowFreq(JsonUtilKt.obtainInt(jSONObject, "rope_skipping_avg_frequency"));
                        sportBasicReport.setBestRowFreq(JsonUtilKt.obtainInt(jSONObject, "rope_skipping_max_frequency"));
                    } else if (sportType == 14) {
                        sportBasicReport.setSkipCount(JsonUtilKt.obtainInt(jSONObject, "rope_skipping_count"));
                        sportBasicReport.setAvgSkipFreq(JsonUtilKt.obtainInt(jSONObject, "rope_skipping_avg_frequency"));
                        sportBasicReport.setBestSkipFreq(JsonUtilKt.obtainInt(jSONObject, "rope_skipping_max_frequency"));
                    }
                    sportBasicReport.setAvgStress(JsonUtilKt.obtainInt(jSONObject, "avg_pressure"));
                    sportBasicReport.setMaxStress(JsonUtilKt.obtainInt(jSONObject, "max_pressure"));
                    sportBasicReport.setMinStress(JsonUtilKt.obtainInt(jSONObject, "min_pressure"));
                    Integer numObtainInt3 = JsonUtilKt.obtainInt(jSONObject, "sport_mode");
                    if (numObtainInt3 != null) {
                        if (numObtainInt3.intValue() == HmSportMode.IntervalTraining.getValue()) {
                            sportBasicReport.setSportCourseId(Boxing.boxInt(254));
                        }
                    }
                    boolean zRecordSportReport = getSendDataRecord().recordSportReport(str, FitnessDateUtils.getTzOffsetInSecBy15min(currentTZOffsetIn15min), sportBasicReport);
                    FitnessLogUtils.i(HMParseUtilKt.TAG, "HmSportReportParse item=" + sportBasicReport + "**result=" + zRecordSportReport + "**time=" + jObtainLongReturnNotNull);
                    jOptLong = jOptLong;
                    return new ParseResultItem(zRecordSportReport, currentTZOffsetIn15min, jOptLong);
                } catch (Exception e2) {
                    e = e2;
                    jOptLong = jOptLong;
                }
            } catch (Excep

... [truncated: exceeds 18000 bytes, total 18117 bytes]
