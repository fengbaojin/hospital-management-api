package com.example.demo.mq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConfirmCallbackService implements RabbitTemplate.ConfirmCallback {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmCallbackService.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            logger.error("消息发送异常!");
        } else {
            logger.info("生产者已经收到确认,ack=" + ack + ", cause=" + cause);//ack=true, cause=null
        }
    }
}