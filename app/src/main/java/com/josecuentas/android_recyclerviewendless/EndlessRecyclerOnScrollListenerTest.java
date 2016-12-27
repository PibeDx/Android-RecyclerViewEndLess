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

    private int preloadOffset = 2; // The minimum amount of items to have below your current scroll position before loading more.
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.

    int totalItemCount, completelyVisibleItemPosition;
    private int currentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        setLinearLayoutManager(recyclerView);

        completelyVisibleItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        totalItemCount = mLinearLayoutManager.getItemCount();

        //validamos para cuando el servicio ya no traiga datos
        if (loading && totalItemCount > previousTotal) { //
            loading = false;
            previousTotal = totalItemCount;
        }
        if (!loading && endlessScrollEnabled() && allViewVisibleOrLastViewReached() && dy >= 0) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }

        //TODO: tratamos de reemplazar lineas arriba
//        if (!enableLoading() && endlessScrollEnabled() && allViewVisibleOrLastViewReached() && dy >= 0) {
//            currentPage++;
//            onLoadMore(currentPage);
//            loading = true;
//        }
    }

    private boolean enableLoading() {
        if (loading && totalItemCount > previousTotal) { //
            loading = false;
            previousTotal = totalItemCount;
        }
        return loading;
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
    public abstract boolean endlessScrollEnabled();
}
