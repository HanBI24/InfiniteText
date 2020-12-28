package hello.world.maketextinfinity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TestEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;

    public TestEntity(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}' + "\n";
    }
}
