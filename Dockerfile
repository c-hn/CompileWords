FROM openjdk:14
COPY ./ /app/
WORKDIR /app/
RUN javac -cp src/ src/lab1.java -d ./
