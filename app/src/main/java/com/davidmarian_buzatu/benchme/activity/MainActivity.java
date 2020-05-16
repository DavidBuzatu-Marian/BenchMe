package com.davidmarian_buzatu.benchme.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.davidmarian_buzatu.benchme.R;
import com.davidmarian_buzatu.benchme.benchmark.MemoryHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtonListener();
    }

    private void setButtonListener() {
        Button startButton = findViewById(R.id.act_main_BTN_benchmark);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: SHOW DIALOG UNTIL BENCHMARK IS FINISHED
                // BENCHMARKING
                MemoryHandler memoryHandler = new MemoryHandler();
                Log.d("TEST", String.valueOf(memoryHandler.allocateMemory(1024 * 1024 * 64)));
                memoryHandler.close();
                //TODO: STOP DIALOG

                // REDIRECT
                redirectToResults();
            }
        });
    }

    private void redirectToResults() {
        Intent activityResults = new Intent(this, ResultsActivity.class);
        //TODO: Put results in extra
        startActivity(activityResults);
    }
}
