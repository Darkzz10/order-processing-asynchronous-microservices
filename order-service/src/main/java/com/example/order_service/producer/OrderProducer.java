package com.example.order_service.producer;


import com.example.order_service.config.RabbitMQConfig;
import com.example.order_service.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProducer {

    // igual que uso CrudRepository para no escribir SQL
    // uso RabbitTemplate para no escribir toda la conexión al broker a mano
    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void enviarPedidoARabbitMQ(OrderCreatedEvent event) {

        System.out.println("Enviando el pedido #" + event.getOrderNumber() + "a RabbitMQ");

        // convertAndSend hace dos cosas:
        //  -convierte el objeto Java (evento) a JSON
        //  -lo envía a la cola NOMBRE_COLA_PEDIDOS
        rabbitTemplate.convertAndSend(RabbitMQConfig.NOMBRE_COLA_PEDIDOS, event);
        System.out.println("Mensaje enviado con éxito a la cola");
    }

}