package hello.world.maketextinfinity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TestDao {

    @Query("SELECT * FROM TestEntity")
    LiveData<List<TestEntity>> getAll();

    @Insert
    void insert(TestEntity testEntity);

    @Update
    void update(TestEntity testEntity);

    @Delete
    void delete(TestEntity testEntity);

    @Query("DELETE FROM TestEntity")
    void deleteAll();
}
