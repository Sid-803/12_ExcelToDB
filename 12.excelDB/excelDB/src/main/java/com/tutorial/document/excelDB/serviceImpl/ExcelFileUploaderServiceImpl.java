package com.tutorial.document.excelDB.serviceImpl;

import com.tutorial.document.excelDB.entity.Invoice;
import com.tutorial.document.excelDB.service.IFileUploaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@Service
public class ExcelFileUploaderServiceImpl implements IFileUploaderService {

    public List<Invoice> invoiceExcelReaderService() {
        return null;
    }

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    @Override
    public void uploadFile(MultipartFile file) {
        try
        {
            //Path object to load all the files from the location
            Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(),copyLocation, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Could-not store the File " + file.getOriginalFilename() + " Please try again");
        }
    }
}
