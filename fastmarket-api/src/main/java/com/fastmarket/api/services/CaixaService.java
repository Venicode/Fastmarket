package com.fastmarket.api.services;

import com.fastmarket.api.domains.Caixa;
import com.fastmarket.api.domains.Login;
import com.fastmarket.api.repositories.CaixaRepository;
import com.fastmarket.api.repositories.ItemPedidoRepository;
import com.fastmarket.api.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Caixa abrir(Long loginId, BigDecimal valorInicial) {
        if (buscarAberto().isPresent()) {
            throw new IllegalStateException("Já existe um caixa aberto.");
        }

        Login operador = loginRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Operador não encontrado."));

        Caixa caixa = new Caixa();
        caixa.setOperador(operador);
        caixa.setValorInicial(valorInicial);
        caixa.setDataAbertura(LocalDateTime.now());
        return caixaRepository.save(caixa);
    }

    public Caixa fechar(Long caixaId, BigDecimal valorContado) {
        Caixa caixa = caixaRepository.findById(caixaId)
                .orElseThrow(() -> new IllegalArgumentException("Caixa não encontrado."));

        if (caixa.getDataFechamento() != null) {
            throw new IllegalStateException("Caixa já está fechado.");
        }

        Double totalVendas = itemPedidoRepository.somarVendasPorCaixa(caixaId);
        BigDecimal vendas = BigDecimal.valueOf(totalVendas == null ? 0.0 : totalVendas);
        BigDecimal esperado = caixa.getValorInicial().add(vendas);

        caixa.setValorEsperado(esperado);
        caixa.setValorContado(valorContado);
        caixa.setDiferenca(valorContado.subtract(esperado));
        caixa.setDataFechamento(LocalDateTime.now());
        return caixaRepository.save(caixa);
    }

    public Optional<Caixa> buscarAberto() {
        return caixaRepository.findFirstByDataFechamentoIsNullOrderByIdDesc();
    }
}