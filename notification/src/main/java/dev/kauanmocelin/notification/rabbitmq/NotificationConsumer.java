package dev.kauanmocelin.notification.rabbitmq;

import dev.kauanmocelin.notification.NotificationRequest;
import dev.kauanmocelin.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(NotificationRequest notificationRequest) {
        /* simulate fail on message consume process
        log.info("try to process message {}", notificationRequest.message());
        throw new RuntimeException();*/
        log.info("Consumed {} from queue", notificationRequest);
        notificationService.send(notificationRequest);
    }

}
