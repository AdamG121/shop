package com.adam.shop.mapper;

import com.adam.shop.domain.dao.Product;
import com.adam.shop.domain.dao.User;
import com.adam.shop.domain.dto.ProductDto;
import com.adam.shop.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.history.Revision;


@Mapper(componentModel = "spring")
public interface HistoryMapper {
    // adnotacja mapping odnosi się do dependency mapstruct
    // w klasie Revision mamy getter getRequiredRevisionNumber który zwraca typ T - do source kopiujemy wszystko co jest po get
    // uwaga nie myslić z getterem getRevisionNumber - on zwraca Optionala a my musimy mieć koniecznie Intiger
    // w poniższym przypadku Usera pobiera przez metodę getEntity
    // getEntity
    // getRequiredRevisionNumber
    // revisionNumber jest tylko w klasach z warstwy dto
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber") //source - to cały obiekt revision to pole na podstawie implementacji mapera (dzięki dependency mapstruct)
    @Mapping(source = "entity.id", target = "id")//source - piszemy z jakiego pola revision chcemy korzystać, target - do czego będziemy wpisywać
    @Mapping(source = "entity.firstName", target = "firstName")
    @Mapping(source = "entity.lastName", target = "lastName")
    @Mapping(source = "entity.email", target = "email")
    UserDto revisionToUserDto (Revision<Integer, User> revision); //Revision - typ danych dostarczany przez Springa aby można było pobrać dane z tabelki user_aud - 1 wiersz z tabelki audytowej
    // z klasy Revision N - to nasz Integer - metadata
    // T - to nasz User - Entity - teraz mamy informacje że w entity znajduje nam się user czyli będziemy musieli
    // wchodzić do entity i z entity brać np id Usera i przekazywać go do UserDta
    // Podsumowanie:
    // Revision ma dwa pola: metadata i entity
    // metadata - odpowiada numerowi rewizji (encji bazodanowej) i musi być typu Integer
    // entity - w powyższym przypadku to User - user stanowi rekord z tabelki bazodanowej więc odpowiada dao

    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.price", target = "price")
    @Mapping(source = "entity.quantity", target = "quantity")
    @Mapping(source = "entity.description", target = "description")
    ProductDto revisionToProductDto (Revision<Integer, Product> revision);
}
