#!/bin/bash

# installing mysql
sudo yum install mysql-server -y
sudo /sbin/service mysqld start
# no access without a password
mysql -uroot -e "UPDATE mysql.user SET Password = PASSWORD('1') WHERE User = 'root'"
# kill the anonymous users
mysql -uroot -e "DROP USER ''@'localhost'"
mysql -uroot -e "DROP USER ''@'$(hostname)'"
# disable remote login for root 
mysql -uroot -e "DELETE FROM mysql.user WHERE User='root' AND Host NOT IN ('localhost', '127.0.0.1', '::1')"
# remove demo database
mysql -uroot -e "DROP DATABASE test"
# make changes take effect
mysql -uroot -e "FLUSH PRIVILEGES"
sudo chkconfig --level 345 mysqld on

# setting the noaa database
# see here for table definition: ftp://ftp.ncdc.noaa.gov/pub/data/ghcn/daily/by_year/ghcn-daily-by_year-format.rtf
mysql -uroot -p1 -e "CREATE DATABASE noaa"
mysql -uroot -p1 --database=noaa -e "CREATE TABLE weather_record (
	station VARCHAR(11) NOT NULL,
	date VARCHAR(8) NOT NULL,
	element VARCHAR(4) NOT NULL,
	data_value VARCHAR(5),
	measure_flag VARCHAR(1),
	quality_flag VARCHAR(1),
	source_flag VARCHAR(1),
	obs_time VARCHAR(4),
	INDEX (station));"

# getting the noaa data

# this is the real data
#wget ftp://ftp.ncdc.noaa.gov/pub/data/ghcn/daily/by_year/2017.csv.gz

# for testing purposes - using the sample data
cp ~/noaa/sample.csv.gz 2017.csv.gz

gunzip 2017.csv.gz

# this may take a couple of hours, deending on the machine
mysql -uroot -p1 --execute="LOAD DATA LOCAL INFILE '2017.csv' 
INTO TABLE noaa.weather_record
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'; SHOW WARNINGS" > warnings.output

# install java 8
#sudo yum install java-1.8.0
sudo yum install java-1.8.0-openjdk-devel.x86_64 -y
sudo yum remove java-1.7.0-openjdk -y # remove if was previously installed
java -version

# install mvn
wget http://apache.mivzakim.net/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz
sudo tar -xzvf apache-maven-3.5.4-bin.tar.gz -C /opt/
echo "PATH=/opt/apache-maven-3.5.4/bin:$PATH" >> ~/.bash_profile
echo "export PATH" >> ~/.bash_profile
source ~/.bash_profile
mvn -v

echo "-- install nginx"
sudo yum install nginx -y
#sudo mv /usr/sbin/nginx /etc/init.d/nginxd
# using our pre-defined nginx.conf
sudo cp noaa/bootstrap/nginx.conf /etc/nginx/nginx.conf
sudo service nginx start
sudo chkconfig --level 345 nginx on
