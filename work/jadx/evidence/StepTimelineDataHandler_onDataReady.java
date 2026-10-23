// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 044-get_method_body-StepTimelineDataHandler-onDataReady.json
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Code duplicated, block: B:24:0x003d  */
    /* JADX WARN: Code duplicated, block: B:47:0x00b1  */
    @Override // com.xiaomi.fitness.common.lifecycle.IDataHandler
    @NotNull
    public Cursor onDataReady(@NotNull IDataHandler.DataSet dataSet) {
        List<StepRecord> listEmptyList;
        Intrinsics.checkNotNullParameter(dataSet, "dataSet");
        Map map = (Map) dataSet.get(STEP_REPORT_MAP);
        Object obj = map != null ? map.get(HomeDataType.STEP) : null;
        DailyStepReport dailyStepReport = obj instanceof DailyStepReport ? (DailyStepReport) obj : null;
        if (dailyStepReport == null || (listEmptyList = dailyStepReport.getStepRecords()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        String[] strArr = this.projection;
        if (strArr == null) {
            strArr = DEFAULT_COLS;
        } else {
            if (strArr.length == 0) {
                strArr = null;
            }
            if (strArr == null) {
                strArr = DEFAULT_COLS;
            }
        }
        MatrixCursor matrixCursor = new MatrixCursor(strArr, listEmptyList.size());
        for (StepRecord stepRecord : listEmptyList) {
            MatrixCursor.RowBuilder rowBuilderNewRow = matrixCursor.newRow();
            for (String str : strArr) {
                switch (str) {
                    case "calories":
                        rowBuilderNewRow.add(Integer.valueOf(stepRecord.getCalories()));
                        break;
                    case "time":
                        rowBuilderNewRow.add(Long.valueOf(stepRecord.getTime()));
                        break;
                    case "steps":
                        rowBuilderNewRow.add(Integer.valueOf(stepRecord.getSteps()));
                        break;
                    case "distance":
                        rowBuilderNewRow.add(Integer.valueOf(stepRecord.getDistance()));
                        break;
                    default:
                        rowBuilderNewRow.add(null);
                        break;
                }
            }
        }
        Logger.i(TAG, "build cursor rows=" + matrixCursor.getCount());
        return matrixCursor;
    }
