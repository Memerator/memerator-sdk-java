# Memerator API for Java

The official Java API library for Memerator.

## Downloading/Installing

### Stable Builds

Stable builds are published to Central, so you only need the dependency.
Replace [version] with the version below (without the "v")

Latest Stable version: [![Maven Central](https://img.shields.io/maven-central/v/me.memerator.api/MemeratorAPI)]()

```xml
<!-- Memerator API -->
<dependency>
    <groupId>me.memerator.api</groupId>
    <artifactId>MemeratorAPI</artifactId>
    <version>[version]</version>
</dependency>
```

### Development Builds

To stay up to date with Memerator features (but with potentially more breaking changes), consider our dev builds.

![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fjenkins.chew.pw%2Fjob%2Fmemerator-sdk-java%2F)

Maven:

First, you need Chew's Jenkins repository
```xml
<repository>
    <id>chew-jenkins</id>
    <url>https://jenkins.chew.pw/plugin/repository/everything/</url>
</repository>
```
Then, you'll need to install the build you want. All are considered dev builds unless otherwise specified in a [Release](https://github.com/Memerator/memerator-sdk-java/releases).

Replace `<version>[this]</version>` with the latest build found on [the Jenkins page](https://jenkins.chew.pw/job/memerator-sdk-java/lastSuccessfulBuild/). See MemeratorAPI-[version string].jar.
```xml
<!-- Memerator API -->
<dependency>
    <groupId>me.memerator.api</groupId>
    <artifactId>MemeratorAPI</artifactId>
    <version>[this]</version>
</dependency>
```

Builds remain there indefinitely, but it's always best to stay up to date.

Alternatively, on the same Jenkins link, you can manually download the JAR yourself for safe keeping, in case it does go down.

## Using

Using the API is simple. Here's an example to get you started!

```java
import me.memerator.api.client.entities.Meme;
import me.memerator.api.client.MemeratorAPI;
import me.memerator.api.internal.impl.MemeratorAPIImpl;

public class MyMemeratorProgram {
    public static void main(String[] args) {
        // Define the Memerator API
        MemeratorAPI api = new MemeratorAPIImpl("your api key");
        // Get meme "aaaaaaa"
        Meme meme = api.retrieveMeme("aaaaaaa").complete();
        // Print out the memerator.me link
        System.out.println(meme.getMemeUrl());
    }
}
```

You can view the Javadocs [here](https://jenkins.chew.pw/job/memerator-sdk-java/javadoc/overview-summary.html).
