package com.batchprocess.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Before Job: " + stepExecution.getStepName());
        System.out.println("Start time: " + stepExecution.getStartTime());
        System.out.println("Step Execution Context: " + stepExecution.getExecutionContext());

        stepExecution.getExecutionContext().put("sec", "sec value");
    }

    @Override
    @Nullable
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("Exit Status: " + stepExecution.getExitStatus());
        System.out.println("Step Execution Context: " + stepExecution.getExecutionContext());
        return null;
    }
}
