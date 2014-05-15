package com.example.topopoc;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pcastex on 02/05/14.
 */
public class SqliteImageDownloader extends BaseImageDownloader {

    private static final String SCHEME_DB = "db";
    private static final String DB_URI_PREFIX = SCHEME_DB + "://";

    public SqliteImageDownloader(Context context) {
        super(context);
    }

    @Override
    protected InputStream getStreamFromOtherSource(String imageUri, Object extra) throws IOException {
        if (imageUri.startsWith(DB_URI_PREFIX)) {
            String path = imageUri.substring(DB_URI_PREFIX.length());

            // Your logic to retreive needed data from DB
            byte[] imageData =null;

            return new ByteArrayInputStream(imageData);
        } else {
            return super.getStreamFromOtherSource(imageUri, extra);
        }
    }
}