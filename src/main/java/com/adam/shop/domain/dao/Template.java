package com.adam.shop.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Table (indexes = @Index(name = "idx_name", columnList = "name", unique = true))
@Audited //do generowania tabelek audytowych (możliwe po dodaniu dependency Spring Data Envers)
@EntityListeners(AuditingEntityListener.class) //włącznik auditingu dla klasy
@Builder //sam się wygeneruje wzorzec projektowy builder
@Entity //zostanie wygenerowana tabelka Entity -> będzie można wygenerować z bazy dancyh tebelkę z templejtami
@Data // generuje getery setery toString equals hashCode i wieloargumentowy konstruktor dla finalnych zmiennych
@NoArgsConstructor // bezagumentowy konstruktor
@AllArgsConstructor // wieloargumentowy konstruktor
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-inkrementacja
    private Long id;
    private String name;
    @Lob // ustawia typ bazodanowy na CLOB który przechowuje więcej niż 255 znaków ale NIE można po nich szukać obiektów w bazie dancyh (zwykły String przechowuje do 255 znaków)
    private String body;
    private String subject;
}


