package com.hhovhann.distributedworker.repository;

import com.hhovhann.distributedworker.entiry.Job;
import com.hhovhann.distributedworker.entiry.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Transactional
    @Modifying
    @Query("update Job job set job.status = :status where job.id = :jobId")
    int updateStatusByJobId(@Param("status") Status status, @Param("jobId") Long jobId);

    @Transactional
    @Modifying
    @Query("update Job job set job.status = :status, job.httpCode = :httpCode where job.id = :jobId")
    int updateStatusAndHttpCodeByJobId(@Param("status") Status status, @Param("httpCode") int httpCode, @Param("jobId") Long jobId);

}
