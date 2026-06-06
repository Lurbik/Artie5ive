package it.uniroma3.siw.Artie5ive.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorie")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String descrizione;
}