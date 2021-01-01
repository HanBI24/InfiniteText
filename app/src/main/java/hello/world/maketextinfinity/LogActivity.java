package hello.world.maketextinfinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LogActivity extends AppCompatActivity {
    private String getContents;
    private int getCount;
    private LogActivityAdapter adapter;
    private ItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        GlobalData data = (GlobalData) getApplication();
        getCount = data.getCount();
        getContents = data.getContents();

        RecyclerView mRecyclerView = findViewById(R.id.log_recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        // 구분선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        db = ItemDatabase.getDatabase(this);
        adapter = new LogActivityAdapter(db, this);
        mRecyclerView.setAdapter(adapter);

        db.itemDao().getAll().observe(this, itemEntities -> adapter.setItem(itemEntities));

        new Thread(()-> {
            ItemEntity dict = new ItemEntity(getCount, getContents);
            db.itemDao().insert(dict);
        }).start();

        adapter.deleteNull();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all) {
            adapter.deleteAll();
            Toast.makeText(getApplicationContext(), "전체 삭제 완료", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}