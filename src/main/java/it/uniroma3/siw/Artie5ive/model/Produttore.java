package it.uniroma3.siw.Artie5ive.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "produttori")
public class Produttore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    private Integer annoFondazione;

    private String immagine;

    @ManyToOne
    @JoinColumn(name = "regione_id")
    private Regione regione;
}