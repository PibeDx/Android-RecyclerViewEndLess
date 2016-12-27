package com.josecuentas.android_recyclerviewendless;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by jcuentas on 27/12/16.
 */

public abstract class EndlessRecyclerViewAdapter<D> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int RESOURCE_EMPTY = -1;
    public static final int TYPE_LOADING = 1;
    public static final int TYPE_DATA = 0;
    private ProgressViewHolder mProgressViewHolder;
    protected final List<D> data;
    private boolean footerVisible = false;
    private final Handler mainHandler;
    private int mResourceProgress = RESOURCE_EMPTY;

    public EndlessRecyclerViewAdapter(List<D> data) {
        this.data = data;
        mainHandler = new Handler(Looper.getMainLooper());
    }
    /*Si se desea un progress personalizado, se debe enviar el diseño del progress*/
    public void setResourceProgress(@LayoutRes int resourceProgress) {
        mResourceProgress = resourceProgress;
    }

    public abstract RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position);
    public void onBindProgressViewHolder(RecyclerView.ViewHolder holder, int position){};

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            if (mProgressViewHolder == null) {
                View view = findView(parent);
                mProgressViewHolder = new ProgressViewHolder(view);
                return mProgressViewHolder;
            } else return mProgressViewHolder;
        }
        return onCreateDataViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (TYPE_LOADING == holder.getItemViewType()) {
            //Override si se desea realizar alguna accion sobre la vista del progress
            this.onBindProgressViewHolder(holder, position);
        } else {
            this.onBindDataViewHolder(holder, position);
        }
    }

    @Override public int getItemCount() {
        return data.size() + (this.footerVisible ? 1 : 0);
    }

    @Override public int getItemViewType(int position) {
        if (position == this.data.size()) {
            return TYPE_LOADING;
        }
        return TYPE_DATA;
    }

    public void showLoadingIndicator() {
        this.footerVisible = true;
        this.mainHandler.post(new Runnable() {
            public void run() {
                EndlessRecyclerViewAdapter.this.notifyItemInserted(getItemCount());
            }
        });
    }

    public void removeLoadingIndicator() {
        this.footerVisible = false;
        this.mainHandler.post(new Runnable() {
            public void run() {
                EndlessRecyclerViewAdapter.this.notifyItemRemoved(getItemCount());
            }
        });
    }

    public boolean isRefreshing() {
        return footerVisible;
    }

    public void addNewPage(List<D> page) {
        this.footerVisible = false;
        this.data.addAll(page);
        this.mainHandler.post(new Runnable() {
            public void run() {
                EndlessRecyclerViewAdapter.this.notifyDataSetChanged();
            }
        });
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    private View findView(ViewGroup viewGroup) {
        View view;
        if (mResourceProgress == RESOURCE_EMPTY) {
            view = createProgressBar(viewGroup.getContext());
            return view;
        }
        view = LayoutInflater.from(viewGroup.getContext()).inflate(mResourceProgress, viewGroup, false);
        return view;
    }

    private View createProgressBar(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //layoutParams.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(layoutParams);

        ProgressBar progressBar = new ProgressBar(context);
        LinearLayout.LayoutParams lpProgressBar = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //lpProgressBar.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(lpProgressBar);

        progressBar.setIndeterminate(true);
        linearLayout.addView(progressBar);
        return linearLayout;
    }
}
