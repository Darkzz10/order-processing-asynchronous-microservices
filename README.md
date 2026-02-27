# Sistema de Procesamiento Asíncrono de Pedidos 

Este proyecto implementa una arquitectura de microservicios diseñada para optimizar el tiempo de respuesta en plataformas de alta demanda, delegando tareas pesadas de inventario a través de mensajería asíncrona.

## El Problema de Negocio (El Dolor)
En sistemas síncronos tradicionales, cuando un cliente realiza un pedido, el sistema se bloquea esperando validaciones. Esto genera latencia y errores de timeout.

## La Solución Técnica
Implementé un flujo asíncrono utilizando **RabbitMQ** como message broker. El servicio de órdenes registra la solicitud en **PostgreSQL** y publica un evento, permitiendo que el flujo de venta nunca se detenga.

## Arquitectura y Tecnologías
* **Java 21 & Spring Boot **: Núcleo de los microservicios.
* **RabbitMQ**: Message Broker para la comunicación desacoplada.
* **PostgreSQL**: Almacenamiento persistente.
* **Spring Security + JWT**: Autenticación y protección.
* **Docker & Docker Compose**: Orquestación de infraestructura.

## 🚀 Cómo ejecutar el proyecto
Ejecuta el siguiente comando en la raíz:

```bash
docker-compose up -d
