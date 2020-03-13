package com.devchimp.falcon9.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devchimp.falcon9.Falcon9Application;
import com.devchimp.falcon9.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class RocketLaunchFragment extends Fragment implements LaunchListView {
    private RocketLaunchAdapter adapter;
    private View errorView, loadingView;
    private RecyclerView recyclerView;
    @Inject RocketLaunchPresenter presenter;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Falcon9Application.getNetComponent(Objects.requireNonNull(getContext())).inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = parent.findViewById(R.id.launch_recycler);
        loadingView = parent.findViewById(R.id.loading_view);
        errorView = parent.findViewById(R.id.error_view);
        Button retry = parent.findViewById(R.id.retry_button);

        adapter = new RocketLaunchAdapter(new ArrayList<>()); // init with emptyList, update with api call later

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        retry.setOnClickListener(view -> presenter.requestLaunchData());

        presenter.requestLaunchData();

        return parent;
    }

    @Override public void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override public void onStop() {
        super.onStop();
        presenter.detach();
    }

    @Override
    public void showList(boolean show){
        recyclerView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLoading(boolean show) {
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateLaunches(List<LaunchViewModel> launches) {
        adapter.updateLaunches(launches);
    }

    @Override
    public void showError(boolean show) {
        errorView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
