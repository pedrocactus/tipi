package com.example.topopoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlFolder;
import org.osmdroid.bonuspack.kml.Style;
import org.osmdroid.bonuspack.overlays.FolderOverlay;
import org.osmdroid.tileprovider.MapTileProviderArray;
import org.osmdroid.tileprovider.modules.IArchiveFile;
import org.osmdroid.tileprovider.modules.MBTilesFileArchive;
import org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.topopoc.views.VoieBulle;

public class MapFragment extends Fragment {

    KmlDocument kmlDocument;
    KmlDocument kmlSecteurs;
	private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
	private DefaultResourceProxyImpl mResourceProxy;
    private MapView mapView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/**
		 * This whole thing revolves around instantiating a MapView class, way,
		 * way below. And MapView requires a ResourceProxy. Who are we to deny
		 * its needs? Let's create one!
		 * 
		 * It would have been nice if this was taken care of in the MapView
		 * constructor. Interestingly MapView *has* a constructor that creates a
		 * new DefaultResourceProxyImpl but unfortunately that one doesn't allow
		 * us to specify the parameters we *do* need to set ...
		 */
		mResourceProxy = new DefaultResourceProxyImpl(this.getActivity());

		/**
		 * A class that implements the ITileSource interface knows how to
		 * convert an InputStream or a file path into a Drawable. It doesn't do
		 * much more than that. The real 'sourcery' is performed by
		 * MapTileFileArchiveProvider which will be introduced shortly.
		 * 
		 * What we need is really a BitmapTileSourceBase instance, but this
		 * class is defined as abstract. XYTileSource is not and comes closest
		 * to what we want.
		 * 
		 * Comment: I don't quite get why BitmapTileSource base is abstract; it
		 * doesn't contain any abstract methods.
		 */
		XYTileSource tSource;
		tSource = new XYTileSource("mbtiles",
				ResourceProxy.string.offline_mode, 8, 22, 256, ".png",
				null);

		/**
		 * Don't think the name SimpleRegisterReceiver is particularly well
		 * chosen. SimpleReceiverRegistrar would have been better because the
		 * only thing SimpleRegisterReceiver does, is wrap the methods
		 * Context.registerReceiver(..) and Context.unregisterReceiver(..). Have
		 * a look at the source if you don't believe me ;-).
		 * 
		 * So why does it exist then?? Don't know, but it's quite possible to
		 * just ignore this step and state the Activity implements
		 * IRegisterReceiver and replace the 'simpleReceiver' variable with
		 * 'this' further down (no additional implementation required).
		 */
		SimpleRegisterReceiver sr = new SimpleRegisterReceiver(getActivity());

		/**
		 * The following looks complicated, but really only creates an
		 * iArchiveFile[]. Apparently Marc Kurtz and Nicolas Gramlich, the
		 * authors of MapTileFileArchiveProvider, figured it might be useful to
		 * support multiple files/sources. I guess that might make sense if
		 * you're providing separate files for, for example, cities.
		 * 
		 * They also provided quite a bit of logic in MapTileFileArchiveProvider
		 * for handling SD Card inserts and ejects. Additionally, if files are
		 * not explicitly specified they can be dynamically loaded from the
		 * /mnt/sdcard/osmdroid directory, which is a nice feature.
		 */
		String packageDir = "/mnt/sdcard/osmdroid";
		String p = Environment.getExternalStorageDirectory() + packageDir;
		File f = new File(packageDir, "testpoly.mbtiles");
		IArchiveFile[] files = { MBTilesFileArchive.getDatabaseFileArchive(f) };

		MapTileModuleProviderBase moduleProvider;
		moduleProvider = new MapTileFileArchiveProvider(sr, tSource, files);

		/**
		 * So at this point we have a MapTileModuleProvider that provides
		 * MapTileModules: a MapTileModule looks at one or more sources, which
		 * are *not* ITileSources but IArchiveFiles and provides MapTiles. What,
		 * then, does MapTileProviderArray do? Well, it just adds another layer
		 * to the complexity cake: this makes it possible to set multiple
		 * MapTileModuleProviders, such as an on- and offline source. I'm sure
		 * it's useful for someone, but for simple applications it's probably
		 * too much.
		 */
		MapTileModuleProviderBase[] pBaseArray;
		pBaseArray = new MapTileModuleProviderBase[] { moduleProvider };

		MapTileProviderArray provider;
		provider = new MapTileProviderArray(tSource, null, pBaseArray);

		/**
		 * Are we there yet??? Create the MapView already!
		 */
		mapView = new MapView(getActivity(), 256, mResourceProxy,
				provider);
		mapView.setBuiltInZoomControls(true);

