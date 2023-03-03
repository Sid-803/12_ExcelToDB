package com.tutorial.document.excelDB.serviceImpl;

import com.tutorial.document.excelDB.entity.Invoice;
import com.tutorial.document.excelDB.repository.InvoiceRepository;
import com.tutorial.document.excelDB.service.IExcelDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
* Accepts the path for excel file from classpath:application.properties.
* Then, method1: creates an object to store excel sheet and populate the data from excel cells into list of string.
* Method2: populates string data as invoice into list of invoices.
* Method3: repository object to save all the invoices
*/
@Slf4j
@Service
public class ExcelDataServiceImpl implements IExcelDataService {

    @Value("${app.upload.file:${user.home}}")
    public String excelFilePath;

    @Autowired
    private InvoiceRepository invoiceRepository;

    private Workbook workbook;

    public List<Invoice> getExcelDataAsList(){

        List<String> list = new ArrayList<String>();

        //create DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        //create the workbook
        try{
            workbook = WorkbookFactory.create(new File(excelFilePath));
        }catch(EncryptedDocumentException ex){
            ex.printStackTrace();
        }catch(IOException e) {
            throw new RuntimeException(e);
        }

        //Retrieving the number of sheets in workbook
        log.info("Workbook has " + workbook.getNumberOfSheets() + "sheets");

        //Getting the sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        //Getting the number of column in sheet
        Integer noOfColumns = Integer.valueOf(sheet.getRow(0).getLastCellNum());
        log.info("Sheet has " +noOfColumns+" columns");

        //Using for each loop to iterate over the rows and columns
        for(Row row: sheet)
        {
            for(Cell cell:row)
            {
                String cellValue = dataFormatter.formatCellValue(cell);
                list.add(cellValue);
            }
        }

        //filling excel data and creating list as List of invoices
        List<Invoice> invList = createList(list, noOfColumns);
        //closing the workbook

        try {
            workbook.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return invList;
    }

    private List<Invoice> createList(List<String> excelDatalist, Integer noOfColumns) {

        ArrayList<Invoice> invList = new ArrayList<Invoice>();
        int i = noOfColumns;
        do
        {
            Invoice inv = new Invoice();
            inv.setName(excelDatalist.get(i));
            inv.setAmount(Double.valueOf(excelDatalist.get(i+1)));
            inv.setNumber(excelDatalist.get(i+2));
            inv.setRecievedDate(excelDatalist.get(i+3));
            invList.add(inv);
            i=i+noOfColumns;

        }while(i<excelDatalist.size());
        return invList;
    }

    @Override
    public int saveExcelData(List<Invoice> invoice){
        invoiceRepository.saveAll(invoice);
        return invoice.size();
    }
}
