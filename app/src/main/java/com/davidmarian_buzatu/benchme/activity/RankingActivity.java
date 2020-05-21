package com.davidmarian_buzatu.benchme.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.davidmarian_buzatu.benchme.R;
import com.davidmarian_buzatu.benchme.adapter.ListRankingAdapter;
import com.davidmarian_buzatu.benchme.model.Device;
import com.davidmarian_buzatu.benchme.services.DialogShow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    private List<Device> mListDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mListDevices = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.act_ranking_RV_ranking);

        ProgressDialog dialog = DialogShow.getInstance().getDisplayDialog(this, R.string.act_ranking_dialog_message);
        dialog.show();
        getDevices().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()) {
                        mListDevices.add(queryDocumentSnapshot.toObject(Device.class));
                    }
                }
                dialog.dismiss();
                setAdapterForRV(recyclerView);
            }
        });
    }

    private void setAdapterForRV(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ListRankingAdapter adapter = new ListRankingAdapter(mListDevices, this);
        recyclerView.setAdapter(adapter);
    }

    private Task<QuerySnapshot> getDevices() {
        return FirebaseFirestore.getInstance().collection("devices").orderBy("score", Query.Direction.DESCENDING).get();
    }
}
