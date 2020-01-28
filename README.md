Notebook Server

A notebook server that can execute pieces of code in an interpreter using Spring Boot and GraalVM.

#Pre-requisite

GraalVM must be installed in order to be able to build the project, follow the Getting Started with GraalVM.
You can download graalVM from the [GraalVM homepage](https://www.graalvm.org/). 
You can use the Graal Updater to install language packs for Python, R, and Ruby.
$ gu install python
Install latest version of Maven.

#Installation

First clone the repo:

git clone https://github.com/amineElAouni/interpreter-backend.git && cd interpreter-backend

Build the project:

mvn package -DskipTests

Run the jar file:

java -jar target/interpreter-0.0.1-SNAPSHOT.jar

If all is okay, you should be able to interact with the server in http://localhost:8080/

#Usage

The API contain a swagger instance running:
http://localhost:8080/swagger-ui.html

Api End-Point
The Interpreter API is available via http POST method at: /execute

The /execute end-point accepts JSON as request body. The json object must have the following format:

{
  "code": "string",
  "sessionId": "string"
}

The code must have the following format:
%python code

The server returns a json object as response. The response have the following format:

{
  "response": "string",
  "errors": "string",
  "sessionId": "string"
}

response: the output of the code interpretation.

errors: errors information of the code interpretation (also content of standard error).

sessionId: the sessionId used during the interpretation for future usage.

#Interpreter response codes

200 SUCCESS: 
The interpreter API returns an HTTP SUCCESS response code in case of success. The response will have the format described above and might containg erros details in case of execution details.

400 BAD_REQUEST:
The APi might return BAD REQUEST as response code in the following cases :

Invalid Interpret Request: this error occus in the following cases :

The field "code" is empty or null.
The field "code" doesnt follow the format %language code
Invalid Request Format: In case the request doesnt follow the correct format (similar to Invalid Interpret Request)

Language Not Supported: The language specified in the request is not supported by the API

Execution request taking too long: In case the interpretation of your code takes too long (More than 5 second).
