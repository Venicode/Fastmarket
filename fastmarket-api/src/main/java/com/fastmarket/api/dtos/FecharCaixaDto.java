package com.fastmarket.api.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FecharCaixaDto(
        @NotNull BigDecimal valorContado
) {}