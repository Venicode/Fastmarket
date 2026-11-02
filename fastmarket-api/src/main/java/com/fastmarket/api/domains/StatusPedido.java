package com.fastmarket.api.domains;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "StatusPedido")
@Table(name = "TB_STATUSPEDIDO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class StatusPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
}
