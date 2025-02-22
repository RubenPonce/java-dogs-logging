package com.lambdaschool.dogsinitial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/dogs")
public class DogController
{
    private static final Logger logger = LoggerFactory.getLogger(DogController.class);
    @Autowired
    RabbitTemplate rt;

    // localhost:8080/dogs/dogs
    @GetMapping(value = "/alldogs")
    public ResponseEntity<?> getAllDogs(HttpServletRequest res)
    {
        //log messages and send to rabbitMQ
        MessageDetail message = new MessageDetail( res.getRequestURI()+ " accessed ", 7, false);
        logger.info(message.getText());
        rt.convertAndSend(DogsinitialApplication.QUEUE_DOGGO, message);

        return new ResponseEntity<>(DogsinitialApplication.ourDogList.dogList, HttpStatus.OK);
    }

    // localhost:8080/dogs/{id}
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDogDetail(@PathVariable long id, HttpServletRequest res)
    {
        MessageDetail message = new MessageDetail( res.getRequestURI()+ " accessed ", 7, false);
        logger.info(message.getText());
        rt.convertAndSend(DogsinitialApplication.QUEUE_DOGGO, message);

        Dog rtnDog = DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id));
        return new ResponseEntity<>(rtnDog, HttpStatus.OK);
    }

    // localhost:8080/dogs/breeds/{breed}
    @GetMapping(value = "/breeds/{breed}")
    public ResponseEntity<?> getDogBreeds (@PathVariable String breed, HttpServletRequest res)
    {
        //log messages and send to rabbitMQ
        MessageDetail message = new MessageDetail( res.getRequestURI()+ " accessed ", 7, false);
        logger.info(message.getText());
        rt.convertAndSend(DogsinitialApplication.QUEUE_CHECK_BREED, message);

        ArrayList<Dog> rtnDogs = DogsinitialApplication.ourDogList.
                findDogs(d -> d.getBreed().toUpperCase().equals(breed.toUpperCase()));
        return new ResponseEntity<>(rtnDogs, HttpStatus.OK);
    }
}
