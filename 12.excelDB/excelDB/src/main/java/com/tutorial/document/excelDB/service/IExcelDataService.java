package com.tutorial.document.excelDB.service;

import com.tutorial.document.excelDB.entity.Invoice;

import java.util.List;

public interface IExcelDataService {

    List<Invoice> getExcelDataAsList();

    int saveExcelData(List<Invoice> invoices);
}
