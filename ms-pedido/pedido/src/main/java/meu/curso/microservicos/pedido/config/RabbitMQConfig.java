package meu.curso.microservicos.pedido.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${broker.queue.concluido.name}")
    private String queueName;

    @Value("${broker.queue.cancelado.name}")
    private String queueCancelados;


    @Bean
    public Queue concluidoQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue canceladoQueue(){
        return new Queue(queueCancelados, true);
    }

}
