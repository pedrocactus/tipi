package com.pedrocactus.topobloc.app.database;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.table.DatabaseTableConfig;

import org.osmdroid.bonuspack.kml.KmlPlacemark;

import java.util.ArrayList;

/**
 * Created by pierrecastex on 01/05/2014.
 */
public class FieldConfig {


    public static  DatabaseTableConfig<KmlPlacemark> buildKmlPlacemarkTableConfig() {
        ArrayList<DatabaseFieldConfig> fieldConfigs = new ArrayList<DatabaseFieldConfig>();
        DatabaseFieldConfig fieldConfig = new DatabaseFieldConfig("mId");
        fieldConfig.setCanBeNull(true);
        fieldConfigs.add(fieldConfig);
        fieldConfig = new DatabaseFieldConfig("mName");
        fieldConfig.setId(true);
        fieldConfig.setCanBeNull(true);
        fieldConfigs.add(fieldConfig);
        fieldConfig = new DatabaseFieldConfig("mDescription");
        fieldConfig.setCanBeNull(true);
        fieldConfigs.add(fieldConfig);
        fieldConfig = new DatabaseFieldConfig("mExtendedData");
        fieldConfig.setCanBeNull(true);
        fieldConfig.setDataType(DataType.SERIALIZABLE);
        fieldConfigs.add(fieldConfig);

        DatabaseTableConfig<KmlPlacemark> tableConfig = new DatabaseTableConfig<KmlPlacemark>(KmlPlacemark.class, fieldConfigs);
        return tableConfig;
    }


    /**
     * Read and write some example data.

    private void readWriteData() throws Exception {
        // create an instance of Account
        String name = "Jim Coakley";
        Account account = new Account(name);
        // persist the account object to the database
        accountDao.create(account);

        Delivery delivery = new Delivery(new Date(), "Mr. Ed", account);
        // persist the account object to the database
        deliveryDao.create(delivery);

        Delivery delivery2 = deliveryDao.queryForId(delivery.getId());
        assertNotNull(delivery2);
        assertEquals(delivery.getId(), delivery2.getId());
        assertEquals(account.getId(), delivery2.getAccount().getId());
    }
     */
}
