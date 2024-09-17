package com.infnet.produtos.consumer;

import com.infnet.produtos.services.ProdutoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @Autowired
    private ProdutoService produtoService;

    @RabbitListener(queues = "produtoQueue")
    public void receiveMessage(String message) {
        // Parse the message to extract product ID and quantity
        String[] parts = message.split(", ");
        Long produtoId = Long.parseLong(parts[0].split(": ")[1]);
        int quantidade = Integer.parseInt(parts[1].split(": ")[1]);

        // Reduce the product quantity
        produtoService.reduzirQuantidade(produtoId, quantidade);
    }
}