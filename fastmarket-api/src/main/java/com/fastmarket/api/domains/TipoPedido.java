package com.fastmarket.api.domains;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "TipoPedido")
@Table(name = "TB_TIPOPEDIDO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoPedido;
}
