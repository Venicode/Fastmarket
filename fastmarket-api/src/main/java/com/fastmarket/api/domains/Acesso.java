package com.fastmarket.api.domains;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Acesso")
@Table(name = "TB_ACESSO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Acesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String acesso;
}
