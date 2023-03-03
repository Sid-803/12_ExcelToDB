package com.tutorial.document.excelDB.invoiceController;

import com.tutorial.document.excelDB.entity.Invoice;
import com.tutorial.document.excelDB.repository.InvoiceRepository;
import com.tutorial.document.excelDB.service.IExcelDataService;
import com.tutorial.document.excelDB.service.IFileUploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class InvoiceController {

    @Autowired
    IFileUploaderService fileService;

    @Autowired
    IExcelDataService iExcelDataService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @GetMapping("/upload")
    public String index() {
        return "uploadPage";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        fileService.uploadFile(file);

        redirectAttributes.addFlashAttribute("message",
                "You have successfully uploaded '"+ file.getOriginalFilename()+"' !");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/saveData")
    public String saveExcelData(Model model) {

        List<Invoice> excelDataAsList = iExcelDataService.getExcelDataAsList();
        int noOfRecords = iExcelDataService.saveExcelData(excelDataAsList);
        model.addAttribute("noOfRecords",noOfRecords);
        return "success";
    }

}
