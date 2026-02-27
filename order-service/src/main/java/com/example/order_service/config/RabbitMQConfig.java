package com.example.order_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    public static final String NOMBRE_COLA_PEDIDOS = "pedidos.queue";

    // arrancar y guarda el resultado
    @Bean
    public Queue configurarColaDePedidos() {

    // durable = true --> significa que si Docker o el broker se cae
    // la cola y los mensajes sobreviven es decir no perdemos los pedidos de los clientes
        boolean sobrevivirReinicio = true;

        return new Queue(NOMBRE_COLA_PEDIDOS, sobrevivirReinicio);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        // este objeto se encarga de convertir automáticamente
        // los DTOs a JSON antes de enviarlos a la cola
        return new Jackson2JsonMessageConverter();
    }
}
