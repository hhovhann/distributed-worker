package com.hhovhann.distributedworker.service.job;

import com.hhovhann.distributedworker.entiry.Job;
import com.hhovhann.distributedworker.template.JobTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JobServiceImpl implements JobService {
    private final JobTemplate jobTemplate;

    public JobServiceImpl(JobTemplate jobTemplate) {
        this.jobTemplate = jobTemplate;
    }

    @Override
    public void execute(List<Job> entities) {
        log.info("Job Service execution started.");
        jobTemplate.processJobWorkflow(entities);
        log.info("Job Service execution finished.");
    }
}
