package com.pedrocactus.topobloc.app.events;

import com.squareup.otto.Bus;

/**
 * Created by pcastex on 14/04/14.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
// No instances.
    }
}