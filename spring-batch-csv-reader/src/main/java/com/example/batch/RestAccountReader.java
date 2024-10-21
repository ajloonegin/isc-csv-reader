package com.example.batch;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class RestAccountReader implements ItemReader<Account> {

    private final String url;
    private final RestTemplate restTemplate;
    private int nextAccount;
    private List<Account> accountList;

    public RestAccountReader(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }


    @Override
    public Account read() throws Exception {
        if (this.accountList == null) {
            accountList = fetchAccounts();
        }
        Account account = null;

        if (nextAccount < accountList.size()) {
            account = accountList.get(nextAccount);
            nextAccount++;
        } else {
            nextAccount = 0;
            accountList = null;
        }
        return account;
    }

    private List<Account> fetchAccounts() {
        ResponseEntity<Account[]> response = restTemplate.getForEntity(this.url, Account[].class);
        Account[] accounts = response.getBody();
        if (accounts != null) {
            return Arrays.asList(accounts);
        }
        return null;
    }
}
