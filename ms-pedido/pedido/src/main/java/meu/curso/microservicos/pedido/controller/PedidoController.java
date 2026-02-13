package meu.curso.microservicos.pedido.controller;


import meu.curso.microservicos.pedido.model.Pedido;
import meu.curso.microservicos.pedido.service.PedidoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final RabbitTemplate rabbitTemplate;
    private final PedidoService pedidoService;

    public PedidoController(RabbitTemplate rabbitTemplate, PedidoService pedidoService) {
        this.rabbitTemplate = rabbitTemplate;
        this.pedidoService = pedidoService;
    }

    @Value("${broker.queue.processamento.name}")
    private String routingKey;

    @PostMapping("/salvar")
    public String criarPedido(@RequestBody Pedido pedido){

        Pedido pedidoSalvo = pedidoService.salvarPedido(pedido);
//        rabbitTemplate.convertAndSend("", routingKey, pedidoSalvo.getDescricao());
        rabbitTemplate.convertAndSend("", routingKey, pedidoSalvo.getId().toString());

        return "Pedido recebido (ID " + pedidoSalvo.getId() + ") - Status: PENDENTE";
    }

    @GetMapping("/listar")
    public List<Pedido> listarPedidos(){
        return pedidoService.listarPedidos();
    }
}
