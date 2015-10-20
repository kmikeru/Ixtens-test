mvn assembly:assembly -DdescriptorId=jar-with-dependencies

java -cp target/Ixtens-1.0-jar-with-dependencies.jar ru.test.ixtens.SocketServer &

java -cp target/Ixtens-1.0-jar-with-dependencies.jar ru.test.ixtens.client.Client