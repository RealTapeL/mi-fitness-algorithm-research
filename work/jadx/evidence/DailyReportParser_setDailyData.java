// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 078-get_method_body-DailyReportParser-setDailyData.json
    private final void setDailyData(FitnessDataBaseParser.DataValueItem valueItem, ParsedDailyReport report) {
        if (valueItem == null || !valueItem.getValid()) {
            return;
        }
        switch (valueItem.getType()) {
            case 0:
                report.setSteps(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 1:
                report.setCalories(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 2:
                report.setHr(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 3:
                report.setRestHr(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 4:
                report.setMaxHr(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 5:
                report.setMaxHrTime(Long.valueOf(valueItem.getValue().longValue()));
                break;
            case 6:
                report.setMinHr(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 7:
                report.setMinHrTime(Long.valueOf(valueItem.getValue().longValue()));
                break;
            case 8:
                report.setAvgHr(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 9:
                report.setAvgStress(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 10:
                report.setMaxStress(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 11:
                report.setMinStress(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 12:
                report.setValidStand(valueItem.getBytes());
                break;
            case 13:
                report.setTotalCal(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 14:
                report.setRecoveryTime(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 15:
                report.setSpo2(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 16:
                report.setMaxSpo2(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 17:
                report.setMaxSpo2Time(Long.valueOf(valueItem.getValue().longValue()));
                break;
            case 18:
                report.setMinSpo2(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 19:
                report.setMinSpo2Time(Long.valueOf(valueItem.getValue().longValue()));
                break;
            case 20:
                report.setAvgSpo2(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 21:
                report.setDayTrainingLoad(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 22:
                report.setWeekTrainingLoad(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 23:
                report.setTrainingLoadLevel(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 24:
                report.setDayLowIntensityVitality(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 25:
                report.setDayMediumIntensityVitality(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 26:
                report.setDayHighIntensityVitality(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 27:
                report.setTotal7daysVitality(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 28:
                report.setSuggestActivityType(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 29:
                report.setSuggestActivityDuration(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 30:
                report.setPhysicalFitnessStatus(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 31:
                report.setPhysicalFitnessLevel(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 32:
                report.setFatigueLevel(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            case 33:
                report.setPhysicalStatusStress(Integer.valueOf(valueItem.getValue().intValue()));
                break;
            default:
                FitnessLogUtils.w(this.TAG, "no support type = " + valueItem.getType());
                break;
        }
    }
