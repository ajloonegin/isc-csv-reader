package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "account_table")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.Auto)
    @Column(name="ACCOUNT_ID")
    private Long id;


    @IbanCode
    @Column(name="ACCOUNT_NUMBER", nullable = false, unique = false)
    private String accountNumber;


    @Enumerated(EnumType.STRING)
    @Column(name="ACCOUNT_TYPE", nullable = false, unique = false)
    private Type type;

    @Column(name="ACCOUNT_CUSTOMER_ID", nullable = false, unique = true)
    private Long customerId;

    @Column(name="ACCOUNT_LIMIT", nullable = false, unique = false)
    private Long limit;


    @Temporal(TemporalType.DATE)
    @Column(name="ACCOUNT_OPEN_DATE", nullable = false, unique = false)
    private Date openDate;

    @Min(10000)
    @Column(name="ACCOUNT_BALANCE", nullable = false, unique = false)
    private Long balance;

}
