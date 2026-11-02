package com.fastmarket.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoriaProdutoDto(
        @NotBlank
        String categoriaProduto
) {
}
