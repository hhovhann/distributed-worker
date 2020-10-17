package com.hhovhann.distributedworker.service;

import com.hhovhann.distributedworker.entiry.Job;
import com.hhovhann.distributedworker.entiry.Status;
import com.hhovhann.distributedworker.repository.JobRepository;
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
class WorkerServiceImplTest {

    @Value("${worker.in.memory.data.enabled}")
    private boolean inMemoryEnabled;
    @Value("${worker.in.memory.data.urls}")
    private String[] urls;

    @BeforeEach
    public void setUp() {
        List<Job> jobList = List.of(
                Job.builder().id(1L).url(urls[0]).status(Status.DONE).httpCode(200).build(),
                Job.builder().id(2L).url(urls[1]).status(Status.NEW).httpCode(200).build()
        );
        Mockito.when(jobRepository.saveAll(jobList)).thenReturn(jobList);
        Mockito.when(jobRepository.findAll()).thenReturn(jobList);
    }

    @Autowired
    private WorkerService workerService;

    @MockBean
    private JobRepository jobRepository;

    @Test
    @DisplayName("Process worker with data from in memory data table")
    public void when_process_called_from_in_memory_then_should_test_passed() {
        workerService.process();
    }
}