package com.hhovhann.distributedworker.repository;

import com.hhovhann.distributedworker.entiry.Job;
import com.hhovhann.distributedworker.entiry.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @BeforeEach
    public void init() {
        log.info("Initialization of all job entities.");
        jobRepository.saveAll(List.of(
                Job.builder().id(1L).url("https://proxify.io").status(Status.DONE).httpCode(200).build(),
                Job.builder().id(2L).url("https://reddit.com").status(Status.NEW).httpCode(200).build(),
                Job.builder().id(3L).url("https://start.spring.io").status(Status.NEW).httpCode(200).build(),
                Job.builder().id(4L).url("https://proxify.io").status(Status.NEW).httpCode(200).build(),
                Job.builder().id(5L).url("https://wrong.io").status(Status.ERROR).httpCode(200).build()
        ));
    }

    @AfterEach
    public void destroy() {
        log.info("Clean Up database before each test method will be executed.");
        jobRepository.deleteAll();
    }

    @Test
    @DisplayName("Find all jobs")
    void when_findAll_then_should_return_all_jobs() {
        List<Job> allJobs = jobRepository.findAll();
        Assertions.assertTrue(allJobs.size() > 0);
    }

    @Test
    @DisplayName("Update job status by existing job id")
    void when_updateStatusByJobId_then_should_update_the_job() {
        // when
        int modifiedStatus = jobRepository.updateStatusByJobId(Status.PROCESSING, 1L);
        Job updatedJob = jobRepository.findById(1L).orElseThrow();

        // then
        Assertions.assertEquals(modifiedStatus, 1);
        Assertions.assertEquals(updatedJob.getStatus(), Status.PROCESSING);
    }

    @Test
    @DisplayName("Update job status and httpCode by existing job id")
    void when_updateStatusAndHttpCodeByJobId_then_should_update_the_job() {
        // when
        int modifiedStatus = jobRepository.updateStatusAndHttpCodeByJobId(Status.DONE, 200, 2L);
        Job updatedJob = jobRepository.findById(2L).orElseThrow();

        // then
        Assertions.assertEquals(modifiedStatus, 1);
        Assertions.assertEquals(updatedJob.getStatus(), Status.DONE);
        Assertions.assertEquals(updatedJob.getHttpCode(), 200);
    }
}