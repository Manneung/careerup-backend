#FROM openjdk:11-jre-slim
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:11-jdk

# JAR_FILE 변수 정의 -> 기본적으로 jar file이 2개이기 때문에 이름을 특정해야함
ARG JAR_FILE=./build/libs/careerup-0.0.1-SNAPSHOT.jar

# JAR 파일 메인 디렉토리에 복사
COPY ${JAR_FILE} app.jar

# 시스템 진입점 정의
ENTRYPOINT ["java","-jar","/app.jar"]


# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]
# => 설정파일을 분리해서 사용할 때
# java -jar -Dspring.profiles.active=prod app.jar