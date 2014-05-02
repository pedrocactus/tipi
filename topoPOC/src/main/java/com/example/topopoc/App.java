package com.example.topopoc;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Config;

import com.example.topopoc.database.DataBaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlPlacemark;

import java.sql.SQLException;

/**
 * Created by pcastex on 14/04/14.
 */
public class App extends Application {


    private SharedPreferences preferences;
    private DataBaseHelper databaseHelper = null;

    private Dao<KmlPlacemark, Integer> kmlDocumentDAO = null;


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

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        initImageLoader(getApplicationContext());
    }

    public void createDB(){
        databaseHelper = new DataBaseHelper(this);
        databaseHelper.getWritableDatabase();

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


    public SharedPreferences getPreferences() {return preferences;}



    public Dao<KmlPlacemark, Integer> getKmlPlacemarkDao() throws SQLException {
        if (kmlDocumentDAO == null) {
            kmlDocumentDAO = databaseHelper.getPlacemarkDAO();
        }
        return kmlDocumentDAO;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
