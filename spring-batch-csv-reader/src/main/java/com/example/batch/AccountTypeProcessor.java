package com.example.batch;

import com.example.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class AccountTypeProcessor implements ItemProcessor<Account, Account> {
    @Override
    public Account process(Account item) throws Exception {
        log.info("Processing type for {}", item);
        if (item.getType() == null || item.getType().isEmpty()) {
            throw new ValidationException("Invalid Type");
        }
        return item;
       
    }
}
