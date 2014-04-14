package com.example.topopoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pierrecastex on 02/04/2014.
 */
public class VoieFragment extends Fragment implements Animation.AnimationListener {
    public static String TAG = "VoieFragment";
    private String voie;
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
    private int VIEWSTATE;
    private GestureDetector gestureDetector;




    public VoieFragment(String voie){
        this.voie = voie;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.voie_fragment, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewVoie);
        imageView.setImageResource(R.drawable.fanny6a);
        cotation = (TextView) view.findViewById(R.id.level);
        cotation.setText(voie);
        nom = (TextView) view.findViewById(R.id.nom_voie_textview);
        nom.setText("La déchirure");
        description = (TextView) view.findViewById(R.id.desciption_voie_textview);
        description.setText("Belle voie de ouf avec tout le toutim");

        VIEWSTATE = InfoPositionState.DOWN.state;

        layout = (RelativeLayout) view.findViewById(R.id.relative_layout_voiefragment);
        layout.setVisibility(View.INVISIBLE);

        setupAnimations();


        //Setting touch events for text animation, need for up and down swipes
        view.setOnTouchListener(new OnSwipeTouchListener(){
        public void onSwipeTop() {
            Toast.makeText(getActivity(), "TOP", Toast.LENGTH_SHORT).show();

            layout.setVisibility(View.VISIBLE);
            if(VIEWSTATE==InfoPositionState.DOWN.state){
                VIEWSTATE=InfoPositionState.COTATION.state;
                layout.startAnimation(animMoveUpCotation);

            }else if(VIEWSTATE==InfoPositionState.COTATION.state){

                VIEWSTATE=InfoPositionState.NOM.state;
                layout.startAnimation(animMoveUpNom);

            }else if(VIEWSTATE==InfoPositionState.NOM.state){

                VIEWSTATE=InfoPositionState.DESCRITPTION.state;
                layout.startAnimation(animMoveUpDescription);

            }

        }

        public void onSwipeBottom() {

            Toast.makeText(getActivity(),"DOWN",Toast.LENGTH_SHORT).show();


            if(VIEWSTATE==InfoPositionState.DESCRITPTION.state){
                VIEWSTATE=InfoPositionState.NOM.state;
                layout.startAnimation(animMoveDownDescription);

            }else if(VIEWSTATE==InfoPositionState.NOM.state){

                VIEWSTATE=InfoPositionState.COTATION.state;
                layout.startAnimation(animMoveDownNom);

            }else if(VIEWSTATE==InfoPositionState.COTATION.state){

                VIEWSTATE=InfoPositionState.DOWN.state;
                layout.startAnimation(animMoveDownCotation);

            }


        }
    });



        return view;
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

    public enum InfoPositionState {
        DOWN (0),
        COTATION (1),
        NOM (2),
        DESCRITPTION (3);

        public int state;

        InfoPositionState( int state){
            this.state = state;
        }

    }
}
