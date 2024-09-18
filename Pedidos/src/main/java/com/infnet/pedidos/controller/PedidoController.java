package com.infnet.pedidos.controller;

import com.infnet.pedidos.model.Pedido;
import com.infnet.pedidos.producer.MessageProducer;
import com.infnet.pedidos.service.PedidoService;
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
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private MessageProducer messageProducer;

    @Operation(summary = "Adicionar um novo pedido")
    @PostMapping
    public Pedido adicionarPedido(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.salvarPedido(pedido);
        pedido.getProdutos().forEach(produtoPedido -> {
            String message = "Produto ID: " + produtoPedido.getProdutoId() + ", Quantidade: " + produtoPedido.getQuantidade();
            messageProducer.sendMessage(message);
        });
        return novoPedido;
    }

    @Operation(summary = "Listar todos os pedidos")
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @Operation(summary = "Buscar um pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado", content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(@Parameter(description = "ID do pedido a ser buscado") @PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar um pedido por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@Parameter(description = "ID do pedido a ser deletado") @PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}