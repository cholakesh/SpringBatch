package com.batchprocess.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.batchprocess.model.JobRequestParams;

@Service
public class JobService {

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("firstJob")
    @Autowired
    Job firstJob;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

    @Async
    public void startJob(String jobName, List<JobRequestParams> jobRequestParamsList) {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));

        jobRequestParamsList.stream().forEach(jobReqParams -> {
            params.put(jobReqParams.getParamsKey(), new JobParameter(jobReqParams.getParamsVal()));
        });

        JobParameters jobParameters = new JobParameters(params);
        JobExecution jobExecution = null;
        try {
            if (jobName.equals("First Job")) {
                jobExecution = jobLauncher.run(firstJob, jobParameters);
            } else {
                jobExecution = jobLauncher.run(secondJob, jobParameters);
            }
            System.out.println("JobExecution Id: " + jobExecution.getId());
        } catch (Exception e) {
            System.out.println("Exeception occured while starting job");
        }
    }
}
