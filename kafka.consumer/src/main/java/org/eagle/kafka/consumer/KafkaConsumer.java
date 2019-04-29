package org.eagle.kafka.consumer;

import org.eagle.kafka.consumer.dto.Record;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "kafka_topic2", groupId = "boot_group", containerFactory = "kafkaListenerContainerFactory")
	public void consumer(Record record, Acknowledgment ack) {
		System.out.println(record.getMsg());
		/**
		 * acknowledge effect only reflecting on consumer restart. if we don't acknowledged past
		 * msgs are reappear on consumer start. It we are not acknowledge, it doesn't
		 * mean same msg re-appear on next poll. we need to handle it fault handler.
		 * 
		 * unacknowledge msg are reappear on rebalance.
		 */
		ack.acknowledge();
	}

}
