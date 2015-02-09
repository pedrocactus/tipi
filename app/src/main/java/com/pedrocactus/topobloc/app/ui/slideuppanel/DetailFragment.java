package com.pedrocactus.topobloc.app.ui.slideuppanel;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.path.android.jobqueue.JobManager;
import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.events.FetchDetailPlaceEvent;
import com.pedrocactus.topobloc.app.model.Place;
import com.pedrocactus.topobloc.app.model.Route;
import com.pedrocactus.topobloc.app.ui.base.BaseFragment;
import com.pedrocactus.topobloc.app.ui.slideuppanel.view.CustomDetailView;
import com.pedrocactus.topobloc.app.ui.slideuppanel.view.DetailView;
import com.pedrocactus.topobloc.app.ui.slideuppanel.view.RatingDialog;
import com.pedrocactus.topobloc.app.ui.slideuppanel.view.RouteDetailView;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by castex on 14/01/15.
 */
public class DetailFragment extends BaseFragment {

    public static final String PLACE_ID = "detailPlace";
    public static final String PLACE_INDEX = "indexlace";

    //CustomView with binding method to movie model
    private CustomDetailView detailView;
    private Place place;
    private int placeIndex;

    @Inject
    JobManager jobManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.panel_description_layout, container,false);
        ButterKnife.inject(this, view);

        place = getArguments().getParcelable(PLACE_ID);
        placeIndex = getArguments().getInt(PLACE_INDEX);
        if(place instanceof Route) {

            detailView = new RouteDetailView(getActivity());
            ((RouteDetailView) detailView).setupListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showEditDialog();
                }
            });
        }else{

            detailView = new DetailView(getActivity());
        }

        detailView.bindModel(place, placeIndex);
        return (View) detailView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if(savedInstanceState!=null){
//            place = savedInstanceState.getParcelable("place");
//        }else {



//        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("place", place);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ((BaseActivity) getActivity()).setActionBar(null, true);
    }

    public void onEventMainThread(FetchDetailPlaceEvent event) {
        place = event.getPlace();
        updatePlace();
    }
//    public void onEventMainThread(AddJobEvent event) {
//        fetchMovie(movie_id);
//    }
//    public void onEventMainThread(NetworkErrorEvent event) {
//        if(event.getErrorType()==NetworkErrorEvent.OTHER) {
//            movieDetailView.setOnNetworkErrorLayout(true);
//        }
//    }
    private void updatePlace(){
        detailView.bindModel(place, placeIndex);

    }
//    public void onEventMainThread(SaveCommentEvent event) {
//        if(event.getComment()!=null) {
//            movieDetailView.setUserReview(event.getComment());
//            movieDetailView.setUserRating(event.getRatingStars());
//            Review review = new Review();
//            review.setRating(event.getRatingStars());
//            review.setReview(event.getComment());
//            BoxotopApp.getInstance().saveObjectInPreferences(review, movie.getTitle());
//        }
//        movieDetailView.enableRating(true);
//    }

    private void showEditDialog() {
        RatingDialog editNameDialog = RatingDialog.newInstance("yes","flash");
        editNameDialog.show(((ActionBarActivity)getActivity()).getSupportFragmentManager(), RatingDialog.TAG);
    }

}
