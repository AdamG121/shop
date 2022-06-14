package com.adam.shop.flyweight.standard.strategy;

import com.adam.shop.flyweight.model.FileType;

public interface GeneratorStrategy {
    byte[] generateFile();

    FileType getType();
}
