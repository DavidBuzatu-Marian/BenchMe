package com.davidmarian_buzatu.benchme.adapter;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.davidmarian_buzatu.benchme.R;
import com.davidmarian_buzatu.benchme.model.Device;

import java.util.List;

public class ListRankingAdapter extends RecyclerView.Adapter<ListRankingAdapter.ListRankingViewHolder> {

    private static final int FIRST = 0, SECOND = 1, THIRD = 2;
    private List<Device> mDataSet;
    private Context mContext;

    public ListRankingAdapter(List<Device> dataset, Context context) {
        mDataSet = dataset;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return FIRST;
            case 1:
                return SECOND;
            case 2:
                return THIRD;
            default:
                return position;
        }
    }


    @NonNull
    @Override
    public ListRankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutID;
        switch (viewType) {
            case FIRST:
                layoutID = R.layout.list_item_ranking_first;
                break;
            case SECOND:
                layoutID = R.layout.list_item_ranking_second;
                break;
            case THIRD:
                layoutID = R.layout.list_item_ranking_third;
                break;
            default:
                layoutID = R.layout.list_item_ranking;
        }


        ConstraintLayout _viewGroup = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);

        ListRankingViewHolder _vh = new ListRankingViewHolder(_viewGroup, parent);
        return _vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListRankingViewHolder holder, int position) {
        Device device = mDataSet.get(position);
        holder.setInfoInViews(device, mContext);
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ListRankingViewHolder extends RecyclerView.ViewHolder {
        private TextView mDevicePosName, mDeviceScore;
        private Button mButton;
        private PopupWindow mPopWindow;
        private View mInflatedView;
        private Device mDevice;

        public ListRankingViewHolder(@NonNull View itemView, @NonNull final ViewGroup parent) {
            super(itemView);

            mDevicePosName = itemView.findViewById(R.id.adapter_listDevices_TV_name);
            mDeviceScore = itemView.findViewById(R.id.adapter_listDevices_TV_score);
            mButton = itemView.findViewById(R.id.adapter_listDevices_BTN_view);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopUp(view, parent);
                }
            });
        }

        private void setInfoInViews(Device device, Context context) {
            mDevicePosName.setText(new StringBuilder().append(mDataSet.indexOf(device) + 1).append(". ").append(device.getDeviceModel()).toString());
            mDeviceScore.setText(new StringBuilder().append("Score: ").append(device.getScore()).toString());
            mDevice = device;
        }

        private void showPopUp(View v, @NonNull ViewGroup parent) {
            mInflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_device_info, null, false);
            // get device size
            Display _display = parent.getDisplay();
            final Point _size = new Point();
            _display.getSize(_size);

            mPopWindow = new PopupWindow(mInflatedView, _size.x, _size.y - 100, true);

            // set a background drawable with rounders corners
            mPopWindow.setBackgroundDrawable(parent.getResources().getDrawable(R.drawable.bkg_appointment_options));
            // make it focusable to show the keyboard to enter in `EditText`
            mPopWindow.setFocusable(true);
            // make it outside touchable to dismiss the popup window
            mPopWindow.setOutsideTouchable(true);
            mPopWindow.setAnimationStyle(R.style.PopupAnimation);

            // show the popup at bottom of the screen and set some margin at bottom ie,
            mPopWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            setInformationInPopup(mInflatedView, mDevice);
        }

    }

    private void setInformationInPopup(View inflatedView, Device device) {
        TextView cpuTV1 = inflatedView.findViewById(R.id.popup_device_TV_CPU_Test_1);
        TextView cpuTV2 = inflatedView.findViewById(R.id.popup_device_TV_CPU_Test_2);
        TextView cpuTV3 = inflatedView.findViewById(R.id.popup_device_TV_CPU_Test_3);
        TextView cpuTV4 = inflatedView.findViewById(R.id.popup_device_TV_CPU_Test_4);

        TextView hddTV1 = inflatedView.findViewById(R.id.popup_device_TV_HDD_Test_1);
        TextView hddRV2 = inflatedView.findViewById(R.id.popup_device_TV_HDD_Test_2);

        // Here we should put write and read speed for HDD

        TextView ramTV1 = inflatedView.findViewById(R.id.popup_device_TV_RAM_Test_1);

        // Here we should pur write and read speed for RAM;

        cpuTV1.setText("Mersenne: " + device.getScoreMersenne());
        cpuTV2.setText("Roots: " + device.getScoreRoots());
        cpuTV3.setText("Hash: " + device.getScoreHASH());
        cpuTV4.setText("Sieve Of Atkin: " + device.getScoreAtkin());

    }
}
