package com.jsrdev.screenmatch.batch.config;

import com.jsrdev.screenmatch.batch.model.UserCsv;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class CsvBatchConfig {

    /**
     * =========================
     * ITEM READER
     * =========================
     * Lee el archivo CSV línea por línea.
     * NO carga todo en memoria.
     */
    @Bean
    public FlatFileItemReader<UserCsv> reader() {

        return new FlatFileItemReaderBuilder<UserCsv>()
                .name("userCsvReader")
                .resource(new FileSystemResource("input/users.csv"))
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("name", "email")
                .fieldSetMapper(fieldSet -> new UserCsv(
                        fieldSet.readString("name"),
                        fieldSet.readString("email")
                ))
                .build();
    }


    /**
     * =========================
     * ITEM PROCESSOR (OPCIONAL)
     * =========================
     * Aquí puedes validar o transformar datos.
     * Si no necesitas lógica, se deja neutro.
     */
    @Bean
    public ItemProcessor<UserCsv, UserCsv> processor() {
        return item -> item; // No hace nada, pasa tal cual
    }

    /**
     * =========================
     * ITEM WRITER
     * =========================
     * Inserta datos en la BD en batch.
     * NO usa JPA.
     */
    @Bean
    public JdbcBatchItemWriter<UserCsv> writer(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<UserCsv>()
                .dataSource(dataSource)
                .sql("""
                            INSERT INTO users (name, email)
                            VALUES (:name, :email)
                        """)
                .beanMapped()
                .build();
    }

    /**
     * =========================
     * STEP
     * =========================
     * Define el flujo Reader → Processor → Writer
     * chunk(1000) = 1000 registros por transacción
     */
    @Bean
    public Step csvStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            DataSource dataSource
    ) {
        return new StepBuilder("csvStep", jobRepository)
                .<UserCsv, UserCsv>chunk(1000)
                .reader(reader())
                .processor(processor())
                .writer(writer(dataSource))
                .transactionManager(transactionManager)
                .build();
    }

    /**
     * =========================
     * JOB
     * =========================
     * El trabajo completo.
     * Puede tener uno o varios steps.
     */
    @Bean
    public Job csvJob(
            JobRepository jobRepository,
            Step csvStep
    ) {
        return new JobBuilder("csvJob", jobRepository)
                .start(csvStep)
                .build();
    }
}
