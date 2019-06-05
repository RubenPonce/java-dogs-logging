package com.lambdaschool.dogsinitial;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
class DogMessageListener {
private static final Logger logger = LoggerFactory.getLogger(DogMessageListener.class);
    @RabbitListener(queues = DogsinitialApplication.QUEUE_DOGGO)
    public void deliverDoggos(MessageDetail dogMessage) {
//        System.out.println(dogMessage.getText());
//        logger.info(dogMessage.getText());
    }
    @RabbitListener(queues = DogsinitialApplication.QUEUE_CHECK_BREED)
    public void deliverBreeds(MessageDetail dogMessage) {
//        System.out.println(dogMessage.getText());
//        logger.info(dogMessage.getText());
    }
}
