package hello.world.maketextinfinity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        TextView mLicense = (TextView)findViewById(R.id.license_text);
        mLicense.setMovementMethod(new ScrollingMovementMethod());
    }
}