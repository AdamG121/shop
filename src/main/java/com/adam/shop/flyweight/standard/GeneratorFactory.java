package com.adam.shop.flyweight.standard;

import com.adam.shop.flyweight.model.FileType;
import com.adam.shop.flyweight.standard.strategy.GeneratorStrategy;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GeneratorFactory {
    private final List<GeneratorStrategy> generatorStrategyList;

    private Map<FileType, GeneratorStrategy> strategyMap;

    @PostConstruct //pozwala na wywłanie metody po wywołaniu konstruktora
    void init(){
        strategyMap = generatorStrategyList.stream()
                .collect(Collectors.toMap(GeneratorStrategy::getType, Function.identity()));
    }

    public GeneratorStrategy getStrategyByType (FileType fileType) {
        return strategyMap.get(fileType);
    }
}
