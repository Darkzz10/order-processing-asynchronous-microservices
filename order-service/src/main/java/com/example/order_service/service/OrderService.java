package com.example.order_service.service;

import com.example.order_service.dto.OrderRequest;
import com.example.order_service.event.OrderCreatedEvent;
import com.example.order_service.model.Order;
import com.example.order_service.producer.OrderProducer;
import com.example.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/*
Al usar RabbitMQ si el inventario se cae, el pedido se guarda en PostgreSQL,
el cliente recibe una confirmación inmediata, y el mensaje se queda
seguro en la cola esperando a que el inventario reviva para procesarlo.
Mejoré la tolerancia a fallos y reduje la latencia de la API.
*/

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;


    public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @Transactional
    //realizar pedido
    public String placeOrder(OrderRequest orderRequest) {

        BigDecimal unitPrice = new BigDecimal("100.00"); // Precio de ejemplo
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(orderRequest.getQuantity()));

        // se crea la orden en estado PENDING (Pendiente)
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(orderRequest.getSkuCode())
                .quantity(orderRequest.getQuantity())
                .price(unitPrice)
                .totalAmount(totalPrice)
                .status("PENDING") // estará pendiente hasta que el inventario lo confirme después
                .build();

        orderRepository.save(order);

        // creo el evento con los datos que le importan al inventario
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setOrderNumber(order.getOrderNumber());
        event.setSkuCode(order.getSkuCode());
        event.setQuantity(order.getQuantity());

        // disparo el mensaje a RabbitMQ de forma asíncrona
        orderProducer.enviarPedidoARabbitMQ(event);

        return "Pedido recibido con éxito (Nro: " + order.getOrderNumber() + "). Procesando inventario.";
    }
}

