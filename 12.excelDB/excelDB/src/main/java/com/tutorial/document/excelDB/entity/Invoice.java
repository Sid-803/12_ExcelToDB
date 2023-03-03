package com.tutorial.document.excelDB.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="invoice_id")
    private Long id;

    @Column(name="invoice_name")
    private String name;

    @Column(name="invoice_amount")
    private Double amount;

    @Column(name="invoice_number")
    private String number;

    @Column(name="invoice_recievedDate")
    private String recievedDate;

}
