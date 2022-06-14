package com.adam.shop.validator;

import com.adam.shop.validator.impl.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = PasswordValidator.class)// tutaj określam że to jest tylko dla klasy UserDto
@Documented //zawsze jak tworze własną adnotację
@Target(ElementType.TYPE) //tylko do klas
@Retention(RetentionPolicy.RUNTIME) //podczas działania programu adnotacja działa automatycznie
public @interface PasswordValid {
    String message() default "Confirmed password aren't the same"; //komunikat błędu jesli adnotacja nie przejdzie

    Class<?>[] groups() default {};//w jakich grupach metod walidacja będzie włączona, gdzie grupy to są interfejsy które mówią nam że adnotacja jest właczona albo wyłączona

    Class<? extends Payload>[] payload() default {};
}
