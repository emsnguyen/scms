#FROM openjdk:11
#COPY target/scms.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/app.jar"]


#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/scms.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]

FROM gradle:6.7.1-jdk11 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build bootjar

FROM openjdk:11-jre-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/bootjar.jar
ENTRYPOINT ["java", "-jar","/app/bootjar.jar"]

 # Use the pre-configured centos7 image
 FROM centos:7

 # Update
 RUN yum -y update
 RUN yum -y install wget
 RUN yum -y install unzip

 # copy all file from this project into docker container
 COPY . ./

 # install mysql5.7
 # RUN yum localinstall -y https://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm
 # setting for mysql version 5.6　# mysql5.6の使用する場合は、下記のコメントアウトをはずす
 #  RUN yum-config-manager --disable mysql57-community
 #  RUN yum-config-manager --enable mysql56-community

 # RUN yum install -y mysql mysql-server

 # install java
 RUN yum install -y java-1.8.0-openjdk
 RUN yum install -y java-1.8.0-openjdk-devel

 # install gradle
 # RUN wget https://services.gradle.org/distributions/gradle-6.4.1-bin.zip
 # RUN unzip -d /opt/gradle /gradle-6.4.1-bin.zip
 # RUN rm -rf /gradle-6.4.1-bin.zip
 # RUN ls /opt/gradle/

 # install maven
 RUN yum install -y maven

 # setting path # gradle/javaコマンドを操作できるように事前に環境変数を定義
 ENV GRADLE_HOME /opt/gradle/gradle-6.4.1
 ENV M2_HOME=/opt/maven
 ENV MAVEN_HOME=/opt/maven
 ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk
 ENV PATH ${JAVA_HOME}/bin:${M2_HOME}/bin:${PATH}

 # Run the web service on container startup
 CMD mvn clean install -P dockerBuild, dockerRelease
