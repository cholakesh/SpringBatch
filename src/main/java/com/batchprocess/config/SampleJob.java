package com.batchprocess.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batchprocess.listener.FirstJobListener;
import com.batchprocess.listener.FirstStepListener;
import com.batchprocess.processor.FirstItemProcessor;
import com.batchprocess.reader.FirstItemReader;
import com.batchprocess.service.SecondTasklet;
import com.batchprocess.writer.FirstItemWriter;

@Configuration
public class SampleJob {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    SecondTasklet secondTasklet;

    @Autowired
    FirstJobListener firstJobListener;

    @Autowired
    FirstStepListener firstStepListener;

    @Autowired
    FirstItemReader firstItemReader;

    @Autowired
    FirstItemProcessor firstItemProcessor;

    @Autowired
    FirstItemWriter firstItemWriter;

    @Bean
    public Job firstJob() {
        return jobBuilderFactory.get("First Job")
                .incrementer(new RunIdIncrementer()) // this is for generating a unique parameter So that the job can be
                                                     // executed recursively.
                .start(firstStep())
                .next(secondStep())
                .listener(firstJobListener)
                .build();
    }

    private Step firstStep() {
        return stepBuilderFactory.get("First Step")
                .tasklet(firstTask())
                .listener(firstStepListener)
                .build();
    }

    private Tasklet firstTask() {
        return new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
                System.out.println("This is my first step");
                System.out.println("Step Execution context: " + chunkContext.getStepContext());
                return RepeatStatus.FINISHED;
            }
        };
    }

    private Step secondStep() {
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTasklet)
                .build();
    }

    // private Tasklet secondTask() {
    // return new Tasklet() {

    // @Override
    // public RepeatStatus execute(StepContribution stepContribution, ChunkContext
    // chunkContext) {
    // System.out.println("This is my second step");
    // return RepeatStatus.FINISHED;
    // }
    // };
    // }

    /**********************************************************************************/
    // Chunk oriented

    @Bean
    public Job secondJob() {
        return jobBuilderFactory.get("Second Job")
                .incrementer(new RunIdIncrementer()) // this is for generating a unique parameter So that the job can be
                                                     // executed recursively.
                .start(firstChunkStep())
                .build();
    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("First Chunk step")
                .<Integer, Long>chunk(3)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

}
