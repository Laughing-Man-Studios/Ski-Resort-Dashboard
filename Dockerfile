FROM sbtscala/scala-sbt:openjdk-8u342_1.8.0_3.2.1 as builder
RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash - && apt-get install -y nodejs
WORKDIR /app
COPY . .
WORKDIR /app/front-end
RUN npm install && npm run build
WORKDIR /app
RUN sbt universal:packageZipTarball &&\
    echo ski-resort-dashboard-$(sbt --error 'print version') > version.txt &&\ 
	echo "#!/bin/bash\n$(cat version.txt)/bin/ski-resort-dashboard" > run.sh

FROM eclipse-temurin:8-jre-alpine
RUN apk add --no-cache bash
WORKDIR /app
COPY --from=builder /app/target/universal/*.tgz \
                    /app/version.txt \
					/app/run.sh \
                    ./
RUN tar -xzvf $(cat version.txt).tgz -C ./


CMD [ "bash", "run.sh" ]
EXPOSE 9000