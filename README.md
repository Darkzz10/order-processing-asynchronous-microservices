Sistema de Procesamiento Asíncrono de Pedidos 🚀
Este proyecto implementa una arquitectura de microservicios diseñada para optimizar el tiempo de respuesta en plataformas de alta demanda, delegando tareas pesadas de inventario a través de mensajería asíncrona.

💼 El Problema de Negocio (El Dolor)
En sistemas síncronos tradicionales, cuando un cliente realiza un pedido, el sistema se bloquea esperando que el inventario confirme el stock y se procesen múltiples validaciones. Esto genera latencia, errores de timeout en picos de tráfico y una mala experiencia de usuario.

🛠️ La Solución Técnica
Implementé un flujo asíncrono utilizando RabbitMQ como message broker. El servicio de órdenes recibe la solicitud, la registra en PostgreSQL y publica un evento. El servicio de inventario consume este evento de forma independiente, permitiendo que el flujo principal de venta nunca se detenga.

🏗️ Arquitectura y Tecnologías
Java 21 & Spring Boot 3: Núcleo de los microservicios.

RabbitMQ: Message Broker para la comunicación desacoplada.

PostgreSQL: Almacenamiento persistente para órdenes e inventario.

Spring Security + JWT: Autenticación y protección de endpoints.

Docker & Docker Compose: Orquestación de infraestructura en contenedores.

🚀 Cómo ejecutar el proyecto
Para levantar todo el ecosistema (Bases de datos y RabbitMQ), solo necesitas ejecutar el siguiente comando en la raíz:

docker-compose up -d