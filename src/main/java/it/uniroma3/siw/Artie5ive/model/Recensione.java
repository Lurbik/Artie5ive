package it.uniroma3.siw.Artie5ive.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(
    name = "recensioni",
    uniqueConstraints = @UniqueConstraint(columnNames = {"utente_id", "vino_id"})
)
@Data
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String testo;

    @Min(1) @Max(5)
    @Column(nullable = false)
    private Integer voto;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vino_id", nullable = false)
    private Vino vino;

    @PrePersist
    public void prePersist() {
        if (this.data == null) {
            this.data = LocalDate.now();
        }
    }
}