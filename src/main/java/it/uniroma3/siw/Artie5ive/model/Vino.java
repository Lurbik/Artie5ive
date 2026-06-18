package it.uniroma3.siw.Artie5ive.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import jakarta.validation.constraints.NotNull;

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

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    private String immagine;

    private Double gradazione;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColoreVino colore;

    @NotNull
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

    @OneToMany(mappedBy = "vino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recensione> recensioni = new ArrayList<>();

}