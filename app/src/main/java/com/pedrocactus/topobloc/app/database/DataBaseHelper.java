package com.pedrocactus.topobloc.app.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;

import org.osmdroid.bonuspack.kml.KmlPlacemark;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "db_secteurs.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<org.osmdroid.bonuspack.kml.KmlPlacemark, java.lang.Integer> placeMarkDAO;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onCreate");
            DatabaseTableConfig<KmlPlacemark> tableConfig = FieldConfig.buildKmlPlacemarkTableConfig();
            placeMarkDAO = DaoManager.createDao(connectionSource, tableConfig);
            TableUtils.createTable(connectionSource, tableConfig);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, FieldConfig.buildKmlPlacemarkTableConfig(), true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<org.osmdroid.bonuspack.kml.KmlPlacemark, java.lang.Integer> getPlacemarkDAO(){
        return placeMarkDAO;
    }

    @Override
    public void close() {
        super.close();
    }
}
