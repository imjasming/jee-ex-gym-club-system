package com.xming.gymclubsystem.service;

import com.xming.gymclubsystem.domain.primary.Trainer;
import com.xming.gymclubsystem.domain.secondary.UserInfo;
import com.xming.gymclubsystem.repository.primary.GymRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class KafkaConsumer {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DataService dataService;

    @Autowired
    private UserService userService;

    @Autowired
    private GymRepository gymRepository;
    /**
     * 监听kafka的 topic
     *
     * @param record
     * @param topic  topic
     */
    @KafkaListener(topics = {"gyms"})
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //判断是否NULL
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            //获取消息
            Object message = kafkaMessage.get();

            //保存到redis数据库里面，key 为topic ，v 为message jsOn串
            redisTemplate.opsForList().rightPop(topic);
            redisTemplate.opsForList().rightPush(topic, gymRepository.findAll());

        }
    }


    @KafkaListener(topics = {"getInfo"})
    public void userInfoListener(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //判断是否NULL
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            //获取消息
            Object message = kafkaMessage.get();

            UserInfo userInfo = userService.getUserInfoByName((String) message);
            //保存到redis数据库里面，key 为Username ，v 为UserInfo jsOn串
            redisTemplate.opsForList().rightPop(message);
            redisTemplate.opsForList().rightPush(message, userInfo);

        }
    }


    @KafkaListener(topics = {"userAddTrainers"})
    public void getTrainersListener(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //判断是否NULL
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            //获取消息
            Object message = kafkaMessage.get();

            final List<Trainer> trainerList = dataService.getUserTrainers((String) message);
            //保存到redis数据库里面，key 为Username ，v trainerList jsOn串
            redisTemplate.opsForList().rightPop(message);
            redisTemplate.opsForList().rightPush(message, trainerList);
        }
    }

    @KafkaListener(topics = {"getTrainers"})
    public void obtainTrainersListener(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //判断是否NULL
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {
            //获取消息
            Object message = kafkaMessage.get();
            final List<Trainer> trainerList = dataService.getUserTrainers((String) message);
            //保存到redis数据库里面，key 为Username ，v trainerList jsOn串
            redisTemplate.opsForList().rightPop(message);
            redisTemplate.opsForList().rightPush(message, trainerList);
        }
    }



}