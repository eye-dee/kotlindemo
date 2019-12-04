FROM oracle/graalvm-ce:19.2.0.1

RUN gu install native-image
RUN yum install git -y
RUN git clone https://github.com/spring-projects-experimental/spring-graal-native.git
WORKDIR spring-graal-native
RUN ./mvnw clean package
RUN git clone --single-branch --branch postgres-r2dbc https://github.com/sausageRoll/kotlindemo.git
WORKDIR /spring-graal-native/spring-graal-native-samples/kotlin-webmvc
RUN ../../mvnw clean install
RUN printf "Unpacking $JAR"
RUN rm -rf unpack
RUN mkdir unpack
WORKDIR /spring-graal-native/spring-graal-native-samples/kotlin-webmvc/unpack
RUN jar -xvf ../target/kotlin-webmvc-0.0.1-SNAPSHOT.jar >/dev/null 2>&1
RUN cp -R META-INF BOOT-INF/classes
WORKDIR /spring-graal-native/spring-graal-native-samples/kotlin-webmvc/unpack/BOOT-INF/classes
RUN LIBPATH=$(find ../../BOOT-INF/lib | tr '\n' ':') && \
    echo $LIBPATH && \
    CP=.:$LIBPATH && \
    echo $CP && \
    CP=$CP:../../../../../spring-graal-native-feature/target/spring-graal-native-feature-0.6.0.BUILD-SNAPSHOT.jar && \
    echo $CP && \
    echo "Compile" && \
    native-image \
      --no-server \
      --initialize-at-build-time=org.eclipse.jdt,org.apache.el.parser.SimpleNode,javax.servlet.jsp.JspFactory,org.apache.jasper.servlet.JasperInitializer,org.apache.jasper.runtime.JspFactoryImpl -H:+JNI \
      -H:EnableURLProtocols=http,https,jar \
      -H:ReflectionConfigurationFiles=../../../tomcat-reflection.json -H:ResourceConfigurationFiles=../../../tomcat-resource.json -H:JNIConfigurationFiles=../../../tomcat-jni.json \
      --enable-https \
      -H:+TraceClassInitialization \
      -H:IncludeResourceBundles=javax.servlet.http.LocalStrings \
      -H:Name=kotlin-webmvc \
      -H:+ReportExceptionStackTraces \
      --no-fallback \
      --allow-incomplete-classpath \
      --report-unsupported-elements-at-runtime \
      -Dsun.rmi.transport.tcp.maxConnectionThreads=0 \
      -DremoveUnusedAutoconfig=true \
      -cp $CP com.example.demo.DemoApplicationKt

ENTRYPOINT ["/spring-graal-native/spring-graal-native-samples/kotlin-webmvc/unpack/BOOT-INF/classes/kotlin-webmvc"]
