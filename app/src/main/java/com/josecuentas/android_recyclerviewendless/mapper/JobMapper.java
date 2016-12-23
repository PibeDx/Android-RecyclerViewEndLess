package com.josecuentas.android_recyclerviewendless.mapper;

import com.josecuentas.android_recyclerviewendless.model.Job;
import com.josecuentas.android_recyclerviewendless.rest.entity.JobEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcuentas on 23/12/16.
 */

public class JobMapper {
    public Job transform(JobEntity jobEntity) {
        Job job = new Job();
        if (jobEntity == null) return job;
        job.setName(jobEntity.name);
        return job;
    }

    public List<Job> transformList(List<JobEntity> jobEntities) {
        List<Job> jobList = new ArrayList<>();
        if (jobEntities == null) return jobList;
        for (JobEntity jobEntity : jobEntities) {
            jobList.add(transform(jobEntity));
        }
        return jobList;
    }
}
