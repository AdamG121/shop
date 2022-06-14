package com.adam.shop.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

// ustawienie pól klasy POJO w zakresie auditingu - wcześniej creadedBy i lastModifiedBy były z defaultu jako null
@Component //rejestruje klasę jako bean w Spring
//AuditorAware musi być sparametryzowany zgodnie z typami CreatedBy LastModifiedBy które są typu String
public class AuditorAwareImpl implements AuditorAware<String> {

    //dzięki tej klasie będą się automatycznie ustawiały pola createdBy i LastModifiedBy (bez tego byłyby puste wartości)

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtils.getCurrentUserEmail());
    }

    //ofNullable - jesli wartość o optionalu będzie null to będzie po prosti pusty Optional i nie rzuci NullPointerException
}
