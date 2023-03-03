package com.tutorial.document.excelDB.repository;

import com.tutorial.document.excelDB.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
