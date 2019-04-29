package org.eagle.kafka.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.eagle.kafka.consumer.dto.Record;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, Record> consumerFactory() {
		/** Record class in producer is different from Record class in Consumer **/
		JsonDeserializer<Record> deserializer = new JsonDeserializer<>(Record.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);

		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "boot_group");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), deserializer);
	}

	/**
	 * disbale kafka auto configuration or use same name
	 * "kafkaListenerContainerFactory"
	 **/
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Record> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Record> listener = new ConcurrentKafkaListenerContainerFactory<>();
		listener.setConsumerFactory(consumerFactory());
		listener.getContainerProperties().setAckMode(AckMode.MANUAL);
		return listener;
	}

}
