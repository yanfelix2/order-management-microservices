package meu.curso.microservicos.pedido.consumer;

import meu.curso.microservicos.pedido.repository.PedidoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoCanceladoConsumer {

    private PedidoRepository pedidoRepository;

    public PedidoCanceladoConsumer(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @RabbitListener(queues = "fila.pedido.cancelado")
    public void atualizarStatusCancelado(@Payload String mensagem){
        try {
            // O ideal é que o Processamento envie apenas o número do ID
            Long id = Long.parseLong(mensagem);

            System.out.println(">>> Recebida notificação de CANCELAMENTO para o pedido: " + id);

            pedidoRepository.findById(id).ifPresentOrElse(pedido -> {
                pedido.setStatus("CANCELADO");
                pedidoRepository.save(pedido);
                System.out.println("Status do pedido " + id + " atualizado para CANCELADO no banco.");
            }, () -> {
                System.out.println("Aviso: Tentativa de cancelar pedido " + id + ", mas ele não existe no banco.");
            });

        } catch (NumberFormatException e) {
            System.err.println("Erro ao processar ID de cancelamento: " + mensagem);
        }
    }


}
