package hello.world.maketextinfinity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {
    private ArrayList<String> textArrayList;
    private ArrayList<Integer> numArrayList;
    ListView listView;
    ListAdapter listAdapter;
    String text;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        textArrayList = new ArrayList<>();
        numArrayList = new ArrayList<>();

        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        num = intent.getIntExtra("num", 0);
        textArrayList.add(text);
        numArrayList.add(num);

        listAdapter = new ListAdapter();
        listView = (ListView)findViewById(R.id.list_item);
        listView.setAdapter(listAdapter);

        for(int i=0; i<textArrayList.size(); i++) {
            listAdapter.addItem(textArrayList.get(i), numArrayList.get(i));
            listAdapter.notifyDataSetChanged();
        }
    }
}