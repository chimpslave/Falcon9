package com.devchimp.falcon9.dependency;

import com.devchimp.falcon9.ui.RocketLaunchFragment;
import com.devchimp.falcon9.ui.RocketLaunchPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface NetworkComponent {

    void inject(RocketLaunchFragment fragment);
}
