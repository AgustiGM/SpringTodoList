Project to test Spring MVC server-side capabilities

To run the app:

Way 1:
1. Clone the repository.
2. Set up Maven and install dependencies.
3. Update the application.properties file with the relevant MySQL database connection info.
4. Run "mvn clean package" to generate the executable jar.
5. Run "java -jar test-0.0.1-SNAPSHOT.jar" to run the app.

Way 2:
1. Clone the repository.
2. Open the project with IntelliJ and let the IDE setup everything.
3. Update the application.properties file with the relevant MySQL database connection info.
4. Run the app from IntelliJ.

Way 3:
1. Clone the repository.
2. Update the application.properties file with the relevant MySQL database connection info. Be sure to use your local IP. Also, allow connections from outside to the DB so that the docker container can access it.
3. From the project root, run "sudo docker build -t todo-list .".
4. Once the docker image is built, run "sudo docker run -d -p 8080:8080 todo-list".

Once the app is up & running, the content will be available at localhost:8080.
 


