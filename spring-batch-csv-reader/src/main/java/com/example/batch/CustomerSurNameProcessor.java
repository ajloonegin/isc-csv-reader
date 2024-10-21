package com.example.batch;

import com.example.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CustomerSurNameProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        log.info("Processing surName for {}", item);
        if (item.getSurName() == null || item.getSurName().isEmpty()) {
            throw new ValidationException("Invalid SurName");
        }
        return item;

    }
}
