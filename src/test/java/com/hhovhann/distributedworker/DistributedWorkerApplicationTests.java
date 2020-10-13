package com.hhovhann.distributedworker;

import com.hhovhann.distributedworker.service.job.JobService;
import com.hhovhann.distributedworker.service.worker.WorkerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DistributedWorkerApplicationTests {

    @Autowired
    private JobService jobService;
    @Autowired
    private WorkerService workerService;

    @Test
    @DisplayName("Smoke tests")
    void contextLoads() {
        assertThat(jobService).isNotNull();
        assertThat(workerService).isNotNull();
    }
}
