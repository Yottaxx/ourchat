package bit.group.ourchat.webSocket;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BookHandler {

    private static final Logger logger = LoggerFactory.getLogger(BookHandler.class);


    @RabbitListener(queues = {FanoutRabbitConfig.DEFAULT_BOOK_QUEUE})
    public void listenerAutoAck(String text, Message message, Channel channel) {

        // TODO 如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            logger.info("[消费者一监听的消息] - [{}]", text);
            new WebSocketServer().sendMqMessage(text);

            // TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // TODO 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}

