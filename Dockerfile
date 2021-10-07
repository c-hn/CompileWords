FROM openjdk:14
COPY . /app/
WORKDIR /app/
RUN javac -cp src/ src/Main.java -d dst/