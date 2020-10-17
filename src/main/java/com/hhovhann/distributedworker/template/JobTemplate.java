package com.hhovhann.distributedworker.template;

import com.hhovhann.distributedworker.entiry.Job;
import com.hhovhann.distributedworker.model.JobModel;

import java.util.List;

public interface JobTemplate {

    /***
     * Before persist business logic can be here
     */
    void beforeProcessJob(Job current);

    /***
     * Update current job, when job status is NEW
     */
    void processJobWorkflow(List<Job> entities);

    /***
     * After persist business logic can be here
     */
    void afterProcessJob(JobModel model);
}
