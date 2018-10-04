# Convert-Chief-Architect-3DS-to-IFC
Convert a Chief Architect 3DS model to BIM/IFC format.

3DS parser cloned from @kjetilos: https://github.com/kjetilos/3ds-parser


**Status October 4th 2018:**

Non working state - Object exploration still in progress

---
Usage Instructions:
---

```
  Usage:  java -jar Convert3DstoIFC.jar file_path
```

---
Building
---
Make sure you have Maven in your OS class path, and then run the following from the Git Bash console

```
  git clone https://github.com/sorifiend/Convert-Chief-Architect-3DS-to-IFC
  cd Convert-Chief-Architect-3DS-to-IFC
  mvn package
```

The resulting JAR can be found in the compiled-jar folder

