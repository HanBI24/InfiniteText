package hello.world.maketextinfinity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TestEntity.class}, version = 1)
public abstract class TestDataBase extends RoomDatabase {
    private static TestDataBase instance;
    public abstract TestDao testDao();

    public static TestDataBase getAppDataBase(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,
                    TestDataBase.class,
                    "test-db")
                    .build();
        }

        return instance;
    }

    public static void destroyInstance(){
        instance = null;
    }
}
