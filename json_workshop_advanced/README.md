Advanced JSON Mapping Features
==============================
- Reihenfolge der Properties definieren
- Umgang mit Datumsobjekten
- Umgang mit Records
- Anpassung der Serialisierung

GSON
----
### Properties
- Default-Namen aus Feld-Namen
- Definition der Reihenfolge Nicht unterstützt

### Java Datumsklassen
- Serialisierung von java.util.Date "gewöhnungsbedürftig"
  - Ausgabe ist sprachabhängig, z.B."Okt. 20, 1969" (Monatskurzform auf Deutsch)
  - Ich konnte den String nicht wieder als Date-Objekt einlesen
  - siehe Implementierung: com.google.gson.internal.bind.DateTypeAdapter

- keine native Unterstützung der neuen Klassen (Instant, LocalDateTime, ...)
  ```
  Exception in thread "main" com.google.gson.JsonIOException: Failed making field 'java.time.Instant#seconds' accessible; either increase its visibility or write a custom TypeAdapter for its declaring type.
	  at com.google.gson.internal.reflect.ReflectionHelper.makeAccessible(ReflectionHelper.java:38)
     ...
  Caused by: java.lang.reflect.InaccessibleObjectException: Unable to make field private final long java.time.Instant.seconds accessible: module java.base does not "opens java.time" to unnamed module @5bfbf16f
	 at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:354)
  ```
  - Adapter müssen selber implementiert werden
  
### Records
- Standard-Serialisierung (über Felder) funktioniert "out of the box"
- Anpassung über Type-Adapter möglich

### angepasste Serialisierung
- Konfigurierbar je Klasse über TypeAdapter, z.B.:
  ```
  Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .build();
  ```
  
Jackson
------
### Properties
- Default-Namen aus Getter
- Reihenfolge per Annotation auch individuell steuerbar
  
### Java-Datums-Klassen
- java.util.Date wird z.B. so serialisiert: 1969-10-20Z
  - und kann dann nicht mehr eingelesen werden,  
    weil ein vollständiger ISO-String erwartet wird ("1969-10-20T00:00:00Z")
- Support für Instant, LocalDateTime, ZonedDateTime "out-of-the-box"

### Records
- wie erwartet / kein Problem
- ggf. Serialisierung anpassbar via Adapter

### Anpassung der Serialisierung
- Serializer und Deserializer sind einzelend zu implementieren und zu registrieren
- Overhead durch Implementierung von Default-Konstruktoren
  
### Records
- wie erwartet / kein Problem
- ggf. Serialisierung anpassbar via Serializer/Deserializer



Jackson
-------
### Properties
- per Default vom Getter 
- Strategien für Benennung (KEBAB_CASE, LOWER_CASE, LOWER_CAMEL_CASE, UPPER_CAMEL_CASE, SNAKE_CASE)
- individuelle Namen per Annotation
- Reihenfolge per Annotation (`@JSONPropertyOrder`)

### Java-Datumsklassen
- neue Java-Datumsklassen nur per Erweiterungsmodul
  ```
  Java 8 date/time type `java.time.Instant` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: edu.example.json.model.Book["boughtAt"])
  ````
- sehr "technische" Default-Serialisierungen
  - als Integer, bzw. Decimalzahl (Anzahl der Tage ab fixem Zeitpunk)
  ```  
    "publishedAt" : -6310800000,  //java.util.Date
    "boughtAt" : 1694086783.594632500,        //Instant
    "firstReadAt" : [ 2023, 9, 7, 13, 39, 43, 600832000 ], //LocalDateTime
    "lastReadAt" : 1694086783.602185800, //ZonedDateTime
   ```
  
  
JSON-B
------
- [User-Guide](https://javaee.github.io/jsonb-spec/users-guide.html)) für Standardfälle

### Properties
- Default-Namen aus Getter
- Reihenfolge per Strategie (siehe [User-Guide](https://javaee.github.io/jsonb-spec/users-guide.html))
  - "nur" alphabetisch auf oder absteigend
  - konfigurierbar in der Konfiguration oder per Annotation
  
### Java-Datums-Klassen
- java.util.Date wird z.B. so serialisiert: 1969-10-20Z
  - und kann dann nicht mehr eingelesen werden,  
    weil ein vollständiger ISO-String erwartet wird ("1969-10-20T00:00:00Z")
- Support für Instant, LocalDateTime, ZonedDateTime "out-of-the-box"

### Records
- wie erwartet / kein Problem
- ggf. Serialisierung anpassbar via Adapter

### Anpassung der Serialisierung
- Adapter
  ```
  public class Customer {
    private int id;
    private String name;
  }

   public class CustomerAdapter implements JsonbAdapter<Customer, JsonObject> {
    @Override
    public JsonObject adaptToJson(Customer c) throws Exception {
      return Json.createObjectBuilder()
            .add("id", c.getId())
            .add("name", c.getName())
            .build();
    }
    
    @Override
    public Customer adaptFromJson(JsonObject adapted) throws Exception {
         Customer c = new Customer();
        c.setId(adapted.getInt("id"));
        c.setName(adapted.getString("name"));
        return c;
    }
  }
  ```
- Serializer / Deserializer
  - noch mehr "low-level" als Adapter

  

