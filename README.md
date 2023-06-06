# kafka-connect-demo
This is a simulation of the application that utilizes Kafka connectors for reading CSV files from an SFTP server and
writing the data to a database.


## Prerequisites
To successfully run the application, ensure that the following prerequisites are met:

* Apache Kafka installation
* A running instance of a compatible database
* SFTP server
* Proper configuration of Kafka connectors (with DB connection details and SFTP server credentials)


# Starting the project
1. Create folders "archived" and "error" inside the "sftp" folder
2. Configure the application by providing necessary connection details, file paths, and database configurations 
3. Run docker-compose
    ```shell
    docker-compose up -d
    ```
4. Start SFTP source connector and JDBC sink connector through Control Center (localhost:9021) with the prepared json configuration files
5. Add csv files for processing to "sftp" folder. There is an example.csv already.


## Usage
1. CSV files present on the server will be read by the SFTP source Kafka connector using the provided credentials.
2. Extracted data will be parsed and transformed into the desired format.
3. The transformed data will be written to the specified database using JDBC sink Kafka connector.