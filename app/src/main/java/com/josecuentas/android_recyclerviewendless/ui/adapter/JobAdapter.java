package com.josecuentas.android_recyclerviewendless.ui.adapter;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.josecuentas.android_recyclerviewendless.R;
import com.josecuentas.android_recyclerviewendless.model.Job;

import java.util.List;

/**
 * Created by jcuentas on 21/12/16.
 */

public class JobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ROW_ITEM = 0;
    private static final int ROW_PROGRES = 1;

    private List<Job> mDataList;
    private boolean isProgressVisible = false;
    private final Handler mMainHandler; //Esta notificacion no se puede dar en el hilo principal

    public JobAdapter(List<Job> jobList) {
        mDataList = jobList;
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == ROW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job,parent, false);
            vh = new ViewHolder(view);
            return vh;
        } else { //ROW_PROGRES
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_progress,parent, false);
            vh = new ProgressViewHolder(view);
            return vh;
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            final Job job = mDataList.get(position);
            String name = job.getName();
            int number = position + 1;
            viewHolder.mTviName.setText(number + ": " + name);
        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
        }
    }

    @Override public int getItemCount() {
        return mDataList.size() + (isProgressVisible ? 1 : 0);
    }

    @Override public int getItemViewType(int position) {
        if (position >= mDataList.size()) {
            return ROW_PROGRES;
        }
        return ROW_ITEM;
    }

    public void showProgress() {
        this.isProgressVisible = true;

        mMainHandler.post(new Runnable() {
            @Override public void run() {
                //notifyDataSetChanged();
                notifyItemInserted(getItemCount());
            }
        });
    }

    public void hideProgress() {
        this.isProgressVisible = false;
        mMainHandler.post(new Runnable() {
            @Override public void run() {
                notifyItemRemoved(getItemCount());
            }
        });
    }

    public void addItems(List<Job> jobList){
        this.isProgressVisible = false;
        mDataList.addAll(jobList);
        mMainHandler.post(new Runnable() {
            @Override public void run() {
                notifyDataSetChanged();
            }
        });

    }

    public boolean isProgressVisible() {
        return this.isProgressVisible;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTviName;

        public ViewHolder(View itemView) {
            super(itemView);
            mTviName = (TextView) itemView.findViewById(R.id.tviName);
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar mPbaLoading;
        public ProgressViewHolder(View itemView) {
            super(itemView);
            mPbaLoading = (ProgressBar) itemView.findViewById(R.id.pbaLoading);
        }
    }
}
