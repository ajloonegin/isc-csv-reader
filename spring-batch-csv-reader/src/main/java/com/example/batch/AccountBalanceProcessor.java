package com.example.batch;

import com.example.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class AccountBalanceProcessor implements ItemProcessor<Account, Account> {
    @Override
    public Account process(Account item) throws Exception {
        log.info("Processing balance for {}", item);
        if (item.getBalance() == null || item.getBalance().isEmpty()) {
            throw new ValidationException("Invalid Balance");
        }
        return item;
       
    }
}
