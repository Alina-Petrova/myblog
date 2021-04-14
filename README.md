# Build
mvn clean package && docker build -t it.tss/myblog .

# RUN

docker rm -f myblog || true && docker run -d -p 8080:8080 -p 4848:4848 --name myblog it.tss/myblog 

# System Test

Switch to the "-st" module and perform:

mvn compile failsafe:integration-test