package com.davidmarian_buzatu.benchme.activity;

import androidx.annotation.NonNull;
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
import com.davidmarian_buzatu.benchme.model.Device;
import com.davidmarian_buzatu.benchme.services.DialogShow;
import com.davidmarian_buzatu.benchme.tester.HDDTest;
import com.davidmarian_buzatu.benchme.tester.MersenneTest;
import com.davidmarian_buzatu.benchme.tester.AtkinTest;
import com.davidmarian_buzatu.benchme.tester.RAMTest;
import com.davidmarian_buzatu.benchme.tester.TestRAM;
import com.davidmarian_buzatu.benchme.tester.ThreadedHashingTest;
import com.davidmarian_buzatu.benchme.tester.ThreadedRootsTest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.davidmarian_buzatu.benchme.model.Device.ATKIN;
import static com.davidmarian_buzatu.benchme.model.Device.CPU;
import static com.davidmarian_buzatu.benchme.model.Device.CPUCORES;
import static com.davidmarian_buzatu.benchme.model.Device.CPUSPEED;
import static com.davidmarian_buzatu.benchme.model.Device.HASH;
import static com.davidmarian_buzatu.benchme.model.Device.HDD;
import static com.davidmarian_buzatu.benchme.model.Device.HDDR;
import static com.davidmarian_buzatu.benchme.model.Device.HDDT;
import static com.davidmarian_buzatu.benchme.model.Device.HDDW;
import static com.davidmarian_buzatu.benchme.model.Device.MERSENNE;
import static com.davidmarian_buzatu.benchme.model.Device.MODEL;
import static com.davidmarian_buzatu.benchme.model.Device.OS;
import static com.davidmarian_buzatu.benchme.model.Device.RAM;
import static com.davidmarian_buzatu.benchme.model.Device.RAMT;
import static com.davidmarian_buzatu.benchme.model.Device.ROOTS;

public class MainActivity extends AppCompatActivity {

    private int workLeft = 6;
    private Bundle bundle = new Bundle();
    private int testsRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtonListener();
        setRankingListener();
    }

    private void setRankingListener() {
        Button rankingButton = findViewById(R.id.act_main_BTN_ranking);
        rankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rankingIntent = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(rankingIntent);
            }
        });
    }

    private void setButtonListener() {
        final Context context = this;

        Button startButton = findViewById(R.id.act_main_BTN_benchmark);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = DialogShow.getInstance().getDisplayDialog(context, R.string.act_main_dialog_message);
                dialog.show();
                ExecutorService executorService = Executors.newFixedThreadPool(8);
                // RAM TEST
                executorService.execute(getRAMRunnable(dialog));

                // HDD TEST
                executorService.execute(getHDDRunnable(dialog));

                // Mersenne
                executorService.execute(getMersenneRunnable(dialog));

                // Threaded Roots
                executorService.execute(getThreadedRootsRunnable(dialog));

                // Atkin
                executorService.execute(getAtkinRunnable(dialog));

                // Hashing
                executorService.execute(getThreadedHashingRunnable(dialog));

            }
        });
    }

    private Runnable getThreadedHashingRunnable(ProgressDialog dialog) {
        return new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                bundle.putDouble(HASH, ThreadedHashingTest.testThreadedHashing() / 60.0);
                Log.d("TEST", HASH);
                decreaseWork();
                if (workLeft == 0) {
                    dialog.dismiss();
                    redirectToResults();
                }
                Looper.loop();
            }
        };
    }

    private Runnable getRAMRunnable(final ProgressDialog dialog) {
        return new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                bundle.putDouble(RAMT, RAMTest.testRAM() / 50.0);
                Log.d("TEST", RAMT);
                decreaseWork();
                if (workLeft == 0) {
                    dialog.dismiss();
                    redirectToResults();
                }
                Looper.loop();
            }
        };
    }

    private Runnable getHDDRunnable(final ProgressDialog dialog) {
        final Context context = this;
        return new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                bundle.putDouble(HDDT, HDDTest.testHDD(context) / 700.0);
                bundle.putDouble(HDDW, HDDTest.getMaxWriteSpeed());
                bundle.putDouble(HDDR, HDDTest.getMaxReadSpeed());
                Log.d("TEST", HDD);
                decreaseWork();
                if (workLeft == 0) {
                    dialog.dismiss();
                    redirectToResults();
                }
                Looper.loop();
            }
        };
    }

    private Runnable getAtkinRunnable(final ProgressDialog dialog) {
        return new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                bundle.putDouble(ATKIN, AtkinTest.testAtkin() / 31.0);
                Log.d("TEST", ATKIN);
                decreaseWork();
                if (workLeft == 0) {
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
                bundle.putDouble(MERSENNE, MersenneTest.testMersenne() / 4000.0);
                Log.d("TEST", MERSENNE);
                decreaseWork();
                if (workLeft == 0) {
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
                bundle.putDouble(ROOTS, ThreadedRootsTest.testThreadedRoots() / Math.pow(10, 8));
                Log.d("TEST", ROOTS);
                decreaseWork();
                if (workLeft == 0) {
                    dialog.dismiss();
                    redirectToResults();
                }
                Looper.loop();
            }
        };
    }


    private synchronized void decreaseWork() {
        --workLeft;
        ++testsRun;
    }

    private void redirectToResults() {
        Intent activityResults = new Intent(this, ResultsActivity.class);
        activityResults.putExtra("Bundle", bundle);
        startActivity(activityResults);
    }
}
