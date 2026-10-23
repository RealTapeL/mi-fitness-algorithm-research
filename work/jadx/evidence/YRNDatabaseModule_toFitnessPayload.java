// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 095-get_method_body-YRNDatabaseModule-toFitnessPayload.json
    private final Map<String, Object> toFitnessPayload(FitnessDbQueryBridge.FitnessRecord fitnessRecord, boolean z, boolean z2) {
        List<Double> listExtractValueList;
        LinkedHashMap linkedHashMapLinkedMapOf = MapsKt.linkedMapOf(TuplesKt.to("sid", fitnessRecord.getSid()), TuplesKt.to("dataType", fitnessRecord.getDataType()), TuplesKt.to("time", Long.valueOf(fitnessRecord.getTime())), TuplesKt.to("timeZero", Long.valueOf(fitnessRecord.getTimeZero())), TuplesKt.to("zoneName", fitnessRecord.getZoneName()), TuplesKt.to(CloudContract.COL_ZONE_OFFSET, Integer.valueOf(fitnessRecord.getZoneOffset())), TuplesKt.to("value", fitnessRecord.getValue()));
        String category = fitnessRecord.getCategory();
        if (category != null) {
            linkedHashMapLinkedMapOf.put("category", category);
        }
        if (z) {
            linkedHashMapLinkedMapOf.put("source", resolveDeviceSource(fitnessRecord.getSid()));
        }
        if (z2 && (listExtractValueList = extractValueList(fitnessRecord.getValue())) != null) {
            linkedHashMapLinkedMapOf.put("valueList", listExtractValueList);
        }
        return linkedHashMapLinkedMapOf;
    }
