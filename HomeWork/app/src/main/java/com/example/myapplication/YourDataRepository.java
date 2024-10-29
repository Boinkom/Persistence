public class YourDataRepository {

    private final AppDatabase database;
    private final NetworkService networkService;

    public YourDataRepository(AppDatabase database, NetworkService networkService) {
        this.database = database;
        this.networkService = networkService;
    }

    public List<YourData> getData() throws Exception {
        List<YourData> cachedData = database.yourDataDao().getAllData();
        if (!cachedData.isEmpty()) {
            return cachedData;
        } else {
            List<YourData> dataFromNetwork = networkService.getData();
            database.yourDataDao().insertData(dataFromNetwork);
            return dataFromNetwork;
        }
    }

    public List<YourData> searchDatabase(String query) {
        return database.yourDataDao().searchDatabase(query);
    }
}
