package com.example.demo.mq.receiver;

import com.alibaba.fastjson.JSON;
import com.example.demo.aop.annotation.RabbitMessage;
import com.example.demo.config.RabbitMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author fengbaojin
 * @date 2022/04/31
 */
@Component
public class ImportExcelReceiver {


    private static final Logger logger = LoggerFactory.getLogger(ImportExcelReceiver.class);

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitMQConstant.IMPORT_EXCEL_QUEUE, durable = "true"),
                    exchange = @Exchange(value = RabbitMQConstant.EXCHANGE, type = ExchangeTypes.TOPIC, durable = "true"),
                    key = RabbitMQConstant.IMPORT_EXCEL_KEY
            )
    )
    @RabbitMessage
    public void statServiceTaskBoard(String message) {
        String name = JSON.parseObject(message, String.class);
        System.out.printf(name);
    }

}
