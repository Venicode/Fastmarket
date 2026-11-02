package com.fastmarket.api.dtos;

import jakarta.validation.constraints.NotNull;

public record AcessoDto(
        @NotNull
        String acesso
) {
}
