package com.batchprocess.controller;

import java.util.List;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batchprocess.model.JobRequestParams;
import com.batchprocess.service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    JobService jobService;

    @Autowired
    JobOperator jobOperator;

    @GetMapping("/start/{jobName}")
    public ResponseEntity<String> startJob(@PathVariable String jobName,
            @RequestBody List<JobRequestParams> jobRequestParamsList)
            throws Exception {
        jobService.startJob(jobName, jobRequestParamsList);
        return new ResponseEntity<String>("Job started....", HttpStatus.OK);
    }

    @GetMapping("/stop/{jobExecutionId}")
    public String stopJob(@PathVariable Long jobExecutionId) throws Exception {
        jobOperator.stop(jobExecutionId);
        return "Job stopped......!";
    }
}
