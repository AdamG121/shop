package com.adam.shop.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Audited //do generowania tabelek audytowych (możliwe po dodaniu dependency Spring Data Envers)
@EntityListeners(AuditingEntityListener.class) //włącznik auditingu dla klasy
@Builder
@Entity // zostanie wygenerowana tabelka user
@Data // generuje getery setery toString equals hashCode i wieloargumentowy konstruktor dla finalnych zmiennych
@NoArgsConstructor // bez-agumentowy konstruktor
@AllArgsConstructor // wieloargumentowy konstruktor
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) //ta adnotacja zapewnia auto-inkrementacje
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @NotAudited
    private String password;

    //auditing
    @CreatedBy //generuje się w workbench na podstawie AuditorAwareImpl
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedBy //generuje się w workbench na podstawie AuditorAwareImpl
    private String lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    //auditing

    //security
    @ManyToMany
    @JoinTable (name = "user_role", inverseJoinColumns = @JoinColumn(name = "role_id"))
    // adnotacja @JoinTable - adnotacja do zmiany nazwy tabeli (nie do robienia joina)
    // name - zmienna odpowiedzialna za nazwę tabelki łączącej - czyli to String
    // inverseJoinColumns - odpowiada za kolumnę łączącą z tabelką Role, inverseJoinColumns wyznacza foreign key dla Roli + trzeba dodać adnotację @JoinColumn
    private List<Role> roles; //liste jako typ pola klasy ZAWSZE!!! dajemy w relacjach one to many i many to many
    // dodajemy listę ról bo 1 użytkownik może miec ich wiele

    @Column(unique = true)
    private String activatedToken;
}

// automatyczne dodanie tabelki po uruchomieniu aplikacji z klasy main