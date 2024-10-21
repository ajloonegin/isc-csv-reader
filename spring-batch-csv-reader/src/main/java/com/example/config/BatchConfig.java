package com.example.config;

import com.example.batch.*;
import com.example.entity.BookEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class BatchConfig {

    @Bean
    public Job customerReaderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("customerReadJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(taskletStep(jobRepository, transactionManager))
                .build();
    }
    @Bean
    public Job accountReaderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("accountReadJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(taskletStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step chunkCustomerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("customerReaderStep", jobRepository).<Customer, Customer>
                        chunk(10, transactionManager)
                .reader(customerReader())
//                .reader(restCustomerReader())
                .processor(customerProcessor())
                .writer(customerWriter())
                .build();

    }

    @Bean
    public Step chunkAccountStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("accountReaderStep", jobRepository).<Account, Account>
                        chunk(10, transactionManager)
                .reader(accountReader())
//                .reader(restAccountReader())
                .processor(accountProcessor())
                .writer(accountWriter())
                .build();

    }


    @Bean
    @StepScope
    public ItemReader<Customer> restCustomerReader() {
        return new RestCustomerReader("http://localhost:8080/customer", new RestTemplate());
    }

    @Bean
    @StepScope
    public ItemReader<Account> restAccountReader() {
        return new restAccountReader("http://localhost:8080/account", new RestTemplate());
    }

    @StepScope
    @Bean
    public ItemWriter<Account> accountWriter() {
        return new AccountWriter();
    }
    @StepScope
    @Bean
    public ItemWriter<Customer> customerWriter() {
        return new CustomerWriter();
    }


    @StepScope
    @Bean
    public ItemProcessor<Customer, Customer> customerProcessor() {
        CompositeItemProcessor<Customer, Customer> customerProcessor = new CompositeItemProcessor<>();
        customerProcessor.setDelegates(List.of(new CustomerNameProcessor(), new CustomerSurNameProcessor()));
        return customerProcessor;
    }

    @StepScope
    @Bean
    public ItemProcessor<Account, Account> accountProcessor() {
        CompositeItemProcessor<Account, Account> accountProcessor = new CompositeItemProcessor<>();
        accountProcessor.setDelegates(List.of(new AccountBalanceProcessor(), new AccountTypeProcessor()));
        return accountProcessor;
    }

    @Bean
    @StepScope
    public FlatFileItemCustomerReader<Customer> customerReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerReader")
                .resource(new ClassPathResource("customers.csv"))
                .delimited()
                .names(new String[]{"name", "surName", "address","zipCode", "nationalId", "birthDate"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Customer.class);
                }})
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemAccountReader<Account> accountReader() {
        return new FlatFileItemReaderBuilder<Account>()
                .name("accountReader")
                .resource(new ClassPathResource("accounts.csv"))
                .delimited()
                .names(new String[]{"accountNumber", "type", "customerId","limit", "openDate", "balance"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Account.class);
                }})
                .build();
    }
}
