package ru.msu.cmc.webprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "labels")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Labels implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_label")
    private Long id_label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_animal")
    @NonNull
    private Animals id_animal;

    @Column(name = "installation_time")
    private Date installation_time;

    @Column(name = "uninstallation_time")
    private Date uninstallation_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee")
    @NonNull
    private Employees id_employee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Labels other = (Labels) o;
        return Objects.equals(id_label, other.id_label)
                && Objects.equals(id_animal, other.id_animal)
                && Objects.equals(installation_time, other.installation_time)
                && Objects.equals(uninstallation_time, other.uninstallation_time)
                && Objects.equals(id_employee, other.id_employee);
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long aLong) {

    }
}
