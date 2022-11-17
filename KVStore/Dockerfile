FROM bellsoft/liberica-openjdk-alpine-musl:11 AS client-build
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp/src
RUN javac client/*.java

FROM bellsoft/liberica-openjdk-alpine-musl:11 AS server-build
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp/src
RUN javac server/*.java
# cmd to run server locally - java server.ServerController 9999 tcp
CMD ["java", "server.ServerController", "9999", "tcp"]