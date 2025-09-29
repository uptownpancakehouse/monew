package com.uphouse.monew.infrastructure.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsBatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job fetchHankyungArticlesJob;
    private final Job fetchChosunArticlesJob;
    private final Job fetchYonhapArticlesJob;

    @Scheduled(cron = "0 0 * * * ?", zone = "Asia/Seoul") // 한시간마다 뉴스 정보 가져오기
    public void runAllJobs() {
        runJob(fetchHankyungArticlesJob, "Hankyung");
        runJob(fetchChosunArticlesJob, "Chosun");
        runJob(fetchYonhapArticlesJob, "Yonhap");
    }

    private void runJob(Job job, String name) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("requestDate", String.valueOf(System.currentTimeMillis()))
                    .addString("jobName", name) // 중복 방지
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
            log.info("{} batch job 이 성공적으로 실행되었습니다.", name);

        } catch (Exception e) {
            log.error("{} batch job 실패", name, e);
        }
    }
}
