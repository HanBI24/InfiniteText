package hello.world.maketextinfinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText etText, etNum;
    private TextView textView;
    private String text;
    private int num;
    String getText;
    int getNum;
    Button createBtn, copyBtn;
    Button roomDbBtn, roomRemoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (EditText) findViewById(R.id.et_text);
        etNum = (EditText) findViewById(R.id.et_num);
        createBtn = (Button) findViewById(R.id.create);
        textView = (TextView) findViewById(R.id.text_view);
        copyBtn = (Button)findViewById(R.id.copy);
        roomDbBtn = (Button)findViewById(R.id.room_btn);
        roomRemoveBtn = (Button)findViewById(R.id.room_remove_btn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etText.getText().toString().trim().length()<=0 || etNum.getText().toString().trim().length()<=0 ){
                    Toast.makeText(MainActivity.this, "한 글자 이상 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    num = Integer.parseInt(etNum.getText().toString());
                    text = etText.getText().toString();
                    getText = text;
                    getNum = num;
                    text = repeatText(text, num);
                    textView.setBackgroundColor(Color.argb(100, 131, 126, 126));
                    textView.setTextColor(Color.argb(100, 0, 0, 0));
                    textView.setText(text);
                }
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etText.getText().toString().trim().length()<=0 || etNum.getText().toString().trim().length()<=0 ){
                    Toast.makeText(MainActivity.this, "한 글자 이상 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("ID", text);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getApplicationContext(), "copy success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // db 생성
        final TestDataBase db = TestDataBase.getAppDataBase(this);

        // UI 갱신 (LiveData Observer 이용, 해당 db가 변경되면 실행됨)
        db.testDao().getAll().observe(this, new Observer<List<TestEntity>>() {
            @Override
            public void onChanged(List<TestEntity> testEntities) {
                textView.setText(testEntities.toString());
            }
        });

        // db 불러오기
        textView.setText(db.testDao().getAll().toString());

        // 데이터 db에 추가
        roomDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etText.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this, "한 글자 이상 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    new InsertAsyncTask(db.testDao()).execute(new TestEntity(etText.getText().toString()));
                }
            }
        });

        roomRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAsyncTask(db.testDao(), MainActivity.this).execute(new TestEntity(etText.getText().toString()));
            }
        });

    }

    public static class InsertAsyncTask extends AsyncTask<TestEntity, Void, Void>{
        private TestDao testDao;

        public InsertAsyncTask(TestDao testDao){
            this.testDao = testDao;
        }

        @Override
        protected Void doInBackground(TestEntity... testEntities) {
            // SELECT 문 필요 없이 LiveData로 인해 getAll()이 반응해서 데이터 자동 갱신
            // MainActivity에서 사용한 Observer가 실행됨
            testDao.insert(testEntities[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<TestEntity, Void, Void>{
        private TestDao testDao;
        private WeakReference<MainActivity> activityWeakReference;

        public DeleteAsyncTask(TestDao testDao, MainActivity context){
            this.testDao = testDao;
            activityWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(TestEntity... testEntities) {
            MainActivity task = activityWeakReference.get();
//            for(int i=0; i<testEntities.length; i++){
//                if(testEntities[i].equals(task.textView.getText().toString())){
//                    testDao.delete(testEntities[i]);
//                }
//            }
            testDao.deleteAll();
            return null;
        }
    }

    public String repeatText(String text, int n){
        String result = text;
        for(int i=0; i<n-1; i++){
            result += text;
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.more_info){
            Intent intent = new Intent(getApplicationContext(), MoreInfo.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.log){
            Intent intent = new Intent(getApplicationContext(), LogActivity.class);
            intent.putExtra("text", getText);
            intent.putExtra("num", getNum);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
