package hello.world.maketextinfinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText etText, etNum;
    private TextView textView;
    private String text;
    private int num;
    String getContents;
    int getCount;
    Button createBtn, copyBtn;
    private GlobalData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (EditText) findViewById(R.id.et_text);
        etNum = (EditText) findViewById(R.id.et_num);
        createBtn = (Button) findViewById(R.id.create);
        textView = (TextView) findViewById(R.id.text_view);
        copyBtn = (Button)findViewById(R.id.copy);

        data = (GlobalData) getApplication();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etText.getText().toString().trim().length()<=0 || etNum.getText().toString().trim().length()<=0){
                    Toast.makeText(MainActivity.this, "한 글자 이상 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etText.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etNum.getWindowToken(), 0);
                    num = Integer.parseInt(etNum.getText().toString());
                    text = etText.getText().toString();
                    getContents = text;
                    getCount = num;
                    text = repeatText(text, num);
                    textView.setBackgroundColor(Color.argb(100, 131, 126, 126));
                    textView.setTextColor(Color.argb(100, 0, 0, 0));
                    textView.setText(text);
                    Toast.makeText(getApplicationContext(), "생성 완료", Toast.LENGTH_SHORT).show();
                    data.setContents(getContents);
                    data.setCount(getCount);
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
                    Toast.makeText(getApplicationContext(), getContents+" "+getCount+"번 복사 완료", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String repeatText(String text, int n){
        String result = text;
        for(int i=0; i<n-1; i++){
            result += text;
        }
        return result;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        data.setContents(null);
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
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
