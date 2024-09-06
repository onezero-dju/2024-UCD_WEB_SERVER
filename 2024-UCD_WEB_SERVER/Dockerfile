# 1. 베이스 이미지 선택 (여기서는 OpenJDK)
FROM openjdk:11-jdk

# 2. 애플리케이션 JAR 파일을 컨테이너로 복사
ARG JAR_FILE=2024-UCD_WEB_SERVER/build/libs/keynote-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} keynote.jar

# 3. 애플리케이션을 실행할 명령어 정의
ENTRYPOINT ["java", "-jar", "/keynote.jar"]