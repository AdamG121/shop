package com.adam.shop.flyweight.standard.strategy.impl;

import com.adam.shop.flyweight.model.FileType;
import com.adam.shop.flyweight.standard.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class XlsGeneratorStrategyImpl implements GeneratorStrategy {
    @Override
    public byte[] generateFile() {
        log.info("generate Xls");
        return new byte[0];
    }

    @Override
    public FileType getType() {
        return FileType.XLS;
    }
}
