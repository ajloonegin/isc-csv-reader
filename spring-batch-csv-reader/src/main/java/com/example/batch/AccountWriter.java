package com.example.batch;

import com.example.entity.BookEntity;
import com.example.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AccountWriter implements ItemWriter<Account> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void write(Chunk<? extends Account> chunk) throws Exception {
        log.info("Writing: {}", chunk.getItems().size());
        accountRepository.saveAll(chunk.getItems());
    }
}
