package com.example.demo.mq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReturnCallbackService implements RabbitTemplate.ReturnCallback {

    private static final Logger logger = LoggerFactory.getLogger(ReturnCallbackService.class);

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("returnedMessage ===> replyCode="+replyCode+" ,replyText="+replyText+" ,exchange="+exchange+" ,routingKey="+routingKey);
    }
}
