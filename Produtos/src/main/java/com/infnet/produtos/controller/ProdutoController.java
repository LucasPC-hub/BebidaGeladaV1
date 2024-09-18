package com.infnet.produtos.controller;

import com.infnet.produtos.model.HistoricoProduto;
import com.infnet.produtos.model.Produto;
import com.infnet.produtos.services.HistoricoProdutoService;
import com.infnet.produtos.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private HistoricoProdutoService historicoProdutoService;

    @Operation(summary = "Listar todos os produtos")
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @Operation(summary = "Adicionar um novo produto")
    @PostMapping
    public Produto adicionarProduto(@RequestBody Produto produto) {
        return produtoService.salvarProduto(produto);
    }

    @Operation(summary = "Buscar um produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado", content = @Content(schema = @Schema(implementation = Produto.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProduto(@Parameter(description = "ID do produto a ser buscado") @PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar todos os produtos")
    @DeleteMapping("/all")
    public ResponseEntity<Void> deletarTodosProdutos() {
        produtoService.deletarTodosProdutos();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar um produto por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@Parameter(description = "ID do produto a ser deletado") @PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar histórico de produtos")
    @GetMapping("/historico")
    public List<HistoricoProduto> listarHistorico() {
        return historicoProdutoService.listarHistorico();
    }
}