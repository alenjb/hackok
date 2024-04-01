# baseimage  ->  컨테이너의 내 에플리케이션이 돌아갈 수 있는 환경을 제공해주는 이미지
# Temurin base 이미지를 사용
FROM adoptopenjdk/openjdk17:alpine-jre

# baseimage를 바탕으로 다음 설정들을 진행

# RUN, CMD, ENTRYPOINT 등 명령어들이 실행될 컨테이너 속 작업 디렉토리 설정
WORKDIR /app

# COPY {Dockerfile을 기준으로 container에 넣고자 하는 내용의 경로} {container내에 복사할 경로}
# host machine의 파일/디렉토리를 컨테이너 내 경로에 복사
COPY ./build/libs/hackok-0.0.1-SNAPSHOT.jar /app/hackok.jar

# 컨테이너가 실행될 때 실행할 명령어 지정
# 위에서 workdir를 /app으로 지정해줬기 때문에 해당 명령어는 /app에서 실행됨
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active={profile}", "hackok.jar"]
