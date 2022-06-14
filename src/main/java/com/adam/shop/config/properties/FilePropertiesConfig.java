package com.adam.shop.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Getter
@Setter
@ConfigurationProperties (prefix = "file") //zbiera wszystkie wartości z yml dla danego prefiksu
public class FilePropertiesConfig {
    private String product;
}
