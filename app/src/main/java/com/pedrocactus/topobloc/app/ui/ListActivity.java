package com.pedrocactus.topobloc.app.ui;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.pedrocactus.topobloc.app.R;
import com.pedrocactus.topobloc.app.ui.base.BaseActivity;

/**
 * Created by pierrecastex on 19/01/2015.
 */
public class ListActivity extends BaseActivity /*implements android.support.v7.widget.SearchView.OnQueryTextListener */{

    private Menu optionsMenu;

    private static final String SEARCH_QUERY = "search_text";

    private CharSequence queryText;

    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_list);
        if (savedInstanceState == null)
            initFragments();
    }

    private void initFragments() {

        ListFragment movieListFragment = new ListFragment();

        movieListFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, movieListFragment).commit();

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(SEARCH_QUERY)) {
            queryText = savedInstanceState.getCharSequence(SEARCH_QUERY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        this.optionsMenu = menu;
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.movie_list_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(this);
//
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
//        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        if(queryText!=null) {
//            searchView.setIconified(false);
//            if(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB) {
//                searchItem.expandActionView();
//            }
//            searchView.setQuery(queryText, true);
//
//
//        }
//
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.refresh:
//                eventBus.post(new AddJobEvent());
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    private void goToDetailFragment(long movieId) {
//
//        Intent detailIntent = new Intent(this, DetailActivity.class);
//        detailIntent.putExtra(MovieDetailFragment.PLACE_ID, movieId);
//        startActivity(detailIntent);
//
//    }
//
//    public void onEventMainThread(NavigationToDetailEvent event) {
//        goToDetailFragment(event.getId());
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String s) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        if (TextUtils.isEmpty(newText)) {
//            eventBus.post(new SearchFilterEvent(SearchFilterEvent.CLEAR, null));
//            queryText = null;
//        } else {
//            eventBus.post(new SearchFilterEvent(SearchFilterEvent.FILTER, newText));
//            queryText = newText;
//        }
//
//        return true;
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence(SEARCH_QUERY, queryText);
        super.onSaveInstanceState(outState);
    }
}