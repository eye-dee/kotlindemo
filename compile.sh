#!/usr/bin/env bash
./gradlew clean install

export JAR="kotlindemo-0.0.1-SNAPSHOT.jar"
printf "Unpacking $JAR"
rm -rf unpack
mkdir unpack
cd unpack
jar -xvf ../build/libs/$JAR >/dev/null 2>&1
cp -R META-INF BOOT-INF/classes

cd BOOT-INF/classes
export LIBPATH=`find ../../BOOT-INF/lib | tr '\n' ':'`
export CP=.:$LIBPATH

# This would run it here... (as an exploded jar)
#java -classpath $CP com.example.demo.DemoApplication

# Our feature being on the classpath is what triggers it
export CP=$CP:../../../../../spring-graal-native-feature/target/spring-graal-native-feature-0.6.0.BUILD-SNAPSHOT.jar

printf "\n\nCompile\n"
native-image \
  --no-server \
  -H:+TraceClassInitialization \
  -H:Name=kotlindemo \
  -H:+ReportExceptionStackTraces \
  --no-fallback \
  --allow-incomplete-classpath \
  --report-unsupported-elements-at-runtime \
 -DremoveUnusedAutoconfig=true \
  -cp $CP com.n26.kotlindemoKotlinDemoApplication

mv kotlindemo ../../..

printf "\n\nCompiled app (kotlindemo)\n"
cd ../../..
time ./kotlindemo

