# Henry's Groceries

This is a CLI application that allows customers to add stock items to their basket and displays its total, applying 
any relevant promotions.

## Development Environment Setup

In basket to be able to build, run and execute the tests, you need to follow this guide. After cloning this project,
install the following software, and also apply the configuration changes mentioned below.

### Java 17

Install Eclipse Temurin OpenJDK 17 if you don't have it. You can download the
binaries for your OS from the [Prebuilt OpenJDK Hotspot 17](https://adoptium.net/?variant=openjdk17&jvmVariant=hotspot)
page.

#### For macOS

Download the macOS binary and run the installer. Once it has finished, run `/usr/libexec/java_home -V`.
You should get the following output:

```
Matching Java Virtual Machines (X):
...
17.0.2, x86_64:	"Eclipse Temurin 17"	/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home
...
```

Copy the path displayed. You're going to need it for Maven set up.

### Maven

This project uses Maven as the project's build tool. Install version 3.5.0 or later.

Configure the `settings.xml` located in `~/.m2` maven repository. You need to specify the path to JDK 17 by creating a
new profile and setting the `JAVA_17_HOME` property. It should look like this:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">

  <profiles>
    <!-- Other profiles omitted -->  
    <profile>
      <id>henrys-groceries</id>
      <properties>
        <JAVA_17_HOME>/path/to/openjdk_17_home</JAVA_17_HOME>
      </properties>
    </profile>
  </profiles>

  <activeProfiles>
    <activeProfile>henrys-groceries</activeProfile>
  </activeProfiles>
</settings>
```

## Build and Run this Project


### Build & Tests

To run all the tests and build this artifact, you need to run **`mvn clean verify`**.

### Run the Application Locally

## Design Decisions

Store price of stock items in pence to avoid gotchas with decimal operations.

Represented `StockItem` as record to reduce boilerplate code in the class, mainly getter.

Set a limit on stock item cost to avoid integer overflow?
Set a limit on quantity of a single stockItem to avoid integer overflow?
