package com.adam.shop.flyweight.generic.strategy.generator.impl;

import com.adam.shop.domain.dao.Product;
import com.adam.shop.flyweight.generic.strategy.generator.FileGeneratorStrategy;
import com.adam.shop.flyweight.model.FileType;
import com.adam.shop.repository.ProductRepository;
import com.adam.shop.security.SecurityUtils;
import com.adam.shop.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class XlsFileGeneratorImpl implements FileGeneratorStrategy {
    private final ProductRepository productRepository;
    private final MailService mailService;

    @Override
    public FileType getType() {
        return FileType.XLS;
    }

    @Override
    public byte[] generateFile() {
        log.info("Generate Xls");
        try {
            Workbook workbook = WorkbookFactory.create(false);
            Sheet sheet = workbook.createSheet("Product import");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Id");
            row.createCell(1).setCellValue("Name");
            row.createCell(2).setCellValue("Describtion");
            row.createCell(3).setCellValue("Price");
            row.createCell(4).setCellValue("Quantity");

            List<Product> products = productRepository.findAll();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getDescription());
                row.createCell(3).setCellValue(product.getPrice());
                row.createCell(4).setCellValue(product.getQuantity());
            }

            sheet.setAutoFilter(new CellRangeAddress(0, products.size(), 0, 4));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            byte[] file = byteArrayOutputStream.toByteArray();
            mailService.send(SecurityUtils.getCurrentUserEmail(), "Test mail", Collections.emptyMap(),
                    file, "Test file");
            return file;


        } catch (IOException e) {
            log.error("Problem with generic excel", e);
        }
        return new byte[0];
    }
}
