package com.fastmarket.api.domains;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Caixa")
@Table(name = "TB_CAIXA")
@Getter @Setter @NoArgsConstructor
public class Caixa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_login", nullable = false)
    private Login operador;

    @Column(nullable = false)
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;

    @Column(nullable = false)
    private BigDecimal valorInicial;
    private BigDecimal valorEsperado;
    private BigDecimal valorContado;
    private BigDecimal diferenca;
}