package com.hhovhann.distributedworker.service.job;

import com.hhovhann.distributedworker.entiry.Job;

import java.util.List;

public interface JobService {
    /***
     * Execute job workflow for all job entities iteratively
     */
    void execute(List<Job> jobEntities);
}
