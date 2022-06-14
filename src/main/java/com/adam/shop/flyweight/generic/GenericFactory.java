package com.adam.shop.flyweight.generic;

import com.adam.shop.flyweight.generic.strategy.GenericStrategy;
import com.adam.shop.flyweight.model.FileType;
import com.adam.shop.flyweight.standard.GeneratorFactory;
import com.adam.shop.flyweight.standard.strategy.GeneratorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GenericFactory<K, V extends GenericStrategy<K>> {
    private final List<V> strategies;

    private Map<K, V> strategyMap;

    @PostConstruct
        //pozwala na wywłanie metody po wywołaniu konstruktora
    void init() {
        strategyMap = strategies.stream()
                .collect(Collectors.toMap(GenericStrategy::getType, Function.identity()));
    }

    public V getStrategyByType(K type) {
        return strategyMap.get(type);
    }
}
