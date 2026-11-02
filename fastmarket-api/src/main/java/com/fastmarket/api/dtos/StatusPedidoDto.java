package com.fastmarket.api.dtos;

import jakarta.validation.constraints.NotNull;

public record StatusPedidoDto(
        @NotNull
        String status
) {
}
