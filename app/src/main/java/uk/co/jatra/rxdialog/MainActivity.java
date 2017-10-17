package uk.co.jatra.rxdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = findViewById(R.id.button);
        TextView answer = findViewById(R.id.answer);
        b.setOnClickListener(view -> RxDialogFragment.newInstance().observe(getSupportFragmentManager(), "tag")
                .subscribe(b1 -> {
                    String message = b1 ? "licks cheese" : "does not lick cheese";
                    Log.d(TAG, message);
                    answer.setText(b1 ? "Yes" : "No");
                }));
    }
}
