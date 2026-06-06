package it.uniroma3.siw.Artie5ive.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "regioni")
public class Regione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
}