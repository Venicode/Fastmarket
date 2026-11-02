package com.fastmarket.api.dtos;

import jakarta.validation.constraints.NotNull;

public record MetodoPagamentoDto(
        @NotNull
        String metodoPagamento
) {
}
