package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// TODO see https://pttrns.com/android-patterns?scid=31
// TODO see global action https://developer.android.com/guide/navigation/navigation-design-graph
// TODO see https://developer.android.com/guide/navigation
// TODO see https://www.youtube.com/watch?v=Y0Cs2MQxyIs

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView numbers = findViewById(R.id.numbers);
        numbers.setOnClickListener(view -> {
            Log.d(TAG, "Click on numbers");
            manageOnClick(view);
        });
        final TextView colors = findViewById(R.id.colors);
        colors.setOnClickListener(view -> {
            Log.d(TAG, "Click on colors");
            manageOnClick(view);
        });
        final TextView family = findViewById(R.id.family);
        family.setOnClickListener(view -> {
            Log.d(TAG, "Click on family");
            manageOnClick(view);
        });
        final TextView phrases = findViewById(R.id.phrases);
        phrases.setOnClickListener(view -> {
            Log.d(TAG, "Click on phrases");
            manageOnClick(view);
        });
    }


    public void manageOnClick(@Nullable final View view) {

        if (view != null) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.numbers:
                    intent = new Intent(this, NumbersActivity.class);
                    break;
                case R.id.colors:
                    intent = new Intent(this, ColorsActivity.class);
                    break;
                case R.id.family:
                    intent = new Intent(this, FamilyActivity.class);
                    break;
                case R.id.phrases:
                    intent = new Intent(this, PhrasesActivity.class);
                    break;
            }
            if (intent != null) {
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        }
    }
}
