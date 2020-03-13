package com.devchimp.falcon9.ui;

import com.devchimp.falcon9.network.SpaceXAPI;
import com.devchimp.falcon9.objects.Launch;
import com.devchimp.falcon9.objects.Links;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RocketLaunchPresenterTest {

    @Rule public final Rx2TestRule rx2TestRule = new Rx2TestRule(Schedulers.trampoline());
    private Observable<List<Launch>> launchList;

    @Mock LaunchListView launchListView;
    @Mock SpaceXAPI spaceXAPI;
    @Mock Launch launch1;
    @Mock Links links;

    RocketLaunchPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockLaunch();
        initFreshList();
        when(spaceXAPI.getLaunches(anyString())).thenReturn(launchList);
        presenter = new RocketLaunchPresenter(spaceXAPI);
    }

    private void mockLaunch(){
        when(launch1.getMission_name()).thenReturn("fly into the sun");
        when(launch1.getLaunch_date_utc()).thenReturn("12-03-20");
        when(launch1.isLaunch_success()).thenReturn(true);
        when(launch1.getLinks()).thenReturn(links);
        when(links.getMission_patch()).thenReturn("fly_into_the_sun.jpeg");
    }

    private void mockErrorLaunch(){
        when(launch1.getMission_name()).thenReturn(null);
        when(launch1.getLaunch_date_utc()).thenReturn(null);
        when(launch1.isLaunch_success()).thenReturn(false);
        when(launch1.getLinks()).thenReturn(null);
        when(links.getMission_patch()).thenReturn(null);
    }
    private void initFreshList(){
        List<Launch> launches = new ArrayList<>();
        launches.add(launch1);

        launchList = Observable.just(launches);
    }

    @Test
    public void whenDataRequested_thenViewShowsLoadingScreen(){
        presenter.attach(launchListView);
        presenter.requestLaunchData();
        verify(launchListView).showLoading(true);
    }

    @Test
    public void whenDataRequested_andDataReceived_thenViewHidesLoadingScreen(){
        presenter.attach(launchListView);
        presenter.requestLaunchData();
        verify(launchListView).showLoading(false);
    }

    @Test
    public void whenDataRequested_andDataReceived_thenViewShowsList(){
        presenter.attach(launchListView);
        presenter.requestLaunchData();
        verify(launchListView).showList(true);
    }

    @Test
    public void whenDataRequested_andRequestEncountersError_thenViewShowsErrorScreen(){
        mockErrorLaunch();
        initFreshList();
        presenter.attach(launchListView);
        presenter.requestLaunchData();
        verify(launchListView).showError(true);
    }

}
