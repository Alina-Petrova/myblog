FROM dhtssdev/wildfly:22
COPY ./target/myblog.war ${DEPLOYMENT_DIR}