		mapView.getController().setZoom(20); // set initial zoom-level, depends

        //Paris
		//mapView.getController().setCenter(new GeoPoint(48.6365, 2.439));

		mapView.setUseDataConnection(true);

        //Petit Paradis
		GeoPoint point3 = new GeoPoint(48.6590, -4.3903); // icon
														// goes
														// here
		mapView.getController().setCenter(point3);

		ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
		// Put overlay icon a little way from map centre
		items.add(new OverlayItem("Here", "SampleDescription", point3));

		/* OnTapListener for the Markers, shows a simple Toast. */
		this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
				new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
					@Override
					public boolean onItemSingleTapUp(final int index,
							final OverlayItem item) {
						Intent intent = new Intent(getActivity(), PanoramaActivity.class);
						startActivity(intent);
						return true; // We 'handled' this event.
					}

					@Override
					public boolean onItemLongPress(final int index,
							final OverlayItem item) {
						Toast.makeText(getActivity(),
								"Item '" + item.getTitle(), Toast.LENGTH_LONG)
								.show();
						return false;
					}
				}, mResourceProxy);
		//mapView.getOverlays().add(this.mMyLocationOverlay);
		mapView.invalidate();


        //Create KML document for Petit Paradis boulders
        kmlDocument = new KmlDocument();

        //Create KML document for Secteurs
        kmlSecteurs = new KmlDocument();

        //Get KML path
        File kerlou = new File(packageDir, "kerlou.kml");
        File secteurs = new File(packageDir, "secteurs-bivouac.kml");




        kmlDocument.parseFile(kerlou);
        kmlSecteurs.parseFile(secteurs);


        KmlFolder feature = kmlDocument.mKmlRoot.clone();

        Drawable defaultMarker = getResources().getDrawable(R.drawable.marker);
        Bitmap defaultBitmap = ((BitmapDrawable)defaultMarker).getBitmap();
        Style defaultStyle = new Style(defaultBitmap, 0x901010AA, 3.0f, 0x20AA1010);



        ArrayList<KmlFeature> iTems = ((KmlFolder)feature.mItems.get(0)).mItems;
        int nbItmes = iTems.size();
        Iterator<KmlFeature> iter = iTems.iterator();
        KmlFeature ffeature = null;

        while (iter.hasNext()) {

            ffeature = iter.next();
            if (ffeature.mExtendedData.get("style")==null||!ffeature.mExtendedData.get("style").contains("dalle")) {
                iter.remove();
            }
        }
        KmlFeature.Styler styler = new VoieBulle(defaultMarker);
        FolderOverlay kmlOverlay = (FolderOverlay)feature.buildOverlay(mapView, defaultStyle,styler, kmlDocument);

        mapView.getOverlays().add(kmlOverlay);

        mapView.invalidate();


        KmlFeature featureSecteur = kmlSecteurs.mKmlRoot.clone();

        FolderOverlay kmlOverlaySecteur = (FolderOverlay)featureSecteur.buildOverlay(mapView, defaultStyle, styler, kmlSecteurs);

        mapView.getOverlays().add(kmlOverlaySecteur);

        mapView.invalidate();

		// Inflate the layout for this fragment
		return mapView;
	}

    public void filerStyle(String style){

        mapView.getOverlays().clear();
        KmlFolder feature = kmlDocument.mKmlRoot.clone();

        Drawable defaultMarker = getResources().getDrawable(R.drawable.marker);
        Bitmap defaultBitmap = ((BitmapDrawable)defaultMarker).getBitmap();
        Style defaultStyle = new Style(defaultBitmap, 0x901010AA, 3.0f, 0x20AA1010);



        ArrayList<KmlFeature> iTems = ((KmlFolder)feature.mItems.get(0)).mItems;
        int nbItmes = iTems.size();
        Iterator<KmlFeature> iter = iTems.iterator();
        KmlFeature ffeature = null;
         if(!style.equals("tout")) {
            while (iter.hasNext()) {

                ffeature = iter.next();
                if (ffeature.mExtendedData.get("style") == null || !ffeature.mExtendedData.get("style").contains(style)) {
                    iter.remove();
                }
            }
        }

        KmlFeature.Styler styler = new VoieBulle(defaultMarker);
        FolderOverlay kmlOverlay = (FolderOverlay)feature.buildOverlay(mapView, defaultStyle,styler, kmlDocument);


        mapView.getOverlays().add(kmlOverlay);

        mapView.invalidate();

    }

}
