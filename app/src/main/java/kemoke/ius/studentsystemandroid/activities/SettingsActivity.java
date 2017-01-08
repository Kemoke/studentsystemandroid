package kemoke.ius.studentsystemandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kemoke.ius.studentsystemandroid.R;

/**
 * Activity for handling application settings.
 */
@SuppressWarnings("ConstantConditions")
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
