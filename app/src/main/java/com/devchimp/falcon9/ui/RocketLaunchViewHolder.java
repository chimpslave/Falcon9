package com.devchimp.falcon9.ui;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devchimp.falcon9.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class RocketLaunchViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private TextView missionName;
    private TextView launchDate;
    private TextView missionSuccess;
    private ImageView missionPatch;
    private Resources resources;

    RocketLaunchViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        missionName = itemView.findViewById(R.id.mission_name);
        launchDate = itemView.findViewById(R.id.launch_date);
        missionSuccess = itemView.findViewById(R.id.mission_success);
        missionPatch = itemView.findViewById(R.id.missionPatch);
        resources = itemView.getResources();
    }

    void bind(LaunchViewModel vm) {
        missionName.setText(vm.missionName());
        launchDate.setText(formatDate(vm));
        int success = vm.missionSuccess() ? R.drawable.ic_check_white_24dp : R.drawable.ic_cross_white_24dp;
        missionSuccess.setCompoundDrawablesWithIntrinsicBounds(null, null, resources.getDrawable(success, itemView.getContext().getTheme()), null);
        setImage(vm);
    }

    @SuppressLint("SimpleDateFormat")
    private String formatDate(LaunchViewModel vm) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(vm.launchDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String launchTime = date != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(date)
                : new SimpleDateFormat("yyyy-MM-dd").format(vm.launchDate());

        return resources.getString(R.string.launch_date, launchTime);
    }

    private void setImage(LaunchViewModel vm) {
        if(vm.missionPatch().isEmpty()){
            missionPatch.setImageResource(R.drawable.planet_express);
        } else {
            Picasso.get()
                    .load(vm.missionPatch())
                    .placeholder(R.drawable.planet_express)
                    .fit()
                    .centerCrop()
                    .into(missionPatch);
        }

    }
}
