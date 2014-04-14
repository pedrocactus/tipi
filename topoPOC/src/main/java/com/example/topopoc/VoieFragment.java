package com.example.topopoc;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.squareup.otto.Subscribe;

/**
 * Created by pierrecastex on 02/04/2014.
 */
public class VoieFragment extends Fragment implements Animation.AnimationListener {
    public static String TAG = "VoieFragment";
    private int voie;
    private TextView cotation;
    private TextView nom;
    private TextView description;
    private RelativeLayout layout;
    private Animation animMoveUpDescription;
    private Animation animMoveUpCotation;
    private Animation animMoveUpNom;
    private Animation animMoveDownNom;
    private Animation animMoveDownCotation;
    private Animation animMoveDownDescription;
    private AlphaAnimation animationFadeOut;
    private AlphaAnimation animationFadeIn;
    private GestureDetector gestureDetector;
    private int VIEWSTATE;
    private ImageView imageView;





    public VoieFragment(int voie){
        this.voie = voie;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.voie_fragment, container, false);

        imageView = (ImageView) view.findViewById(R.id.imageViewVoie);
        //imageView.setImageResource(R.drawable.fanny6a);
        final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading);

        ImageLoader.getInstance().displayImage(ConstantsTestUIL.IMAGES[voie], imageView, ((VoieSliderFragment) getParentFragment()).options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "Input/Output error";
                        break;
                    case DECODING_ERROR:
                        message = "Image can't be decoded";
                        break;
                    case NETWORK_DENIED:
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "Out Of Memory error";
                        break;
                    case UNKNOWN:
                        message = "Unknown error";
                        break;
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spinner.setVisibility(View.GONE);
            }
        });

        cotation = (TextView) view.findViewById(R.id.level);
        cotation.setText("6A");
        nom = (TextView) view.findViewById(R.id.nom_voie_textview);
        nom.setText("La déchirure");
        description = (TextView) view.findViewById(R.id.desciption_voie_textview);
        description.setText("Belle voie de ouf avec tout le toutim");



        layout = (RelativeLayout) view.findViewById(R.id.relative_layout_voiefragment);
        layout.setVisibility(View.INVISIBLE);

        setupAnimations();




        //Setting touch events for text animation, need for up and down swipes
        view.setOnTouchListener(new OnSwipeTouchListener(){
        public void onSwipeTop() {
            Toast.makeText(getActivity(), "TOP", Toast.LENGTH_SHORT).show();

            BusProvider.getInstance().post(new AnimationEvent(VIEWSTATE,"TOP"));

            /*if(VIEWSTATE==InfoPositionState.DOWN.state){
                VIEWSTATE=InfoPositionState.COTATION.state;
                layout.startAnimation(animMoveUpCotation);
                BusProvider.getInstance().post(InfoPositionState.COTATION);

            }else if(VIEWSTATE==InfoPositionState.COTATION.state){

                VIEWSTATE=InfoPositionState.NOM.state;
                layout.startAnimation(animMoveUpNom);
                BusProvider.getInstance().post(InfoPositionState.NOM);

            }else if(VIEWSTATE==InfoPositionState.NOM.state){

                VIEWSTATE=InfoPositionState.DESCRITPTION.state;
                layout.startAnimation(animMoveUpDescription);
                imageView.startAnimation(animationFadeIn);
                BusProvider.getInstance().post(InfoPositionState.DESCRITPTION);

            }*/

        }

        public void onSwipeBottom() {

            Toast.makeText(getActivity(),"DOWN",Toast.LENGTH_SHORT).show();

            BusProvider.getInstance().post(new AnimationEvent(VIEWSTATE, "BOTTOM"));


            /*if(VIEWSTATE==InfoPositionState.DESCRITPTION.state){

                VIEWSTATE=InfoPositionState.NOM.state;
                layout.startAnimation(animMoveDownDescription);
                imageView.startAnimation(animationFadeOut);
                BusProvider.getInstance().post(InfoPositionState.NOM);

            }else if(VIEWSTATE==InfoPositionState.NOM.state){

                VIEWSTATE=InfoPositionState.COTATION.state;
                layout.startAnimation(animMoveDownNom);
                BusProvider.getInstance().post(InfoPositionState.COTATION);

            }else if(VIEWSTATE==InfoPositionState.COTATION.state){

                VIEWSTATE=InfoPositionState.DOWN.state;
                layout.startAnimation(animMoveDownCotation);
                BusProvider.getInstance().post(InfoPositionState.DOWN);

            }*/


        }
    });

    view.setBackgroundColor(getActivity().getResources().getColor(android.R.color.black));

        return view;
    }

    @Subscribe
    public void startAnimationsOnEvent(AnimationEvent event){
    if(event.getDirection().equals("TOP")) {
        layout.setVisibility(View.VISIBLE);
        if (VIEWSTATE == InfoPositionState.DOWN.state) {
            VIEWSTATE = InfoPositionState.COTATION.state;
            ((App)getActivity().getApplication()).setVoiesViewState(InfoPositionState.COTATION);
            if(!this.isVisible()) {
               animMoveUpCotation.setDuration(0);
            }else{

                animMoveUpCotation.setDuration(400);
            }
            layout.startAnimation(animMoveUpCotation);

        } else if (VIEWSTATE == InfoPositionState.COTATION.state) {

            VIEWSTATE = InfoPositionState.NOM.state;
            ((App)getActivity().getApplication()).setVoiesViewState(InfoPositionState.NOM);
            if(!this.isVisible()) {
                animMoveUpNom.setDuration(0);
            }else{

                animMoveUpNom.setDuration(400);
            }
            layout.startAnimation(animMoveUpNom);

        } else if (VIEWSTATE == InfoPositionState.NOM.state) {

            VIEWSTATE = InfoPositionState.DESCRITPTION.state;
            ((App)getActivity().getApplication()).setVoiesViewState(InfoPositionState.DESCRITPTION);
            if(!this.isVisible()) {
                animMoveUpDescription.setDuration(0);
                animationFadeIn.setDuration(0);
            }else{

                animMoveUpDescription.setDuration(400);
                animationFadeIn.setDuration(400);
            }
            layout.startAnimation(animMoveUpDescription);
            imageView.startAnimation(animationFadeIn);

        }
    }else{
        if(VIEWSTATE==InfoPositionState.DESCRITPTION.state){

            VIEWSTATE=InfoPositionState.NOM.state;
            ((App)getActivity().getApplication()).setVoiesViewState(InfoPositionState.NOM);
            if(!this.isVisible()) {
                animMoveDownDescription.setDuration(0);
                animationFadeOut.setDuration(0);
            }else{

                animMoveDownDescription.setDuration(400);
                animationFadeOut.setDuration(400);
            }
            layout.startAnimation(animMoveDownDescription);
            imageView.startAnimation(animationFadeOut);

        }else if(VIEWSTATE==InfoPositionState.NOM.state){

            VIEWSTATE=InfoPositionState.COTATION.state;
            ((App)getActivity().getApplication()).setVoiesViewState(InfoPositionState.COTATION);
            if(!this.isVisible()) {
                animMoveDownNom.setDuration(0);
            }else{

                animMoveDownNom.setDuration(400);
            }
            layout.startAnimation(animMoveDownNom);

        }else if(VIEWSTATE==InfoPositionState.COTATION.state){

            VIEWSTATE=InfoPositionState.DOWN.state;
            ((App)getActivity().getApplication()).setVoiesViewState(InfoPositionState.DOWN);
            if(!this.isVisible()) {
                animMoveDownCotation.setDuration(0);
            }else{

                animMoveDownCotation.setDuration(400);
            }
            layout.startAnimation(animMoveDownCotation);

        }
    }

    }

    private void setupAnimations() {

        // load animations
        animMoveUpDescription = AnimationUtils.loadAnimation(getActivity(),
                R.anim.move_up_description);

        animMoveUpCotation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.move_up_cotation);

        animMoveUpNom = AnimationUtils.loadAnimation(getActivity(),
                R.anim.move_up_nom);

        animMoveDownNom = AnimationUtils.loadAnimation(getActivity(),
                R.anim.move_down_nom);

        animMoveDownCotation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.move_down_cotation);

        animMoveDownDescription = AnimationUtils.loadAnimation(getActivity(),
                R.anim.move_down_description);

        // set animation listeners
        animMoveUpDescription.setAnimationListener(this);
        animMoveUpCotation.setAnimationListener(this);
        animMoveUpNom.setAnimationListener(this);
        animMoveDownNom.setAnimationListener(this);
        animMoveDownCotation.setAnimationListener(this);
        animMoveDownDescription.setAnimationListener(this);

        animationFadeOut = new AlphaAnimation(0.4f, 1f);
        animationFadeOut.setDuration(400);
        animationFadeOut.setFillAfter(true);

        animationFadeIn = new AlphaAnimation(1f, 0.4f);
        animationFadeIn.setDuration(400);
        animationFadeIn.setFillAfter(true);

        if(((App)getActivity().getApplication()).getVoiesViewState() == -1){

            ((App)getActivity().getApplication()).setVoiesViewState(InfoPositionState.DOWN);
            VIEWSTATE = InfoPositionState.DOWN.state;

        }else if(((App)getActivity().getApplication()).getVoiesViewState()!=-1){

            VIEWSTATE = ((App)getActivity().getApplication()).getVoiesViewState();
            startAnimationsOnEvent(new AnimationEvent(VIEWSTATE, "TOP"));

        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation== animMoveDownCotation){
            layout.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public class OnSwipeTouchListener implements View.OnTouchListener {

        @SuppressWarnings("deprecation")
        private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

        public boolean onTouch(final View v, final MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {

                return true;
            }
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                onTouch(e);
                return true;
            }


            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffY) > Math.abs(diffX)) {
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                onSwipeBottom();
                            } else {
                                onSwipeTop();
                            }
                        }
                    } else {
                        // onTouch(e);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }
        public void onTouch(MotionEvent e) {
        }
        public void onSwipeRight() {
//call this only if your condition was set
        }

        public void onSwipeLeft() {
//nothing, this means,swipes to left will be ignored
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }




    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}
