//package com.userservice.config;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageListener {
//    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
//    public void receiveMessage(String message) {
//        System.out.println("Received message: " + message);
//    }
//}
//
//// If you're receiving objects, you need to match the type:
//    /*
//    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
//    public void receiveObject(YourObjectType object) {
//        System.out.println("Received object: " + object);
//        // Process the object here
//    }
//}
//
//     */
