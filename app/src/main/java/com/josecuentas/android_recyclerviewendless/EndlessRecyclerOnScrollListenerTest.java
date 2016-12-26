package com.josecuentas.android_recyclerviewendless;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by jcuentas on 21/12/16.
 * base in https://gist.github.com/ssinss/e06f12ef66c51252563e
 * see https://gist.github.com/polbins/b6830d392e5c687c8e77
 */


public abstract class EndlessRecyclerOnScrollListenerTest extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListenerTest.class.getSimpleName();

    private int preloadOffset = 1; // The minimum amount of items to have below your current scroll position before loading more.
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.

    int totalItemCount, completelyVisibleItemPosition;
    private int currentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //Log.d(TAG, "onScrolled() called with: dx = [" + dx + "], dy = [" + dy + "]");
        setLinearLayoutManager(recyclerView);

        completelyVisibleItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        totalItemCount = mLinearLayoutManager.getItemCount();

        if (loading && totalItemCount > previousTotal) { //
            loading = false;
            previousTotal = totalItemCount;
        }

        if (!loading && allViewVisibleOrLastViewReached() && dy >= 0) { //
            // End has been reached

            // Do something
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    private void setLinearLayoutManager(RecyclerView recyclerView){
        if (mLinearLayoutManager == null)
            mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    private boolean allViewVisibleOrLastViewReached() {
        return totalItemCount - completelyVisibleItemPosition <= preloadOffset;
    }

    public int getPreloadOffset() {
        return preloadOffset;
    }

    public void setPreloadOffset(int preloadOffset) {
        this.preloadOffset = preloadOffset;
    }

    public abstract void onLoadMore(int currentPage);
}
