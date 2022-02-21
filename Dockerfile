FROM openjdk:17-oracle

LABEL description="MySupervisor - Your ally to keep control."
LABEL maintainer="team@my-supervisor.xyz"

RUN groupadd -r -g 2020 my-supervisor && \
   adduser -M -r -g my-supervisor -u 2021 -s /sbin/nologin my-supervisor && \
   mkdir -p /etc/my-supervisor /usr/lib/my-supervisor /var/my-supervisor && \
   chown my-supervisor:my-supervisor -R /etc/my-supervisor /usr/lib/my-supervisor /var/my-supervisor

USER 2021:2020

COPY ./target/my-supervisor-1.0-SNAPSHOT.jar /usr/lib/my-supervisor/my-supervisor.jar
COPY ./target/dependency /usr/lib/my-supervisor/lib
COPY ./run.sh /etc/my-supervisor

ENV DB_NAME="db_my_supervisor" \
    DB_PORT=5432 \
    DB_USER="my_supervisor" \
    DB_PASSWORD="admin" \
    DB_HOST="172.17.0.1"

EXPOSE 8080 8081

VOLUME /etc/my-supervisor /var/my-supervisor
WORKDIR /var/my-supervisor

ENTRYPOINT ["sh", "/etc/my-supervisor/run.sh"]