package com.pedrocactus.topobloc.app;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;
import com.path.android.jobqueue.network.NetworkUtilImpl;
import com.pedrocactus.topobloc.app.events.NetworkErrorEvent;
import com.pedrocactus.topobloc.app.job.NationalSiteJob;
import com.pedrocactus.topobloc.app.job.SectorJob;
import com.pedrocactus.topobloc.app.service.SignedOkClient;
import com.pedrocactus.topobloc.app.service.TopoblocAPI;
import com.pedrocactus.topobloc.app.ui.DetailFragment;
import com.pedrocactus.topobloc.app.ui.MainActivity;
import com.pedrocactus.topobloc.app.ui.MapFragment;
import com.pedrocactus.topobloc.app.ui.MapboxFragment;
import com.pedrocactus.topobloc.app.ui.utils.JacksonConverter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Cache;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by pierrecastex on 23/11/2014.
 */

@Module(
        injects = {
                MainActivity.class,
                MapboxFragment.class,
                MapFragment.class,
                DetailFragment.class,
                SectorJob.class,
                NationalSiteJob.class,
                TopoblocApp.class,
                JobManager.class
        }
)
public class TopoblocModule {

    @Provides
    @Singleton
    de.greenrobot.event.EventBus provideEventBus() {
        return de.greenrobot.event.EventBus.getDefault();
    }

    @Provides
    @Singleton
    JobManager provideJobManager() {
        Configuration configuration = new Configuration.Builder(TopoblocApp.getInstance())
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
        return new JobManager(TopoblocApp.getInstance(), configuration);
    }

    @Provides
    TopoblocAPI provideApiClient() {


//        OAuthConsumer consumer = new DefaultOAuthConsumer(
//                Config.API_KEY,
//                Config.API_KEY_SECRET);
//        consumer.setTokenWithSecret(token, tokenSecret);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Config.PRODUCTION_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new JacksonConverter(new ObjectMapper()));
        //Implementing cache for retrofit calls
        //Simple cache strategy with caching reading when the network is not available
        //(cache memory up to 10 mb)
        OkHttpClient okHttpClient = new OkHttpClient();
        File cacheDir = TopoblocApp.getInstance().getCacheDir();
        Cache cache = null;
        try {
            cache = new Cache(cacheDir, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        okHttpClient.setCache(cache);
        builder.setClient(new OkClient(okHttpClient)/*new SignedOkClient(consumer,okHttpClient)*/);
        //Adding Interceptor for cache request only behaviors
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                NetworkUtilImpl util = new NetworkUtilImpl(TopoblocApp.getInstance());
                if (util.isConnected(TopoblocApp.getInstance())) {
                    int maxAge = 0;
                    request.addHeader("Cache-Control", "public, max-age=" + maxAge);
                } else {
                    de.greenrobot.event.EventBus.getDefault().post(new NetworkErrorEvent(NetworkErrorEvent.CACHE));
                    int maxStale = 60 * 60 * 24 * 8; // tolerate 8 days stale
                    request.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
                }
            }
        });
        return builder.build().create(TopoblocAPI.class);
    }
}
