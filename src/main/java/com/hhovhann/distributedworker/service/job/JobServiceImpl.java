package com.hhovhann.distributedworker.service.job;

import com.hhovhann.distributedworker.entiry.Job;
import com.hhovhann.distributedworker.entiry.Status;
import com.hhovhann.distributedworker.repository.JobRepository;
import com.hhovhann.distributedworker.service.worker.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class JobServiceImpl implements JobService {
    @Value("${worker.threads.quantity}")
    private int threadsQuantity;

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void execute(List<Job> entities) {
        log.info("Job Service execution started.");
        doExecute(entities);
        log.info("Job Service execution finished.");
    }

    private void doExecute(List<Job> entities) {
        ExecutorService executor = Executors.newFixedThreadPool(threadsQuantity);
        entities.forEach((var current) -> {
            try {
                // update job status into processing, before job running
                jobRepository.updateStatusByJobId(Status.PROCESSING, current.getId());
                var statusCode = executor.submit(new Worker(current.getUrl())).get();
                // update the job status and status code after job finished
                var status = statusCode == HttpStatus.OK.value() ? Status.DONE : Status.ERROR;
                jobRepository.updateStatusAndHttpCodeByJobId(status, statusCode, current.getId());
            } catch (InterruptedException | ExecutionException ex) {
                log.error("", ex);
            }
        });
        executor.shutdown();
    }
}
