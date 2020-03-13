package com.devchimp.falcon9.ui;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;

public class Rx2TestRule implements TestRule {

    private final Function<Scheduler, Scheduler> handler;
    private final Function<Callable<Scheduler>, Scheduler> handler2;

    public Rx2TestRule(Scheduler scheduler) {
        this.handler = other -> scheduler;
        this.handler2 = callable -> scheduler;
    }

    @Override public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() {
                try {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                    RxJavaPlugins.setIoSchedulerHandler(handler);
                    RxJavaPlugins.setComputationSchedulerHandler(handler);
                    RxJavaPlugins.setNewThreadSchedulerHandler(handler);
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler(handler2);
                    RxAndroidPlugins.setMainThreadSchedulerHandler(handler);
                    base.evaluate();
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }
}
