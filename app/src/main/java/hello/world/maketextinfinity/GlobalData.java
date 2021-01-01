package hello.world.maketextinfinity;

import android.app.Application;

public class GlobalData extends Application {
    int count;
    String contents;

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
}
