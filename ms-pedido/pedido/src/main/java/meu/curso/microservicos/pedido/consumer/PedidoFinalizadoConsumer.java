package meu.curso.microservicos.pedido.consumer;

import meu.curso.microservicos.pedido.repository.PedidoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoFinalizadoConsumer {

    private final PedidoRepository repository;

    public PedidoFinalizadoConsumer(PedidoRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "fila.pedido.concluido")
    public void atualizarStatus(@Payload String mensagem) {
        try {
            Long id = Long.parseLong(mensagem);

            // O ifPresent garante que só faz algo se o pedido ainda existir no banco
            repository.findById(id).ifPresentOrElse(pedido -> {
                pedido.setStatus("CONCLUIDO");
                repository.save(pedido);
                System.out.println("Pedido " + id + " atualizado!");
            }, () -> {
                System.out.println("Aviso: Recebi ID " + id + ", mas esse pedido não existe mais no banco.");
            });

        } catch (NumberFormatException e) {
            System.err.println("Mensagem inválida descartada: " + mensagem);
            // Tratando o erro aqui, a mensagem NÃO volta para a fila
        }


}
}
