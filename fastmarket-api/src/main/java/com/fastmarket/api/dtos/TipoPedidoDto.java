package com.fastmarket.api.dtos;

import jakarta.validation.constraints.NotNull;

public record TipoPedidoDto(
        @NotNull
        String tipoPedido
) {
}
