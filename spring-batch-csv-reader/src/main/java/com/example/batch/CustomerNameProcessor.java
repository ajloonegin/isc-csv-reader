package com.example.batch;

import com.example.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CustomerNameProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        log.info("Processing name for {}", item);
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new ValidationException("Invalid Name");
        }
        return item;

    }
}
