package com.pedrocactus.topobloc.app;

import android.util.Log;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

import javax.inject.Singleton;

import dagger.Provides;

/**
 * Created by pierrecastex on 23/11/2014.
 */
public class TopoblocModule {
/*
    @Provides
    @Singleton
    de.greenrobot.event.EventBus provideEventBus() {
        return de.greenrobot.event.EventBus.getDefault();
    }
*/
//    @Provides
//    @Singleton
//    JobManager provideJobManager() {
//        Configuration configuration = new Configuration.Builder(TopoblocApp.getInstance())
//                .customLogger(new CustomLogger() {
//                    private static final String TAG = "JOBS";
//                    @Override
//                    public boolean isDebugEnabled() {
//                        return true;
//                    }
//
//                    @Override
//                    public void d(String text, Object... args) {
//                        Log.d(TAG, String.format(text, args));
//                    }
//
//                    @Override
//                    public void e(Throwable t, String text, Object... args) {
//                        Log.e(TAG, String.format(text, args), t);
//                    }
//
//                    @Override
//                    public void e(String text, Object... args) {
//                        Log.e(TAG, String.format(text, args));
//                    }
//                })
//                .minConsumerCount(1)//always keep at least one consumer alive
//                .maxConsumerCount(3)//up to 3 consumers at a time
//                .loadFactor(3)//3 jobs per consumer
//                .consumerKeepAlive(120)//wait 2 minute
//                .build();
//        return new JobManager(TopoblocApp.getInstance(), configuration);
//    }
}
