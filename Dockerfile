FROM openjdk:15
COPY ./ /app/
WORKDIR /app/
RUN -cp src/ src/lab1.java -d ./
