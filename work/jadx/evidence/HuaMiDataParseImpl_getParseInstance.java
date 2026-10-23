// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 054-get_method_body-HuaMiDataParseImpl-getParseInstance.json
    private final IHmDataParseByCategory getParseInstance(HuaMiParseType dataType) {
        switch (WhenMappings.$EnumSwitchMapping$0[dataType.ordinal()]) {
            case 1:
                return new HmAfibDataParse();
            case 2:
                return new HmAllDayStressParse();
            case 3:
                return new HmManualStressParse();
            case 4:
                return new HmManualHrParse();
            case 5:
                return new HmPaiDataParse();
            case 6:
                return new HmSpo2DataParse();
            case 7:
                return new HmDailyDataParse();
            case 8:
                return new HmSportReportParse();
            case 9:
                return new HmSportRecordParse();
            case 10:
                return new HmCaloriesParse();
            case 11:
                return new HmTrainingLoadParse();
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
