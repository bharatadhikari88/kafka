package org.eagle.kafka.consumer;

import org.eagle.kafka.consumer.dto.Record;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "kafka_topic1", groupId = "boot_group", containerFactory = "kafkaListenerContainerFactory")
	public void consumer(Record record) {
		System.out.println(record.getMsg());
	}

}
