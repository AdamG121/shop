package com.adam.shop.flyweight.generic.strategy.generator.impl;

import com.adam.shop.flyweight.generic.strategy.generator.FileGeneratorStrategy;
import com.adam.shop.flyweight.model.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PdfFileGeneratorImpl implements FileGeneratorStrategy {
    @Override
    public FileType getType() {
        return FileType.PDF;
    }

    @Override
    public byte[] generateFile() {
        log.info("Generate pdf");
        return new byte[0];
    }
}
