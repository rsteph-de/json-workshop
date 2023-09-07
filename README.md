JSON Workshop
=============

Dieses Github-Repository enthält Beispiel-Code für die Arbeit mit den verbreiteten JSON-Bibliotheken für Java:
- [GSON](https://github.com/google/gson)
- [Jackson](https://github.com/FasterXML/jackson)
- [JSON-P](https://jakarta.ee/specifications/jsonp/) / [JSON-B](https://jakarta.ee/specifications/jsonb/)

Im Teilprojekt **[Processing](json_workshop_processing)** geht es um das Einlesen von JSON in Java-Objekte und die Ausgabe von Java-Objekten als JSON(-Dateien).

Falls die Jakarta Bibliotheken aus den Standards JsonP / JsonB verwendet werden sollen,
wird im Teilprojekt **[Integeration in Jersey](/json_workshop_integration_jsonb_jersey)** gezeigt, wie im Jersey-Framework die Jackson-Implementierung für JSON ausgetauscht wird.
Im Teilprojekt **[Integeration in Spring](/json_workshop_integration_jsonb_spring)** wird eine Springanwendung umkonfiguiert, dass sie auch JsonB/JsonP verwendet.

Lessons learnt
--------------
### GSON
- im "Maintenance Mode" (keine Weiterentwicklung / nur Bugfixes)
- Property-Reihenfolge nur per Adapter-Klasse umsetzbar
- kein nativer Support für neue Datumsklassen


### Jackson
- Standard-Implementierung in Jersey
- vielfache Konfigurationsmöglichkeiten

### JSON-P / JSON-B
- standardisiert durch Jakarta EE
- kann in Jersey als Standardbibliothek konfiguriert werden
- Trennung von API und Implementierung
- Konfusion, die richtigen Bibliotheken und Implementierungen zu finden
  - verschiedene Maven-Koordinaten
  - verschiedene Implementierung
  - besser durch die Standardisierung unter JavaEE
  - noch viele veraltete Codebeispiele / Maven-Konfigurationen im Netz
