package ru.msu.cmc.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Employees implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_employee")
    private Long id_employee;

    @Column(nullable = false, name = "surname")
    @NonNull
    private String surname;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "education")
    private String education;

    @Column(name = "work_experience")
    private long work_experience;

    @Column(name = "animal_species")
    private String animal_species;

    @Column(name = "help")
    private String help;

    @Column(name = "marked")
    private String marked;

    @Column(name = "photo")
    private String photo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employees other = (Employees) o;
        return Objects.equals(id_employee, other.id_employee)
                && Objects.equals(surname, other.surname)
                && Objects.equals(name, other.name)
                && Objects.equals(patronymic, other.patronymic)
                && Objects.equals(education, other.education)
                && Objects.equals(work_experience, other.work_experience)
                && Objects.equals(animal_species, other.animal_species)
                && Objects.equals(help, other.help)
                && Objects.equals(marked, other.marked)
                && Objects.equals(photo, other.photo);
    }

    @Override
    public Long getId() {
        return id_employee;
    }

    @Override
    public void setId(Long aLong) {
        id_employee = aLong;
    }
}
