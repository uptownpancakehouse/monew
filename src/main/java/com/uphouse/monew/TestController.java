package com.uphouse.monew;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final JobLauncher jobLauncher;
    private final Job fetchRssArticlesJob; // RssArticleBatchConfig에 정의된 Job Bean을 주입받습니다.

    @GetMapping("/force-fetch")
    public String forceFetchNews() {
        try {
            // Job을 실행하기 위한 파라미터를 생성합니다.
            // 매번 다른 파라미터를 주어야 새로운 Job 인스턴스가 생성되어 실행됩니다.
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("requestDate", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();

            // jobLauncher를 사용하여 Job을 실행합니다.
            jobLauncher.run(fetchRssArticlesJob, jobParameters);

            return "OK - Batch job has been requested.";

        } catch (Exception e) {
            // Job 실행 중 발생할 수 있는 예외를 처리합니다.
            return "Error - Failed to execute batch job: " + e.getMessage();
        }
    }
}
