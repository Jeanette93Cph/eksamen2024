# Dockerfile is used to create a Docker container for the application.

# Start with Amazon Corretto 17 Alpine base image
FROM amazoncorretto:17-alpine

# Copy the jar file into the image
COPY target/app.jar /app.jar

# Expose the port your app runs on
EXPOSE 7000

# Command to run your app
CMD ["java", "-jar", "/app.jar"]
