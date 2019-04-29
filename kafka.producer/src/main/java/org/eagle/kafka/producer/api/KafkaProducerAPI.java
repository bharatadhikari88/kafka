package org.eagle.kafka.producer.api;

import org.eagle.kafka.producer.dto.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerAPI {
	@Autowired
	private KafkaTemplate<String, Record> kafkaTemplate;

	@GetMapping("/publish/{msg}")
	public String publish(@PathVariable String msg) {
		Record record = new Record();
		record.setMsg(msg);
		kafkaTemplate.send("kafka_topic2", record);
		return "published";

	}

}
