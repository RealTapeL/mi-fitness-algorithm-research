// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 083-get_method_body-YRNDatabaseModule-executeQuery.json
    /* JADX WARN: Code duplicated, block: B:16:0x0050  */
    private final <Q, R> void executeQuery(String api, ReadableMap rawQuery, Callback callback, Function0<? extends Object> emptyData, Function1<? super ReadableMap, ParsedInput<Q>> parser, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block, Function1<? super R, ? extends Object> transform) {
        long jLongValue;
        Logger.i(TAG, api + " start " + describeReadableMap(rawQuery));
        ParsedInput<Q> parsedInputInvoke = parser.invoke(rawQuery);
        if (parsedInputInvoke.getError() == null && parsedInputInvoke.getValue() != null) {
            Long lOptLong = optLong(rawQuery, "timeoutMs");
            if (lOptLong == null) {
                jLongValue = 10000;
            } else {
                if (lOptLong.longValue() <= 0) {
                    lOptLong = null;
                }
                if (lOptLong != null) {
                    jLongValue = lOptLong.longValue();
                } else {
                    jLongValue = 10000;
                }
            }
            BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass1(jLongValue, transform, api, this, emptyData, callback, block, parsedInputInvoke, null), 3, null);
            return;
        }
        Logger.i(TAG, api + " badParams error=" + parsedInputInvoke.getError() + " " + describeReadableMap(rawQuery));
        Object objInvoke = emptyData.invoke();
        String error = parsedInputInvoke.getError();
        if (error == null) {
            error = "invalid params";
        }
        callback.invoke(buildResponse(1, objInvoke, error));
    }
