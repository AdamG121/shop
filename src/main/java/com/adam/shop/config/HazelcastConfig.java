package com.adam.shop.config;

import com.hazelcast.config.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching //włącza casching w całej aplikacji
@Configuration
public class HazelcastConfig { //to samo co cache - pamięć podręczna
    @Bean
    Config configHazelcast(){
        return new Config()
                .setInstanceName("hazelcast-instance") //nazwa instancji aplikacji
                .addMapConfig(new MapConfig()
                        .setName("product") //nazwa mapy (zmiennej), nie mylić z kluczem
                        .setEvictionConfig(new EvictionConfig()
                                .setEvictionPolicy(EvictionPolicy.LRU) //polityka usuwania obiektów z mapy
                                .setSize(100) //rozmiar mapy - ile może być par klucz-wartość
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)) //polityka usuwania obiektów z mapy gdy przepełni się pamięć
                        .setTimeToLiveSeconds(60*60*24));
    }
}
