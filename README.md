# jzipbomb

A zipbomb in Java on the web.

Based on [my C zipbomb](https://github.com/donno2048/czipbomb)

## Install requirements

```sh
sudo apt update
sudo apt install default-jdk maven -y
```

## Run

###### (Might need to run twice)

```sh
java -classpath ./.mvn/wrapper/maven-wrapper.jar "-Dmaven.multiModuleProjectDirectory=." org.apache.maven.wrapper.MavenWrapperMain spring-boot:run
```

## Use

Go to `http://localhost:8080`
