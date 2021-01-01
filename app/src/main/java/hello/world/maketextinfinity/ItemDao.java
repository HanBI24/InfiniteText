package hello.world.maketextinfinity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item_table")
    LiveData<List<ItemEntity>> getAll();

    @Insert
    void insert(ItemEntity dictionaryEntity);

    @Update
    void update(ItemEntity dictionaryEntity);

    @Delete
    void delete(ItemEntity dictionaryEntity);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("DELETE FROM item_table WHERE contents is null ")
    void deleteNull();

//    @Query("SELECT * FROM item_table ORDER BY id DESC")
//    LiveData<List<ItemEntity>> orderDESC(int id);
}
