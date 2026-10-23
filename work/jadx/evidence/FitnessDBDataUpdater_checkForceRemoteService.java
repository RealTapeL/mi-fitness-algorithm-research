// JADX 1.5.6 method evidence from Android APK 3.59.1; source: 093-get_method_body-FitnessDBDataUpdater-checkForceRemoteService.json
    private final boolean checkForceRemoteService() {
        return ApplicationExtKt.isMainProcess(ApplicationExtKt.getApplication()) && getMRemoteService() != null;
    }
