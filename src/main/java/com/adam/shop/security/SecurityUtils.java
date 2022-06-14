package com.adam.shop.security;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE) //bezagrumentowy prywatny konstruktor - dzięki temu z zewnątrz nie można stworzyć instancji tego obiektu
public class SecurityUtils {
// klasy utils nie mają konstruktorów
// klasy utils do metod
// klasy constance do zmiennych

    //utils czyli powinna mieć tylko statyczne funkcje - do metod
    // *a w klasach Constance są zmienne statyczne - do zmiennych
    //klasy Utils (podobnie jak klasy Constance) nie mają w ogóle dostępnych konstruktorów

    //pobranie emaila obecnie zalogowanego użytkownika
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;

        //operator trójargumentowy tzw. elwis
        //warunek ? wartość jak prawda : wartość jak fałsz

        //wcześniej w klasie JwtAuthorizationFilter ustawialiśmy Authentication
    }
}
