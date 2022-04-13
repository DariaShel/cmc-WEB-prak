package ru.msu.cmc.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "diseases")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Diseases implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_disease")
    private Long id_disease;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_animal")
    @ToString.Exclude
    @NonNull
    private Animals id_animal;

    @Column(name = "name_disease")
    private String name_disease;

    @Column(name = "time_disease")
    private java.util.Date time_disease;

    @Column(name = "recovery")
    private java.util.Date recovery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee")
    @ToString.Exclude
    @NonNull
    private Employees id_employee;

    @Column(name = "help")
    private String help;

    @Column(name = "consequences")
    private String consequences;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diseases other = (Diseases) o;
        return Objects.equals(id_disease, other.id_disease)
                && Objects.equals(id_animal, other.id_animal)
                && Objects.equals(name_disease, other.name_disease)
                && Objects.equals(time_disease, other.time_disease)
                && Objects.equals(recovery, other.recovery)
                && Objects.equals(id_employee, other.id_employee)
                && Objects.equals(help, other.help)
                && Objects.equals(consequences, other.consequences);
    }

    @Override
    public Long getId() {
        return id_disease;
    }

    @Override
    public void setId(Long aLong) {
        id_disease = aLong;
    }
}
