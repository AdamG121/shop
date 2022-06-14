package com.adam.shop.flyweight.generic.strategy.generator;

import com.adam.shop.flyweight.generic.strategy.GenericStrategy;
import com.adam.shop.flyweight.model.FileType;

public interface FileGeneratorStrategy extends GenericStrategy <FileType> {
    byte[] generateFile();
}
