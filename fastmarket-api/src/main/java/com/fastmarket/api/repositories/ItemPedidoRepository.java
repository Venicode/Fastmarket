package com.fastmarket.api.repositories;

import com.fastmarket.api.domains.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query("SELECT COALESCE(SUM(i.valor - i.desconto), 0) " +
            "FROM ItemPedido i WHERE i.pedido.caixa.id = :caixaId")
    Double somarVendasPorCaixa(@Param("caixaId") Long caixaId);

    List<ItemPedido> findByPedidoId(Long pedidoId);
}