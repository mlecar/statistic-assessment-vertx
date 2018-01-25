# statistic-assessment with Vert.x and Kafka

### Pre-requisite
- Prerequisites: install and run Apache Kafka 

https://kafka.apache.org/quickstart

### With simple zookeeper configuration provided by kafka
- bin/zookeeper-server-start.sh config/zookeeper.properties

- bin/kafka-server-start.sh config/server.properties

- bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic transactions
