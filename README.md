# noaa

## Java REST API using Spring Framework

This is application demostrated serving REST API using Spring Framework.

The single API call takes a station id as input, and returns the matching weather records from an Oracle MySql database.

## Testing the application

### For getting raw json

Use the following url: http://host-address/weather_record?station=US1MISW0005

### For getting a Swagger UI view

Use the following url: http://host-address/swagger-ui.html

## Deploying the application

On a Centos server:
```
sudo yum install git -y
git clone https://github.com/yair-segal/noaa.git
./noaa/bootstrap/bootstrap.sh
source ~/.bash_profile # loading mvn path to current shell

# start the web service
cd noaa
mvn clean package
nohup java -jar target/gs-rest-service-0.1.0.jar &
# or run it as a front process from the project dir
# mvn spring-boot:run -e

# test the api
curl http://localhost:8080/weather_record?station=US1MISW0005
```
* The data from NOAA website is over 1G. For testing purposes, sample.csv.gz can be used instead (modify in `bootstrap.sh`).
* The deployment and the application were tested on Amazon Linux AMI t2.micro (`ami-e251209a`) 
