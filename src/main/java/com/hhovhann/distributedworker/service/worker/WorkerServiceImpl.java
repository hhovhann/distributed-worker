package com.hhovhann.distributedworker.service.worker;

import com.hhovhann.distributedworker.entiry.Job;
import com.hhovhann.distributedworker.entiry.Status;
import com.hhovhann.distributedworker.repository.JobRepository;
import com.hhovhann.distributedworker.service.job.JobService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {
    @Value("${worker.in.memory.data.enabled}")
    private boolean inMemoryEnabled;

    @Value("${worker.in.memory.data.urls}")
    private String[] urls;

    private final JobRepository jobRepository;
    private final JobService jobService;

    public WorkerServiceImpl(JobRepository jobRepository, JobService jobService) {
        this.jobRepository = jobRepository;
        this.jobService = jobService;
    }

    @Override
    public void process() {
        // The application seeds some data into database (2 row data what we have), because will use h2 in memory database
        if (inMemoryEnabled) {
            jobRepository.saveAll(List.of(Job.builder().id(1L).url(urls[0]).status(Status.DONE).httpCode(200).build(), Job.builder().id(2L).url(urls[1]).status(Status.NEW).httpCode(200).build(), Job.builder().id(3L).url(urls[2]).status(Status.NEW).httpCode(200).build(), Job.builder().id(4L).url(urls[3]).status(Status.NEW).httpCode(200).build(), Job.builder().id(5L).url(urls[4]).status(Status.ERROR).httpCode(200).build()));
        }
        // The application find all urls from database
        List<Job> entities = jobRepository.findAll();
        // The application grabs performs HTTP requests and updates HTTP response code accordingly
        jobService.execute(entities);
    }
}
