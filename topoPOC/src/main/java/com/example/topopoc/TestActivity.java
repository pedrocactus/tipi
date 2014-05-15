package com.example.topopoc;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.j256.ormlite.dao.Dao;

import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlFolder;
import org.osmdroid.bonuspack.kml.KmlPlacemark;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by pierrecastex on 01/05/2014.
 */

public class TestActivity extends Activity {

    private String LOG_TAG = MainActivity.class.getSimpleName();
    private App dtoFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        dtoFactory = (App) getApplication();
        dtoFactory.createDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onclick(View view) throws SQLException {
        try {

            Dao<KmlPlacemark, Integer> pDao = dtoFactory.getKmlPlacemarkDao();
            String packageDir = "/mnt/sdcard/osmdroid";
            String p = Environment.getExternalStorageDirectory() + packageDir;
            File kerlou = new File(packageDir, "kerlou.kml");


            switch (view.getId()) {

                case R.id.btnInsert:
                    System.out.println("insert");
                    KmlDocument kmlDocument = new KmlDocument();
                    kmlDocument.parseKMLFile(kerlou);

                    KmlFolder feature = kmlDocument.mKmlRoot.clone();
                    ArrayList<KmlFeature> iTems = ((KmlFolder)feature.mItems.get(0)).mItems;
                    KmlFeature ffeature = null;

                    Iterator<KmlFeature> iter = iTems.iterator();

                    while (iter.hasNext()) {

                        ffeature = iter.next();

                        pDao.createOrUpdate((KmlPlacemark)ffeature);
                    }

                    break;

                case R.id.btnUpdate:
                    System.out.println("update");
                    break;


                case R.id.btnSelect:
                    System.out.println("select: " + pDao.countOf());
                    for (KmlPlacemark entity : pDao.queryForAll()) {
                        System.out.println(String.format("name: %s,Id: %s, Nome: %s, Tp: %s",entity.mName, entity.mExtendedData.get("nom"),entity.mExtendedData.get("niveau"), entity.mExtendedData.get("style")));
                        Log.d(LOG_TAG, String.format("name: %s,Id: %s, Nome: %s, Tp: %s",entity.mName, entity.mExtendedData.get("nom"),entity.mExtendedData.get("niveau"), entity.mExtendedData.get("style")));
                    }
                    break;

                case R.id.btnDelete:
                    System.out.println("delete");
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}