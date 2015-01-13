package com.pedrocactus.topobloc.app.service;


import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpRequest;
import retrofit.client.OkClient;
import retrofit.client.Request;

/**
 * Created by castex on 13/01/15.
 */
public class SignedOkClient extends OkClient {
    private static final String TAG = "SignedOkClient";

    private OAuthConsumer mConsumer =null;

    public SignedOkClient(OAuthConsumer consumer, OkHttpClient client) {
        super(client);
        mConsumer = consumer;
    }

    @Override
    protected HttpURLConnection openConnection(Request request)
            throws IOException {
        HttpURLConnection connection = super.openConnection(request);
        try {
            HttpRequest signedReq = mConsumer.sign(connection);
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }
        return connection;
    }
}