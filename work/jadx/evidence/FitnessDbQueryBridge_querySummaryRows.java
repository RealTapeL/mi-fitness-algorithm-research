// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 042-get_method_body-FitnessDbQueryBridge-querySummaryRows.json
// Method: com.xiaomi.fit.fitness.persist.db.utils.FitnessDbQueryBridge.querySummaryRows
    /* JADX INFO: Access modifiers changed from: private */
    public final List<FitnessRecord> querySummaryRows(SupportSQLiteDatabase db, String sql, Function1<? super Cursor, FitnessRecord> mapper) {
        if (StringsKt.isBlank(sql)) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = db.query(new SimpleSQLiteQuery(sql));
        try {
            Cursor cursor = cursorQuery;
            while (cursor.moveToNext()) {
                arrayList.add(mapper.invoke(cursor));
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursorQuery, null);
            return arrayList;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(cursorQuery, th);
                throw th2;
            }
        }
    }
