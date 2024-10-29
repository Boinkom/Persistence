@Database(entities = {YourData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract YourDataDao yourDataDao();
}
