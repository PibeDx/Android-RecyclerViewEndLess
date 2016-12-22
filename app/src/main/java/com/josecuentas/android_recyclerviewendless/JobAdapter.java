package com.josecuentas.android_recyclerviewendless;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jcuentas on 21/12/16.
 */

public class JobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ROW_ITEM = 0;
    private static final int ROW_PROGRES = 1;

    private List<Job> mDataList;

    public JobAdapter(List<Job> dataList) {
        mDataList = dataList;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == ROW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job,parent, false);
            vh = new ViewHolder(view);
            return vh;
        } else { //ROW_PROGRES
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_progress,parent, false);
            vh = new ViewHolder(view);
            return vh;
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            final Job job = mDataList.get(position);
            String name = job.getName();
            viewHolder.mTviName.setText(name);
        }
    }

    @Override public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTviName;

        public ViewHolder(View itemView) {
            super(itemView);
            mTviName = (TextView) itemView.findViewById(R.id.tviName);
        }
    }
}
