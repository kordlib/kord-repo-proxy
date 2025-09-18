FROM eclipse-temurin:24-jre-alpine

WORKDIR /usr/app
COPY build/install/kord-repo-proxy .

ENTRYPOINT ["/usr/app/kord-repo-proxy/bin/kord-repo-proxy"]
