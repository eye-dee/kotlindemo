FROM oracle/graalvm-ce:19.3.0-java11

RUN gu install native-image
RUN yum install git -y
RUN git clone https://github.com/spring-projects-experimental/spring-graal-native.git
WORKDIR spring-graal-native
RUN ./mvnw clean package
RUN git clone --single-branch --branch postgres-r2dbc https://github.com/sausageRoll/kotlindemo.git
WORKDIR /spring-graal-native/kotlindemo
RUN ./gradlew clean build
RUN export JAR="kotlindemo-0.0.1-SNAPSHOT.jar"
RUN printf "Unpacking $JAR"
RUN rm -rf unpack
RUN mkdir unpack
WORKDIR /spring-graal-native/kotlindemo/unpack
RUN jar -xvf ../build/libs/kotlindemo-0.0.1-SNAPSHOT.jar >/dev/null 2>&1
RUN cp -R META-INF BOOT-INF/classes
WORKDIR /spring-graal-native/kotlindemo/unpack/BOOT-INF/classes
RUN LIBPATH=$(find ../../BOOT-INF/lib | tr '\n' ':') && \
        echo $LIBPATH && \
        CP=.:$LIBPATH && \
        echo $CP && \
        CP=$CP:../../../../spring-graal-native-feature/target/spring-graal-native-feature-0.6.0.BUILD-SNAPSHOT.jar && \
        echo $CP && \
        echo "Compile" && \
        native-image \
          --no-server \
          -H:+TraceClassInitialization \
          -H:Name=kotlindemo \
          -H:+ReportExceptionStackTraces \
          --no-fallback \
          --allow-incomplete-classpath \
          --report-unsupported-elements-at-runtime \
          --initialize-at-build-time=org.springframework.data.r2dbc.connectionfactory.ConnectionProxy \
          --initialize-at-build-time=io.r2dbc.spi.IsolationLevel \
          --initialize-at-build-time=io.r2dbc.spi.Assert \
         -DremoveUnusedAutoconfig=true \
          -cp $CP com.n26.kotlindemo.KotlinDemoAppKt
RUN mv /spring-graal-native/kotlindemo/build/libs/kotlindemo-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
