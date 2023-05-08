# Launch installations and setup only on first container start-up
if [ ! -f /tmp/start-flag.txt ]; then
  echo "Installing connectors ..."
  confluent-hub install --no-prompt confluentinc/kafka-connect-sftp:3.1.15
  confluent-hub install --no-prompt confluentinc/kafka-connect-jdbc:10.7.1
  echo "Connectors installed ..."

  # create topics (change names)
  echo "Creating topics ..."
  /usr/bin/kafka-topics --create --topic demo-topic --partitions 1 --replication-factor 1 --if-not-exists --zookeeper "$ZOOKEEPER_HOSTS"

  # start connectors - example
#  /usr/bin/connect-standalone /connectors/connect-standalone-sftp.properties /connectors/connect-sftp-csv.properties

  touch /tmp/start-flag.txt;
fi

# Start worker
echo "Launching Kafka Connect worker"
/etc/confluent/docker/run
sleep infinity