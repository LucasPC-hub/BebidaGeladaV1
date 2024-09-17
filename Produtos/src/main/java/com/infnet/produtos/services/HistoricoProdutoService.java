package com.infnet.produtos.services;

import com.infnet.produtos.model.HistoricoProduto;
import com.infnet.produtos.repository.HistoricoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoProdutoService {
    @Autowired
    private HistoricoProdutoRepository historicoProdutoRepository;

    public List<HistoricoProduto> listarHistorico() {
        return historicoProdutoRepository.findAll();
    }
}