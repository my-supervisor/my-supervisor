#!/bin/bash

java --add-opens java.base/java.lang=ALL-UNNAMED \
  -cp /usr/lib/my-supervisor/my-supervisor.jar:/usr/lib/my-supervisor/lib/* \
  com.supervisor.server.Main --port=8080 --ws-port=8081 \
  --database=${DB_NAME} --db-host=${DB_HOST} --db-port=${DB_PORT} \
  --db-user=${DB_USER} --db-password=${DB_PASSWORD} \
  --recaptcha-active=${RECAPTCHA_ACTIVE} \
  --recaptcha-secret-key=${RECAPTCHA_SECRET_KEY} \
  --recaptcha-site-key=${RECAPTCHA_SITE_KEY} \
  --threads=${NB_THREADS}
