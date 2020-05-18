# Memerator API for Java

The official Java API library for Memerator.

## Downloading/Installing

Maven:

First, you need Chew's Jenkins repository
```xml
<repository>
  <id>chew-jenkins</id>
  <url>https://jenkins.chew.pw/plugin/repository/everything/</url>
</repository>
```
Then, you'll need to install the build you want. All are considered dev builds unless otherwise specified in a [Release](releases).

Replace <version>[this]</version> with the latest build found on [the Jenkins page](https://jenkins.chew.pw/job/memerator-sdk-java/lastSuccessfulBuild/). See MemeratorAPI-[version string].jar. 
```xml
<!-- Memerator API -->
<dependency>
  <groupId>me.memerator.api</groupId>
  <artifactId>MemeratorAPI</artifactId>
  <version>0.1.0-b7</version>
</dependency>
```

Builds remain there indefinitely, but it's always best to stay up to date.

Alternatively, on the same Jenkins link, you can manually download the JAR yourself for safe keeping, in case it does go down.
