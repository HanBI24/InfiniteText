package hello.world.maketextinfinity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText etText;
    TextView textView;
    String text;
    int num;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = (EditText) findViewById(R.id.et_text);
        final EditText etNum = (EditText) findViewById(R.id.et_num);
        Button createBtn = (Button) findViewById(R.id.create);
        textView = (TextView) findViewById(R.id.text_view);
        textView.setMovementMethod(new ScrollingMovementMethod());
        Button copyBtn = (Button)findViewById(R.id.copy);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = Integer.parseInt(etNum.getText().toString());
                text = etText.getText().toString();
                text=repeatText(text, num);
                textView.setBackgroundColor(Color.argb(100, 131, 126, 126));
                textView.setTextColor(Color.argb(100, 0, 0, 0));
                textView.setText(text);
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("ID", text);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "copy success", Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.more_info){
            Intent intent = new Intent(getApplicationContext(), MoreInfo.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
