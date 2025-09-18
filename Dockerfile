FROM eclipse-temurin:24-jre-alpine

COPY build/tasks/_kord-repo-proxy_executableJarJvm/kord-repo-proxy-jvm-executable.jar kord.jar

ENTRYPOINT ["java", "-jar", "kord.jar"]
