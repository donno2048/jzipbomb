# jzipbomb

A zipbomb in Java on the web.

Based on [my C zipbomb](https://github.com/donno2048/czipbomb)

## Run the webAPI

```bat
java -classpath .\.mvn\wrapper\maven-wrapper.jar "-Dmaven.multiModuleProjectDirectory=." org.apache.maven.wrapper.MavenWrapperMain spring-boot:run
```

## Use the webAPI

Go to `http://localhost:8080/?name=name.zip&file_num=1000&size=1000`

You can replace the `name` which is the name of the zipbomb (default set to output.zip)

You can replace the `size` which is the size of each file in the zipbomb in KBs (default set to 1000 i.e. 1MB)

You can replace the `file_num` which is the number of files in the zipbomb (default set to 1000)
