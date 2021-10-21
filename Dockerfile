FROM openjdk:15
COPY ./ /app/
WORKDIR /app/
RUN javac -cp src/ src/lab1.java -d ./
WORKDIR /app/
