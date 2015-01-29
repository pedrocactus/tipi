package com.pedrocactus.topobloc.app.ui.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.ItemizedIconOverlay;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.Overlay;
import com.mapbox.mapboxsdk.views.MapView;
import com.mapbox.mapboxsdk.views.util.Projection;
import com.pedrocactus.topobloc.app.model.Route;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;

import java.util.List;

/**
 * Created by castex on 23/01/15.
 */
public class TextOverlay extends Overlay implements Overlay.Snappable {

    protected final Paint mPaint = new Paint();
    protected MapView mMapView;

    private final Rect mRect = new Rect();
    private final PointF mCurScreenCoords = new PointF();

    private Route mFocusedRoute;



    private final float[] mMatrixValues = new float[9];
    private final GeoPoint mGeoPoint = new GeoPoint(0, 0);
    private final Matrix mMatrix = new Matrix();
    private List<Route> routes;

    protected OnItemGestureListener<Route> mOnItemGestureListener;




    public TextOverlay(final Context ctx, final MapView mapView, List<Route> routes,final TextOverlay.OnItemGestureListener<Route> onGestureListener) {
        this(ctx, mapView, new DefaultResourceProxyImpl(ctx));
        mMapView = mapView;
        this.routes = routes;
        mOnItemGestureListener = onGestureListener;
    }

    public TextOverlay(final Context ctx, final MapView mapView,
                             final ResourceProxy pResourceProxy) {
        super(ctx);
        mMapView = mapView;
    }



    @Override
    protected void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (shadow) {
            return;
        }
        canvas.getMatrix(mMatrix);
        mMatrix.getValues(mMatrixValues);

            final float tx = (-mMatrixValues[Matrix.MTRANS_X] + 200)
                    / mMatrixValues[Matrix.MSCALE_X];
            final float ty = (-mMatrixValues[Matrix.MTRANS_Y] + 900)
                    / mMatrixValues[Matrix.MSCALE_Y];

//        canvas.drawText("some text", tx, ty + 5, this.mPaint);
        mPaint.setTextSize(50);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mPaint.setColor(Color.RED);



        if (shadow) {
            return;
        }

        final Projection pj = mapView.getProjection();
        final int size = this.routes.size() - 1;

                /* Draw in backward cycle, so the items with the least index are on the front. */
        for (int i = size; i >= 0; i--) {
            final Route route = routes.get(i);
            pj.toMapPixels(new LatLng(route.getCoordinates()[1],route.getCoordinates()[0]), mCurScreenCoords);

//            onDrawItem(canvas, route, mCurScreenCoords);
            canvas.drawText(route.getNumber()+"" , mCurScreenCoords.x, mCurScreenCoords.y + 5, this.mPaint);
        }




    }

//    protected onDrawItem(final Canvas canvas, final Route route, final Point curScreenCoords){
//        final int state = (mDrawFocusedItem && (mFocusedItem == route) ? OverlayItem.ITEM_STATE_FOCUSED_MASK
//                : 0);
//        final Drawable marker = (item.getMarker(state) == null) ? getDefaultMarker(state) : item
//                .getMarker(state);
//        final Marker.HotspotPlace hotspot = route.getMarkerHotspot();
//
//        boundToHotspot(marker, hotspot);
//
//        // draw it
//        Overlay.drawAt(canvas, marker, curScreenCoords.x, curScreenCoords.y, false);
//
//    }
//
//    protected synchronized Drawable boundToHotspot(Drawable marker, Marker.HotspotPlace hotspot) {
//        int markerWidth = (int) (marker.getIntrinsicWidth() * mScale);
//        int markerHeight = (int) (marker.getIntrinsicHeight() * mScale);
//
//        mRect.set(0, 0, 0 + markerWidth, 0 + markerHeight);
//
//        if (hotspot == null)
//            hotspot = HotspotPlace.BOTTOM_CENTER;
//
//        switch (hotspot) {
//            default:
//            case NONE:
//                break;
//            case CENTER:
//                mRect.offset(-markerWidth / 2, -markerHeight / 2);
//                break;
//            case BOTTOM_CENTER:
//                mRect.offset(-markerWidth / 2, -markerHeight);
//                break;
//            case TOP_CENTER:
//                mRect.offset(-markerWidth / 2, 0);
//                break;
//            case RIGHT_CENTER:
//                mRect.offset(-markerWidth, -markerHeight / 2);
//                break;
//            case LEFT_CENTER:
//                mRect.offset(0, -markerHeight / 2);
//                break;
//            case UPPER_RIGHT_CORNER:
//                mRect.offset(-markerWidth, 0);
//                break;
//            case LOWER_RIGHT_CORNER:
//                mRect.offset(-markerWidth, -markerHeight);
//                break;
//            case UPPER_LEFT_CORNER:
//                mRect.offset(0, 0);
//                break;
//            case LOWER_LEFT_CORNER:
//                mRect.offset(0, markerHeight);
//                break;
//        }
//        marker.setBounds(mRect);
//        return marker;
//    }
private boolean activateSelectedItems(final MotionEvent event,
                                      final MapView mapView,
                                      final ActiveItem task) {
    final Projection projection = mapView.getProjection();
    final float x = event.getX();
    final float y = event.getY();
    for (int i = 0; i < this.routes.size(); ++i) {
        final Route item = routes.get(i);
//        if (markerHitTest(item, projection, x, y)) {
//            if (task.run(i)) {
//                this.setFocus(item);
//                return true;
//            }
//        }
    }
    return false;
}

    @Override
    public boolean onSnapToItem(int i, int i2, Point point, MapView mapView) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(final MotionEvent event, final MapView mapView) {
        return (activateSelectedItems(event, mapView, new ActiveItem() {
            @Override
            public boolean run(final int index) {
                final TextOverlay that = TextOverlay.this;
                if (that.mOnItemGestureListener == null) {
                    return false;
                }
                return onSingleTapUpHelper(index, that.routes.get(index), mapView);
            }
        }));
    }
    protected boolean onSingleTapUpHelper(final int index, final Route item,
                                          final MapView mapView) {
        return this.mOnItemGestureListener.onItemSingleTapUp(index, item);
    }

    public static interface OnItemGestureListener<T> {
        public boolean onItemSingleTapUp(final int index,final T item);
        public boolean onItemLongPress(final int index,final T item);
    }
    public static interface ActiveItem {
        public boolean run(final int aIndex);
    }
}