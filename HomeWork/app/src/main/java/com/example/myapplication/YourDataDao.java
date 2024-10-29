@Dao
public interface YourDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(List<YourData> data);

    @Query("SELECT * FROM your_data_table")
    List<YourData> getAllData();

    @Query("SELECT * FROM your_data_table WHERE name LIKE '%' || :query || '%'")
    List<YourData> searchDatabase(String query);
}
