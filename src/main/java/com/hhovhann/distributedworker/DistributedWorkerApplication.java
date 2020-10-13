package com.hhovhann.distributedworker;

import com.hhovhann.distributedworker.service.worker.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class DistributedWorkerApplication implements CommandLineRunner {
    @Autowired
    private WorkerService workerService;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DistributedWorkerApplication.class, args);
        ctx.close();
    }

    @Override
    public void run(String... args) {
        log.info("Start Worker processing with argument");
        workerService.process();
        log.info("Finished Worker processing successfully finished:");
    }
}
