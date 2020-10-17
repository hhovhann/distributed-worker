package com.hhovhann.distributedworker.model;

import com.hhovhann.distributedworker.entiry.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JobModel {
    long id;
    Status status;
    int statusCode;
}
