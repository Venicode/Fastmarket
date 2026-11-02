package com.fastmarket.api.services;

import com.fastmarket.api.domains.ItemPedido;
import com.fastmarket.api.domains.Pedido;
import com.fastmarket.api.domains.Produto;
import com.fastmarket.api.repositories.ItemPedidoRepository;
import com.fastmarket.api.dtos.ItemPedidoDto;
import com.fastmarket.api.infra.exceptions.ValorInsuficienteException;
import com.fastmarket.api.infra.exceptions.QuantidadeInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private EstoqueService estoqueService;

    public ItemPedido createItemPedido(ItemPedidoDto itemPedidoDto) throws Exception {
        Pedido pedido = pedidoService.findPedidoById(itemPedidoDto.pedido().getId());
        Produto produto = this.produtoService.findProdutoById(itemPedidoDto.produto().getId());
        double amountTotal = ((produto.getPrecoVenda() * itemPedidoDto.quantidade()) - itemPedidoDto.desconto());

        ItemPedido itemPedido = new ItemPedido();

        double bruto = produto.getPrecoVenda() * itemPedidoDto.quantidade();
        double desconto = itemPedidoDto.desconto();
        double esperado = bruto - desconto;

        if (desconto < 0 || desconto > bruto) {
            throw new ValorInsuficienteException("Desconto inválido.");
        }
        if (Math.abs(itemPedidoDto.valor() - esperado) > 0.01) {
            throw new ValorInsuficienteException("Valor do item não confere. Esperado: " + esperado);
        }
        if (itemPedidoDto.quantidade() > produto.getQuantidade()) {
            throw new QuantidadeInsuficienteException("Quantidade insuficiente no estoque.");
        }
        if (itemPedidoDto.quantidade() > produto.getQuantidade()) {
            throw new QuantidadeInsuficienteException("Quantidade insuficiente no estoque.");
        }
        itemPedido.setPedido(pedido);
        itemPedido.setValor(itemPedidoDto.valor());
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(itemPedidoDto.quantidade());
        itemPedido.setDesconto(itemPedidoDto.desconto());
        itemPedido.setTroco(itemPedidoDto.troco());
        this.itemPedidoRepository.save(itemPedido);

        this.estoqueService.updateEstoque(produto.getQuantidade() - itemPedidoDto.quantidade(), produto);
        return itemPedido;
    }

    public ResponseEntity<List<ItemPedido>> getAllItemPedidos() {
        List<ItemPedido> orderList = this.itemPedidoRepository.findAll();
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
}