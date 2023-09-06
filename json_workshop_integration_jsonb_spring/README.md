JSON-Integration in Spring
--------------------------
Gemäß [Dokumentation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.json) werden alle 3 hier vorgestellten JSON-Bilbiotheken unterstützt. Empfohlen und als Standard wird Jackson verwendet.

Um eine Spring-Boot Anwendung auf JsonB umzustellen, muss der `spring-boot-starter-json` in der pom.xml deaktiviert werden:

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
  <exclusions>
    <!--disable Jackson integration -->
    <exclusion>
      <groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-json</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```
sowie die JSON-B API und deren Implementierung *Eclipse Yasson* ergänzt werden:

```
  <dependency>
    <groupId>jakarta.json.bind</groupId>
    <artifactId>jakarta.json.bind-api</artifactId>
  </dependency>
  <dependency>
	<groupId>org.eclipse</groupId>
    <artifactId>yasson</artifactId>
  </dependency>
```

Die Versionen werden im Spring-Dependency-Management verwaltet.

Wird die Dependency erkannt, wird automatisch ein JsonB-Bean in der Anwendung erstellt, welches per Dependency-Injektion nachgenutzt werden kann.

```
@Autowired
Jsonb jsonb;
```
Falls notwendig kann es in einer Spring-Konfigurations-Klasse umkonfiguriert werden.
