package com.uphouse.monew.infrastructure.batch.job;

import com.uphouse.monew.domain.article.domain.Article;
import com.uphouse.monew.domain.article.dto.RssArticle;
import com.uphouse.monew.infrastructure.batch.processor.ChosunRssProcessor;
import com.uphouse.monew.infrastructure.batch.processor.HankyungRssProcessor;
import com.uphouse.monew.infrastructure.batch.reader.ChosunRssReader;
import com.uphouse.monew.infrastructure.batch.reader.HankyungRssReader;
import com.uphouse.monew.infrastructure.batch.writer.RssItemWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RssArticleJobConfig {

    private final HankyungRssReader rssItemReader;
    private final HankyungRssProcessor rssItemProcessor;

    private final ChosunRssReader chosunItemReader;
    private final ChosunRssProcessor chosunItemProcessor;

    private final RssItemWriter rssItemWriter;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * RSS 기사 수집 Job을 정의합니다.
     */
    @Bean
    public Job fetchHankyungArticlesJob() {
        return new JobBuilder("fetchRssArticlesJob", jobRepository)
                .start(fetchHankyungArticlesStep()) // Job이 실행될 때 시작할 Step을 지정합니다.
                .build();
    }

    /**
     * RSS 기사 수집 Step을 정의합니다.
     * Step은 Reader, Processor, Writer를 포함하는 실질적인 작업 단위입니다.
     */
    @Bean
    public Step fetchHankyungArticlesStep() {
        return new StepBuilder("fetchRssArticlesStep", jobRepository)
                // <Input, Output> 타입을 지정. Reader의 반환 타입이자 Processor의 입력 타입, Processor의 반환 타입이자 Writer의 입력 타입
                .<RssArticle, Article>chunk(10, platformTransactionManager) // 한 번에 처리할 데이터 양(Chunk)을 10으로 설정합니다.
                .reader(rssItemReader)
                .processor(rssItemProcessor)
                .writer(rssItemWriter)
                .build();
    }

    @Bean
    public Job fetchChosunArticlesJob() {
        return new JobBuilder("fetchChosunArticlesJob", jobRepository)
                .start(fetchChosunRssArticlesStep())
                .build();
    }

    @Bean
    public Step fetchChosunRssArticlesStep() {
        return new StepBuilder("fetchChosunRssArticlesStep", jobRepository)
                .<RssArticle, Article>chunk(10, platformTransactionManager)
                .reader(chosunItemReader)
                .processor(chosunItemProcessor)
                .writer(rssItemWriter)
                .build();
    }
}
