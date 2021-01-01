package hello.world.maketextinfinity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class ItemEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int count;
    private String contents;

    public ItemEntity(int count, String contents) {
        this.count = count;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", count=" + count +
                ", contents='" + contents + '\'' +
                '}';
    }
}
