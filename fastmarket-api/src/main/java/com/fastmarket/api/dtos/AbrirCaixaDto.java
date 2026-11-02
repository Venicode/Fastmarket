package com.fastmarket.api.dtos;

import com.fastmarket.api.domains.Acesso;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AbrirCaixaDto(
        @NotNull Long loginId,
        @NotNull BigDecimal valorInicial
) {}
