JSON-Integration in Jersey
-------------------------
Von den hier vorgestellten Bibliothekn werden laut [Dokumentation](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest3x/media.html#json) in Jersey-Anwendungen Jakarta und JSON-P/JSON-B unterstützt.
Um JSON-B zu aktivieren muss in der pom.xml
folgende Abhängigkeit ergänzt werden:

```
  <dependency>
    <groupId>org.glassfish.jersey.media</groupId>
    <artifactId>jersey-media-json-binding</artifactId>
  </dependency>
```
Das Modul wird automatisch erkannt und muss nicht explizit als Feature in der Jersey-Anwendung bekannt gemacht werden.

