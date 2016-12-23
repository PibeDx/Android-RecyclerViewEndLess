package com.josecuentas.android_recyclerviewendless.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.josecuentas.android_recyclerviewendless.EndlessRecyclerOnScrollListener;
import com.josecuentas.android_recyclerviewendless.model.Job;
import com.josecuentas.android_recyclerviewendless.ui.adapter.JobAdapter;
import com.josecuentas.android_recyclerviewendless.R;
import com.josecuentas.android_recyclerviewendless.mapper.JobMapper;
import com.josecuentas.android_recyclerviewendless.rest.ApiClient;
import com.josecuentas.android_recyclerviewendless.rest.response.BaseResponse;
import com.josecuentas.android_recyclerviewendless.rest.entity.JobEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final int INIT_PAGINATION = 1;
    private RecyclerView mRviContainer;
    private List<Job> mJobList = new ArrayList<>();
    private JobAdapter mJobAdapter;
    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;
    private int mPages = 5;
    private int mCurrentPage = 1;
    private JobMapper mMapper = new JobMapper();
    private int mPageSize = 5;
    private int mOffset = 0;

    /*Cambiar dependiendo de tipo de prueba*/
    private final boolean isSimulation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectView();
        setupRecycler();
        loadData();
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
                loadDataEndLess(currentPage);
            }
        };
        mRviContainer.setOnScrollListener(mEndlessRecyclerOnScrollListener);
    }

    /*
    * Simulamos la llamada a un servicio
    * */
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

    private void injectRetrofit(int page) {
        if (page == INIT_PAGINATION) {
            mOffset = 0;
        } else {
            mOffset = mOffset + mPageSize;
        }
        Call<BaseResponse<JobEntity>> call = ApiClient.getACInterface().getAllJobByPage(mPageSize, mOffset);
        call.enqueue(new Callback<BaseResponse<JobEntity>>() {
            @Override public void onResponse(Call<BaseResponse<JobEntity>> call, Response<BaseResponse<JobEntity>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<JobEntity> body = response.body();
                    List<Job> jobList = mMapper.transformList(body.data);
                    mJobList.addAll(jobList);
                    mJobAdapter.notifyDataSetChanged();
                }
            }

            @Override public void onFailure(Call<BaseResponse<JobEntity>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadData() {
        if (isSimulation) {
            mookJobList();
        } else {
            injectRetrofit(INIT_PAGINATION);
        }
    }

    private void loadDataEndLess(int currentPage) {
        if (isSimulation) {
            loadMore(currentPage);
        } else {
            injectRetrofit(currentPage);
        }
    }

}
