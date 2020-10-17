package com.hhovhann.distributedworker.template;

import com.hhovhann.distributedworker.entiry.Job;
import com.hhovhann.distributedworker.entiry.Status;
import com.hhovhann.distributedworker.model.JobModel;
import com.hhovhann.distributedworker.repository.JobRepository;
import com.hhovhann.distributedworker.service.worker.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class JobTemplateImpl implements JobTemplate {
    @Value("${worker.threads.quantity}")
    private int threadsQuantity;

    private final JobRepository jobRepository;

    public JobTemplateImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void beforeProcessJob(Job current) {
        jobRepository.updateStatusByJobId(Status.PROCESSING, current.getId());
    }

    @Override
    public void afterProcessJob(JobModel model) {
        jobRepository.updateStatusAndHttpCodeByJobId(model.getStatus(), model.getStatusCode(), model.getId());
    }

    @Override
    public void processJobWorkflow(List<Job> entities) {
        ExecutorService executor = Executors.newFixedThreadPool(threadsQuantity);
        entities.forEach((var current) -> {
            if (Objects.equals(current.getStatus(), Status.NEW)) {
                try {
                    beforeProcessJob(current);
                    var statusCode = executor.submit(new Worker(current.getUrl())).get();
                    var status = statusCode == HttpStatus.OK.value() ? Status.DONE : Status.ERROR;
                    afterProcessJob(new JobModel(current.getId(), status, statusCode));
                } catch (InterruptedException | ExecutionException ex) {
                    log.error("", ex);
                }
            }
        });
        executor.shutdown();
    }
}
