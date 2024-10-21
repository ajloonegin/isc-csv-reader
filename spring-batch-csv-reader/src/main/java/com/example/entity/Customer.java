package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customer_table")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.Auto)
    @Column(name="CUSTOMER_ID")
    private Long id;

    @Column(name="CUSTOMER_NAME", nullable = false, unique = false)
    private String name;

    @Column(name="CUSTOMER_SURNAME", nullable = false, unique = false)
    private String surName;

    @Column(name="CUSTOMER_ADDRESS", nullable = false, unique = false)
    private String address;

    @Column(name="CUSTOMER_ZIP_CODE", nullable = false, unique = false)
    @Pattern(regexp = "^[0-9]{8}$")
    private Long zipCode;

    @IranianNationalCode
    @Column(name="CUSTOMER_NATIONAL_ID", nullable = false, unique = false)
    private String nationalId;

    @Column(name="CUSTOMER_BIRTH_DATE", nullable = false, unique = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;


}
