package com.fastmarket.api.dtos;

import com.fastmarket.api.domains.Cliente;
import com.fastmarket.api.domains.MetodoPagamento;
import com.fastmarket.api.domains.StatusPedido;
import com.fastmarket.api.domains.TipoPedido;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PedidoDto(
        Cliente cliente,
        @NotNull MetodoPagamento metodoPagamento,
        @NotNull TipoPedido tipoPedido,
        StatusPedido statusPedido,
        String enderecoEntrega,
        String entregador
) {}
