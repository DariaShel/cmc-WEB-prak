package ru.msu.cmc.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "animals")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Animals implements CommonEntity<Long> {

    public enum StatusType {
        DEAD,
        ALIVE
    }

    public enum MigrationsType {
        NO_MIGRATING,
        MIGRATING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_animal")
    private Long id_animal;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "class")
    private String class_field;

    @Column(name = "family")
    private String family;

    @Column(name = "species")
    private String species;

    @Column(name = "latin_name")
    private String latin_name;

    @Column(name = "status")
    @NonNull
    private StatusType status;

    @Column(nullable = false, name = "migrations")
    @NonNull
    private MigrationsType migrations;

    @Column(name = "appearance")
    private String appearance;

    @Column(name = "behavior")
    private String behavior;

    @Column(name = "communications")
    private String communications;

    @Column(name = "photo")
    private String photo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animals other = (Animals) o;
        return Objects.equals(id_animal, other.id_animal)
                && Objects.equals(name, other.name)
                && Objects.equals(type, other.type)
                && Objects.equals(class_field, other.class_field)
                && Objects.equals(family, other.family)
                && Objects.equals(species, other.species)
                && Objects.equals(latin_name, other.latin_name)
                && Objects.equals(status, other.status)
                && Objects.equals(migrations, other.migrations)
                && Objects.equals(appearance, other.appearance)
                && Objects.equals(behavior, other.behavior)
                && Objects.equals(communications, other.communications)
                && Objects.equals(photo, other.photo);
    }

    @Override
    public Long getId() {
        return id_animal;
    }

    @Override
    public void setId(Long aLong) {
        id_animal = aLong;
    }

}
