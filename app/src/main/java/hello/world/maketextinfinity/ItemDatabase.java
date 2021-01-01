package hello.world.maketextinfinity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ItemEntity.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    private static volatile ItemDatabase INSTANCE;

    // Singleton
    public static ItemDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ItemDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, "dict_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyINSTANCE(){
        INSTANCE = null;
    }
}
