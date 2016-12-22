package com.josecuentas.android_recyclerviewendless;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRviContainer;
    private List<Job> mJobList = new ArrayList<>();
    private JobAdapter mJobAdapter;
    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;
    private int mPages = 3;
    private int mCurrentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectView();
        setupRecycler();
        mookJobList();
        injectAdapters();
    }

    private void injectView() {
        mRviContainer = (RecyclerView) findViewById(R.id.rviContainer);
    }

    private void setupRecycler() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRviContainer.setLayoutManager(llm);
        mRviContainer.setHasFixedSize(true);
        mEndlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override public void onLoadMore(int currentPage) {
                Log.d(TAG, "onLoadMore() called with: currentPage = [" + currentPage + "]");
                loadMore(currentPage);
            }
        };
        mRviContainer.setOnScrollListener(mEndlessRecyclerOnScrollListener);
    }

    private void loadMore(final int currentPage) {
        new Handler().post(new Runnable() {
            @Override public void run() {
                if(mPages >= currentPage) {
                    mookJobList();
                    mJobAdapter.notifyDataSetChanged();
                    mCurrentPage++;
                } else {
                    mJobAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void injectAdapters() {
        mJobAdapter = new JobAdapter(mJobList);
        mRviContainer.setAdapter(mJobAdapter);
    }

    private void mookJobList() {
        mJobList.add(new Job("Juan"));
        mJobList.add(new Job("José"));
        mJobList.add(new Job("Angel"));
        mJobList.add(new Job("Carlos"));
        mJobList.add(new Job("Franz"));
        mJobList.add(new Job("Diseñador"));
        mJobList.add(new Job("Frontend"));
        mJobList.add(new Job("Backend"));
        mJobList.add(new Job("AndroidDeveloper"));
        mJobList.add(new Job("IosDeveloper"));
    }
}
