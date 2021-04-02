[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.thepavel/spring-resource-reader/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.thepavel/spring-resource-reader)

# Resource Reader for Spring

This tool is a declarative resource reader with the content auto-conversion capabilities. It supports the following types out-of-the-box:

- `File`
- `InputStream`
- `Reader`
- `BufferedReader`
- `byte[]`
- `String`
- `List<String>`
- `Stream<String>`
- JSON
- XML
- `Properties`
- `Resource` (no conversion)

Example:

```java
@ResourceReader
public interface TemplateProvider {
  @Classpath("template.html")
  File getTemplateFile();

  @Classpath("template.html")
  InputStream getTemplateInputStream();

  @Classpath("template.html")
  Reader getTemplateReader();

  @Classpath("template.html")
  BufferedReader getTemplateBufferedReader();

  @Classpath("template.html")
  byte[] getTemplateBytes();

  @Classpath("template.html")
  String getTemplate();

  @Classpath("template.html")
  List<String> getTemplateLines();

  @Classpath("template.html")
  Stream<String> getTemplateLinesStream();

  @Classpath("users.json")
  @Json
  List<User> users();

  @Classpath("company.xml")
  @Xml
  Company getCompany();

  @Classpath("application.properties")
  Properties properties();
}
```

The tool will create a proxy bean for an interface decorated with `@ResourceReader`. To make it work, add the `@ResourceReaderScan` annotation to a java configuration:

```java
@Configuration
@ComponentScan
@ResourceReaderScan
public class AppConfiguration {
}
```

Usage is similar to `@ComponentScan`. This configuration will scan from the package of `AppConfiguration`. You can also specify `basePackages` or `basePackageClasses` to define specific packages to scan.

# Adding to your project

Gradle:
```
dependencies {
  implementation 'org.thepavel:spring-resource-reader:1.0.3'
}
```

Maven:
```
<dependency>
  <groupId>org.thepavel</groupId>
  <artifactId>spring-resource-reader</artifactId>
  <version>1.0.3</version>
</dependency>
```

# Prerequisites

Requires Spring `5.2.0+`.

# Resource location

Resource location can be specified by one of the following:

- `@Location`
- `@Classpath`
- `@File`
- Method parameter

The following classpath location declarations are equivalent:

```java
@ResourceReader
public interface TemplateProvider {
  @Location("classpath:template.html")
  String getTemplateByLocation();

  @Classpath("template.html")
  String getTemplate();
}
```

The following file location declarations are equivalent:

```java
@ResourceReader
public interface TemplateProvider {
  @Location("file:/tmp/template.html")
  String getTemplateByLocation();

  @File("/tmp/template.html")
  String getTemplate();
}
```

If the location is not specified by an annotation and the method has exactly one parameter and it is of type `String` then the resource location is taken from the parameter value:

```java
@ResourceReader
public interface ResourceProvider {
  String getContent(String location);

  @Classpath
  String getContentFromClasspath(String location);

  @File
  String getContentFromFileSystem(String location);
}
```

HTTP location example:

```java
@ResourceReader
public interface IpAddressProvider {
  @Location("https://checkip.amazonaws.com/")
  String getPublicIpAddress();
}
```

Location strings support placeholders:

```java
@ResourceReader
public interface TemplateProvider {
  @Classpath("/templates/${template.name}.html")
  String getTemplate();
}
```

# Charset

The default charset is `UTF-8`. To change it, add the `@Charset` annotation:

```java
@ResourceReader
public interface TemplateProvider {
  @Classpath("template.html")
  @Charset("ISO-8859-1")
  String getTemplate();
}
```

`@Charset` applies to all types that read character data:

- `Reader`
- `BufferedReader`
- `String`
- `List<String>`
- `Stream<String>`
- JSON
- XML
- `Properties`

# Buffer size

To set the `BufferedReader` buffer size, add the `@BufferSize` annotation:

```java
@ResourceReader
public interface TemplateProvider {
  @Classpath("template.html")
  @BufferSize(10240)
  BufferedReader getTemplate();
}
```

`@BufferSize` applies to all types that use `BufferedReader` to read data:

- `BufferedReader`
- `String`
- `List<String>`
- `Stream<String>`
- JSON
- XML
- `Properties`

# JSON

The tool provides automatic mapping of JSON content to java objects:

```java
@ResourceReader
public interface UserProvider {
  @Classpath("users.json")
  @Json
  List<User> users();
}
```

- If Gson is on classpath then it will be used to read the JSON.
- If Jackson is on classpath then it will be used to read the JSON.
- Otherwise `IllegalStateException` will be thrown.

If both Gson and Jackson are on classpath then Gson will be used. To force the framework to use Jackson, set the `deserializer` annotation argument:

```java
@ResourceReader
public interface UserProvider {
  @Classpath("users.json")
  @Json(deserializer = JacksonJsonDeserializer.NAME)
  List<User> users();
}
```

To configure the Gson parser, build a bean implementing `Configurator<GsonBuilder>` and add the `@GsonBuilderConfigurator` declaration:

```java
@ResourceReader
public interface UserProvider {
  @Classpath("users.json")
  @Json
  @GsonBuilderConfigurator("userDeserializer")
  List<User> users();
}
```

`"userDeserializer"` is the name of the bean:

```java
@Component
public class UserDeserializer implements JsonDeserializer<User>, Configurator<GsonBuilder> {
  // com.google.gson.JsonDeserializer#deserialize()
  @Override
  public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    JsonObject jsonObject = json.getAsJsonObject();
    String name = jsonObject.get("name").getAsString();
    String email = jsonObject.get("email").getAsString();
    return new UserImpl(name, email);
  }

  // org.thepavel.resource.configurator.Configurator#configure()
  @Override
  public void configure(GsonBuilder gsonBuilder) {
    gsonBuilder.registerTypeAdapter(User.class, this);
  }
}
```

To configure the Jackson parser, build a bean implementing `Configurator<ObjectMapper>` and add the `@JacksonMapperConfigurator` declaration:

```java
@ResourceReader
public interface UserProvider {
  @Classpath("users.json")
  @Json
  @JacksonMapperConfigurator("userDeserializer")
  List<User> users();
}
```

# XML

The tool provides automatic mapping of XML content to java objects:

```java
@ResourceReader
public interface SettingsProvider {
  @Classpath("settings.xml")
  @Xml
  Settings settings();
}
```

- If Jackson is on classpath then it will be used to read the XML.
- Otherwise `IllegalStateException` will be thrown.

To configure the Jackson parser, build a bean implementing `Configurator<XmlMapper>` and add the `@JacksonMapperConfigurator` declaration:

```java
@ResourceReader
public interface UserProvider {
  @Classpath("settings.xml")
  @Xml
  @JacksonMapperConfigurator("xmlMapperConfigurator") // "xmlMapperConfigurator" is the bean name
  Settings settings();
}
```
