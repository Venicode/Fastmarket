package com.fastmarket.api.domains;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "CategoriaProduto")
@Table(name = "TB_CATEGORIAPRODUTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoriaProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoriaProduto;
}
