package com.fastmarket.api.dtos;

import com.fastmarket.api.domains.CategoriaProduto;
import com.fastmarket.api.domains.Fornecedor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDto(
        @NotBlank
        String descricao,
        @NotNull
        double precoCusto,
        @NotNull
        double precoVenda,
        @NotNull
        Fornecedor fornecedor,
        @NotNull
        int quantidade,
        @NotNull
        CategoriaProduto categoriaProduto,
        @NotNull
        Long codigoBarras,
        String marca,
        @NotNull
        boolean ativo
) {}
