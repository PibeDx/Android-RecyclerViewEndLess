package com.josecuentas.android_recyclerviewendless.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.josecuentas.android_recyclerviewendless.EndlessRecyclerViewAdapter;
import com.josecuentas.android_recyclerviewendless.R;
import com.josecuentas.android_recyclerviewendless.model.Job;

import java.util.List;

/**
 * Created by jcuentas on 27/12/16.
 */

public class JobEndLessAdapter extends EndlessRecyclerViewAdapter<Job> {
    public JobEndLessAdapter(List<Job> data) {
        super(data);
    }

    @Override public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job, parent, false);
        return new DataViewHolder(view);
    }

    @Override public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case TYPE_DATA:
                DataViewHolder dataHolder = (DataViewHolder) holder;
                Job job = data.get(position);
                int number = position + 1;
                String name = job.getName();
                String message = new StringBuilder().append(number).append(" " + name).toString();
                dataHolder.mTviName.setText(message);
                break;
        }
    }

    static class DataViewHolder extends RecyclerView.ViewHolder{
        TextView mTviName;

        public DataViewHolder(View itemView) {
            super(itemView);
            mTviName = (TextView) itemView.findViewById(R.id.tviName);
        }
    }

}
