package it.uniroma3.siw.Artie5ive.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "vini", uniqueConstraints = @UniqueConstraint(columnNames = { "nome", "annata", "produttore_id" }))
public class Vino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer annata;

    private String descrizione;

    private String immagine;

    private Double gradazione;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColoreVino colore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StileVino stile;

    @ManyToOne
    @JoinColumn(name = "produttore_id")
    private Produttore produttore;

    @ManyToOne
    @JoinColumn(name = "regione_id")
    private Regione regione;

    @ManyToMany
    @JoinTable(name = "vino_categoria", joinColumns = @JoinColumn(name = "vino_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorie;
}