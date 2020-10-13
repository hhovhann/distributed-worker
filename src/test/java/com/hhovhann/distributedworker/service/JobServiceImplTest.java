package com.hhovhann.distributedworker.service;

import com.hhovhann.distributedworker.entiry.Job;
import com.hhovhann.distributedworker.entiry.Status;
import com.hhovhann.distributedworker.repository.JobRepository;
import com.hhovhann.distributedworker.service.job.JobService;
import com.hhovhann.distributedworker.service.worker.WorkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class JobServiceImplTest {

    @Autowired
    private JobService jobService;

    @MockBean
    private JobRepository jobRepository;

    @Test
    @DisplayName("Execution of distributed worker will execute all url's and update status codes")
    public void when_execute_called_then_test_should_passed() {
        List<Job> jobList = List.of(
                Job.builder().id(1L).url("https://proxify.io").status(Status.DONE).httpCode(200).build(),
                Job.builder().id(2L).url("https://reddit.com").status(Status.NEW).httpCode(200).build()
        );
        jobService.execute(jobList);
    }
}