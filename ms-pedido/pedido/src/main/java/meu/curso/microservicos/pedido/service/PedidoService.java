package meu.curso.microservicos.pedido.service;


import meu.curso.microservicos.pedido.model.ItemPedido;
import meu.curso.microservicos.pedido.model.Pedido;
import meu.curso.microservicos.pedido.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

//    public Pedido salvarPedido(Pedido pedido){
//        pedido.setStatus("PENDENTE");
//        if (pedido.getItens() != null){
//            for (ItemPedido item : pedido.getItens()){
//                item.setPedido(pedido);
//            }
//        }
//
//        return pedidoRepository.save(pedido);
//    }
//
    public List<Pedido> listarPedidos(){
        return pedidoRepository.findAll();
    }

    public Pedido salvarPedido(Pedido pedido) {
        pedido.setStatus("PENDENTE");

        System.out.println("Quantidade de itens recebidos: " + (pedido.getItens() != null ? pedido.getItens().size() : "NULO"));

        // 1. Primeiro, salvamos o pedido para ele ganhar um ID do banco
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 2. Agora que o pedidoSalvo tem ID, vinculamos aos itens
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                item.setPedido(pedidoSalvo); // Usamos o objeto que já veio com ID do banco
            }
            // 3. Salvamos novamente (o JPA vai apenas atualizar os itens com o ID correto)
            pedidoRepository.save(pedidoSalvo);
        }

        return pedidoSalvo;
    }
}
