package com.davidmarian_buzatu.benchme.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.davidmarian_buzatu.benchme.R;
import com.davidmarian_buzatu.benchme.services.DialogShow;
import com.davidmarian_buzatu.benchme.tester.MersenneTest;
import com.davidmarian_buzatu.benchme.tester.AtkinTest;
import com.davidmarian_buzatu.benchme.tester.ThreadedRootsTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private int workLeft = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtonListener();
    }

    private void setButtonListener() {
        final Context context = this;

        Button startButton = findViewById(R.id.act_main_BTN_benchmark);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = DialogShow.getInstance().getDisplayDialog(context, R.string.act_main_dialog_message);
                dialog.show();
                ExecutorService executorService = Executors.newFixedThreadPool(4);

//                // Mersenne
//                executorService.execute(getMersenneRunnable(dialog));

//                // Threaded Roots
//                executorService.execute(getThreadedRootsRunnable(dialog));
//
//                // Atkin
//                executorService.execute(getAtkinRunnable(dialog));
            }
        });
    }

    private Runnable getAtkinRunnable(final ProgressDialog dialog) {
        return new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                increaseWork();
                Log.d("RESULT", "Score Atkin " + AtkinTest.testAtkin());
                decreaseWork();
                if(workLeft == 0) {
                    dialog.dismiss();
                    redirectToResults();
                }
                Looper.loop();
            }
        };
    }



    private Runnable getMersenneRunnable(final ProgressDialog dialog) {
        return new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                increaseWork();
                Log.d("RESULT", "Score Mersenne:" + MersenneTest.testMersenne());
                decreaseWork();
                if(workLeft == 0) {
                    dialog.dismiss();
                    redirectToResults();
                }
                Looper.loop();
            }
        };
    }

    private Runnable getThreadedRootsRunnable(final ProgressDialog dialog) {
        return new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                increaseWork();
                Log.d("RESULT", "Score roots:" + ThreadedRootsTest.testThreadedRoots());
                decreaseWork();
                if(workLeft == 0) {
                    dialog.dismiss();
                    redirectToResults();
                }
                Looper.loop();
            }
        };
    }

    private synchronized void increaseWork() {
        ++workLeft;
    }

    private synchronized void decreaseWork() {
        --workLeft;
    }

    private void redirectToResults() {
        Intent activityResults = new Intent(this, ResultsActivity.class);
        //TODO: Put results in extra
        startActivity(activityResults);
    }
}
