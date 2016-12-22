package com.josecuentas.android_recyclerviewendless;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by jcuentas on 21/12/16.
 * base in https://gist.github.com/ssinss/e06f12ef66c51252563e
 * see https://gist.github.com/polbins/b6830d392e5c687c8e77
 */


public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int currentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

//    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
//        this.mLinearLayoutManager = linearLayoutManager;
//    }


    public void cancelLoading() {
        this.loading = false;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (mLinearLayoutManager == null) {
            //mLinearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        }


        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        Log.i(TAG, "onScrolled() called with: visibleItemCount = [" + visibleItemCount + "], totalItemCount = [" + totalItemCount + "], firstVisibleItem = [" + firstVisibleItem + "]");
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) //23
                <= (firstVisibleItem + visibleThreshold)) { //22
            // End has been reached

            // Do something
            currentPage++;

            onLoadMore(currentPage);

            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
