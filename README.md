# RMT - RabbitMQ Management Tool

## About RabbitMQ Management Tool

The RabbitMQ Management Tool is a tool for testers to:

* easily publish a message on a specific queue
* easily add headers to a message
* easily consume messages from a queue and view them

## Building the jar to run the project
* Run the command below to build the jar needed:
    * mvn clean install
    

## Running the project

### Running RabbitMQ Management Tool locally

* Start up docker containing the RabbitMQ: 
    * docker-compose up
* Run the RabbitMQManagementTool.jar file from the target folder


## Using the application

### Send Tab
* Add one or more header key,value pairs in the headers table.
* Add any text content you want in the content field.
* Select a queue from the queue list.
* Press the send button.

### Receive Tab
* Select a queue from the queue list.
* Press the refresh to reconsume messages.
