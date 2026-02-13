package meu.curso.microservicos.processamento.consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProcessamentoConsumer {

    private final RabbitTemplate rabbitTemplate;

    public ProcessamentoConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${broker.queue.processamento.name}")
    public void listenerProcessamentoQueue(@Payload String pedidoId){
        System.out.println(">>> Iniciando Processamento do Pedido: " + pedidoId);

        try {
            // Simula o tempo de operação (ex: 5 segundos)
            Thread.sleep(5000);

            // LOGICA DE TESTE:
            // Vamos cancelar pedidos com ID ÍMPAR e concluir pedidos com ID PAR.
            // Isso permite que teste os dois consumidores do outro lado.
            Long id = Long.parseLong(pedidoId);

            if (id % 2 == 0) {
                // CAMINHO DO SUCESSO
                rabbitTemplate.convertAndSend("", "fila.pedido.concluido", pedidoId);
                System.out.println("✅ Pedido " + pedidoId + " processado com SUCESSO.");
            } else {
                // CAMINHO DO CANCELAMENTO
                rabbitTemplate.convertAndSend("", "fila.pedido.cancelado", pedidoId);
                System.out.println("❌ Pedido " + pedidoId + " CANCELADO por falta de estoque (Simulação).");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (NumberFormatException e) {
            System.err.println("⚠️ Erro ao converter ID do pedido: " + pedidoId);
        }
    }
}
