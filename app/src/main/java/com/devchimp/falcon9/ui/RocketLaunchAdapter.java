package com.devchimp.falcon9.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devchimp.falcon9.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RocketLaunchAdapter extends RecyclerView.Adapter<RocketLaunchViewHolder> {

    private List<LaunchViewModel> dataList;

    RocketLaunchAdapter(List<LaunchViewModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RocketLaunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        final View view = inflater.inflate(R.layout.launch_item, parent, false);
        return new RocketLaunchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RocketLaunchViewHolder holder, int position) {
        final LaunchViewModel vm = dataList.get(position);

        holder.bind(vm);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    void updateLaunches(List<LaunchViewModel> launches) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(this.dataList, launches));
        this.dataList.clear();
        this.dataList.addAll(launches);
        result.dispatchUpdatesTo(this);
    }

    static class DiffCallback extends DiffUtil.Callback {
        private final List<LaunchViewModel> prev, current;

        private DiffCallback(List<LaunchViewModel> prev, List<LaunchViewModel> current) {
            this.prev = prev == null ? new ArrayList<>() : prev;
            this.current = current == null ? new ArrayList<>() : current;
        }

        @Override
        public int getOldListSize() {
            return prev.size();
        }

        @Override
        public int getNewListSize() {
            return current.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return Objects.equals(prev.get(oldItemPosition), current.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return false;
        }
    }
}