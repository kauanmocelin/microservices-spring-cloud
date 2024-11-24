package dev.kauanmocelin.notification;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;
    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKey;

    @Value("${rabbitmq.exchanges.internal-dlq}")
    private String internalExchangeDlq;
    @Value("${rabbitmq.queues.notification-dlq}")
    private String notificationQueueDlq;
    @Value("${rabbitmq.routing-keys.internal-notification-dlq}")
    private String internalNotificationRoutingKeyDlq;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder
            .nonDurable(this.notificationQueue)
            .deadLetterExchange(this.internalExchangeDlq)
            .deadLetterRoutingKey(this.internalNotificationRoutingKeyDlq)
            .build();
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
            .bind(notificationQueue())
            .to(internalTopicExchange())
            .with(this.internalNotificationRoutingKey);
    }

    @Bean
    public TopicExchange internalTopicExchangeDlq() {
        return new TopicExchange(this.internalExchangeDlq);
    }

    @Bean
    public Queue notificationQueueDlq() {
        return new Queue(this.notificationQueueDlq);
    }

    @Bean
    public Binding internalToNotificationDlqBinding() {
        return BindingBuilder
            .bind(notificationQueueDlq())
            .to(internalTopicExchangeDlq())
            .with(this.internalNotificationRoutingKeyDlq);
    }
}
