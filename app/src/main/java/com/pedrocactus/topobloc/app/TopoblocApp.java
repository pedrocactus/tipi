package com.pedrocactus.topobloc.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.osmdroid.bonuspack.kml.KmlPlacemark;

import java.sql.SQLException;

import dagger.ObjectGraph;

/**
 * Created by pcastex on 14/04/14.
 */
public class TopoblocApp extends Application {

    private static TopoblocApp instance;

    private SharedPreferences preferences;


    private int VOIES_VIEW_STATE = -1;

    public int getVoiesViewState(){
        return VOIES_VIEW_STATE;
    }

    public void setVoiesViewState(InfoPositionState viewState){
        VOIES_VIEW_STATE = viewState.state;

    }

    @Override
    public void onCreate() {
        if (ConstantsTestUIL.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }

        super.onCreate();
        getObjectGraph();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        initImageLoader(getApplicationContext());
    }

    public TopoblocApp() {
        instance = this;
    }

    public static TopoblocApp getInstance() {
        return instance;
    }

    private ObjectGraph objectGraph;

    public synchronized ObjectGraph getObjectGraph() {
        objectGraph = ObjectGraph.create(new TopoblocModule());
        return objectGraph;
    }

    public static void injectMembers(Object object) {
        getInstance().objectGraph.inject(object);
    }

    public void createDB(){
//        databaseHelper = new DataBaseHelper(this);
//        databaseHelper.getWritableDatabase();

    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }



    public void saveStringInPreferences(String object,String tag){
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(tag, object);
        prefsEditor.commit();
    }
    public String getStringFromSharedPreferences(String tag ){
        return preferences.getString(tag, "");
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
//        if (databaseHelper != null) {
//            OpenHelperManager.releaseHelper();
//            databaseHelper = null;
//        }
    }

}
