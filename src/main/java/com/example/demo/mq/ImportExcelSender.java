package com.example.demo.mq;


import com.alibaba.fastjson.JSON;
import com.example.demo.config.RabbitMQConstant;
import com.example.demo.mq.service.ConfirmCallbackService;
import com.example.demo.mq.service.ReturnCallbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fengbaojin
 * @date 2022/04/31
 */
@Component
public class ImportExcelSender {

    private static final Logger logger = LoggerFactory.getLogger(ImportExcelSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConfirmCallbackService confirmCallbackService;

    @Autowired
    private ReturnCallbackService returnCallbackService;

    public void sender(String name) {
        try {
            /**
             * 确保消息发送失败后可以重新返回到队列中
             * 注意：yml需要配置 publisher-returns: true
             */
            rabbitTemplate.setMandatory(true);

            /**
             * 消费者确认收到消息后，手动ack回执回调处理
             */
            this.rabbitTemplate.setConfirmCallback(confirmCallbackService);

            /**
             * 消息投递到队列失败回调处理
             */
            this.rabbitTemplate.setReturnCallback(returnCallbackService);
            // 发送消息
            this.rabbitTemplate.convertAndSend(RabbitMQConstant.EXCHANGE, RabbitMQConstant.IMPORT_EXCEL_KEY, JSON.toJSONString(name));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
