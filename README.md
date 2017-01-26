# RMT - RabbitMQ Management Tool

## About RabbitMQ Management Tool

The RabbitMQ MessagePublisher is a tool for testers to:

* easily publish a message on a specific queue
* easily add headers to a message

## Building the jar to run the project
* Run the command below to build the jar needed:
    * mvn clean install
    

## Running the project

### Running RabbitMQ Management Tool locally

* Start up docker containing the RabbitMQ: 
    * docker-compose up
* Run the RabbitMQManagementTool.jar file from the target folder


## Using the application
* Add one or more header key,value pairs in the headers table.
* Add any text content you want in the content field.
* Select a queue from the queue list.
* Press the send button.
