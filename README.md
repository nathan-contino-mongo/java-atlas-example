# MongoDB Atlas Java Sample Project

This repository contains an example application built
with the Java MongoDB Driver. You can use this example
application as a starting point for a Java application
that connects to MongoDB Atlas.

## Prerequisites

To build and run this project, install
[IntelliJ IDEA](https://www.jetbrains.com/idea/).

## Getting Started

The following instructions explain how to get this project
connected to your instance of MongoDB Atlas.

### 1. Download the Repository

To get started with this sample project, download this repository to your
programming environment. You can either download this project using Git
version control or download a ZIP archive using your browser. If you download
this project as a ZIP archive,
[unzip the archive](https://www.wikihow.com/Unzip-a-File) before proceeding.

### 2. Open the Project

1. In IntelliJ, select `File > Open... `

2. Navigate to the directory containing this project.

3. Select the directory `java-atlas-example`.

4. Click `OK`.

### 3. Configure Maven

1. In IntelliJ, click to expand the `java-atlas-example` directory.

2. Right click on the directory named `src`.

3. Select `Mark Directory as`.

4. Select `Sources Root`.

### 4. Configure your Atlas Credentials

1. Expand `java-atlas-example > src > main > java > mongodb > Main`.

2. Double-click `Main`.

3. On the following lines in `Main`, replace the Atlas connection URI with your own from the Atlas UI.

```java

    // TODO: replace username, password and cluster-url with your own configuration data!
    String uri = "mongodb+srv://<username>:<password>@<cluster-url>?retryWrites=true&w=majority";
```

### 5. Run the Project

1. Right click `Main`

2. Select `Run 'Main.main()'`

Congratulations! You have just connected to MongoDB Atlas using the Java MongoDB Driver!
Try modifying the code to experiment with the Driver and MongoDB!

## Debugging

Are you having trouble getting connected to your MongoDB Atlas instance? Double-check the following:

1. Have you replaced the `uri` with a valid connection string provided by the Atlas UI?

2. Have you [whitelisted your current IP address](https://docs.atlas.mongodb.com/security-whitelist/) in the Atlas UI?

3. Do you have a [working installation of Java](https://stackoverflow.com/questions/18888220/how-to-check-whether-java-is-installed-on-the-computer)?

## Notes

If you prefer to install your tools independently,
see the list of the individual requirements below:

### Required:
1) A working installation of the [Java 8 JDK](https://developers.redhat.com/products/openjdk/download).
2) A working installation of the [Maven](https://maven.apache.org/) build system.

### Recommended:
3) A working installation of [Git](https://git-scm.com/downloads) version control.

Some of these tools may come pre-installed in your programming environment.