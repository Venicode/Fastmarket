package com.fastmarket.api.dtos;

import com.fastmarket.api.domains.Pedido;
import com.fastmarket.api.domains.Produto;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoDto(
        @NotNull
        Produto produto,
        @NotNull
        Pedido pedido,
        @NotNull
        double valor,
        @NotNull
        int quantidade,
        double desconto,
        double troco
) {
}
