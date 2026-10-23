// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 077-get_method_body-DailyReportParser-processDailyDataReport.json
    private final ParsedDailyReport processDailyDataReport(FitnessDataId dataId, Map<Integer, FitnessDataBaseParser.DataValueItem> resultMap) {
        ParsedDailyReport parsedDailyReport = new ParsedDailyReport(dataId.getTimeStamp(), dataId.getTzOffsetInSec());
        for (FitnessDataBaseParser.OneDimenDataType oneDimenDataType : this.reportDataTypeArray) {
            setDailyData(resultMap.get(Integer.valueOf(oneDimenDataType.getType())), parsedDailyReport);
        }
        return parsedDailyReport;
    }
