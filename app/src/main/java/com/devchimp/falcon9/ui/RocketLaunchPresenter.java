package com.devchimp.falcon9.ui;

import com.devchimp.falcon9.network.SpaceXAPI;
import com.devchimp.falcon9.objects.Launch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.devchimp.falcon9.dependency.RetrofitModule.FALCON9;

public class RocketLaunchPresenter {

    private CompositeDisposable disposables;
    private LaunchListView launchListView;

    private final SpaceXAPI api;

    @Inject RocketLaunchPresenter(SpaceXAPI api) {
        this.api = api;
        disposables = new CompositeDisposable();
    }

    void attach(LaunchListView launchListView) {
        this.launchListView = launchListView;
    }

    void detach() {
        disposables.clear();
        this.launchListView = null;
    }

    void requestLaunchData() {
        disposables.add(api.getLaunches(FALCON9)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> onRetrieveLaunchDataStart())
                .doOnTerminate(this::onRetrieveLaunchDataFinish)
                .subscribe(this::onRetrieveLaunchDataSuccess, error -> onRetrieveLaunchDataError()));
    }

    private void onRetrieveLaunchDataStart() {
        if(isAttached()) {
            launchListView.showError(false);
            launchListView.showList(false);
            launchListView.showLoading(true);
        }
    }

    private void onRetrieveLaunchDataFinish() {
        if(isAttached()) {
            launchListView.showLoading(false);
            launchListView.showError(false);
        }
    }

    private void onRetrieveLaunchDataSuccess(List<Launch> launches) {
        final LaunchViewModel.Factory vmFactory = new LaunchViewModel.Factory();
        List<LaunchViewModel> viewModelList = new ArrayList<>();

        for (int i = 0; i < launches.size(); i++) {
            viewModelList.add(vmFactory.from(launches.get(i)));
        }

        if(isAttached()) {
            launchListView.showList(true);
            launchListView.updateLaunches(viewModelList);
        }
    }

    private void onRetrieveLaunchDataError() {
        if(isAttached()) {
            launchListView.showList(false);
            launchListView.showLoading(false);
            launchListView.showError(true);
        }
    }

    private boolean isAttached() {
        return (launchListView != null);
    }
}
