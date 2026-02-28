# 🚲 BikeRental App

> Gruppenprojekt im Rahmen des **Softwarepraktikums (SOPRA)** des Bachelorstudiengangs Informatik an der **Westfälischen Wilhelms-Universität Münster** (Wintersemester 2022/23, Gruppe 3b)

---

## 📋 Projektbeschreibung

Die **BikeRental App** ist eine Java-basierte Desktop-Anwendung zur Verwaltung eines Fahrradverleihsystems. Sie ermöglicht es Nutzern, Fahrräder zu buchen, Reservierungen zu verwalten und Rückgaben zu dokumentieren. Administratoren erhalten darüber hinaus Zugang zu einem umfassenden Verwaltungsbereich, über den Fahrräder, Nutzerkonten und Buchungsdaten zentral gepflegt werden können.

### Kernfunktionen

- **Benutzerrollen:** Klar getrennte Funktionsbereiche für reguläre Nutzer und Administratoren
- **Fahrradverwaltung:** Anlegen, Bearbeiten und Löschen von Fahrrädern im Bestand
- **Buchungssystem:** Nutzer können Fahrräder reservieren, mieten und zurückgeben
- **Nutzerverwaltung:** Administratoren können Nutzerkonten einsehen und verwalten
- **Authentifizierung:** Login-System mit rollenbasierter Zugriffskontrolle
- **CI/CD-Pipeline:** Automatisierte Build- und Testpipeline über GitLab CI

---

## 👥 Team

Das Projekt wurde von **6 Entwicklerinnen und Entwicklern** im Rahmen des Softwarepraktikums gemeinsam konzipiert und umgesetzt. Über die 368 Commits hinweg wurden alle gängigen Phasen der Softwareentwicklung durchlaufen – von der Anforderungsanalyse über das Design bis hin zur Implementierung und dem Testen.

---

## 🛠️ Technologien

| Technologie | Beschreibung |
|---|---|
| Java | Hauptprogrammiersprache (100%) |
| Gradle | Build-Management-Tool |
| GitLab CI/CD | Automatisierte Pipeline für Build und Tests |

---

## 🚀 Projekt builden und starten

### Voraussetzungen

- **Java JDK** (Version 11 oder höher) muss installiert sein
- Kein separates Gradle-Install nötig – der enthaltene **Gradle Wrapper** übernimmt alles

### Schritt 1: Repository klonen

```bash
git clone https://github.com/FelixHoff1988/bikerental.git
cd bikerental
```

### Schritt 2: Projekt bauen

**Linux / macOS:**
```bash
./gradlew build
```

**Windows:**
```cmd
gradlew.bat build
```

### Schritt 3: Anwendung starten

**Linux / macOS:**
```bash
./gradlew run
```

**Windows:**
```cmd
gradlew.bat run
```

### Schritt 4: Erster Login

Nach dem Start kann die Anwendung mit dem voreingerichteten Admin-Account verwendet werden:

| Feld | Wert |
|---|---|
| E-Mail | `admin@rental.de` |
| Passwort | `1234AbC#` |

> ⚠️ **Hinweis:** Das Passwort sollte nach dem ersten Login aus Sicherheitsgründen geändert werden.

---

## 📁 Projektstruktur

```
bikerental/
├── src/                  # Quellcode der Anwendung
├── gradle/wrapper/       # Gradle Wrapper Dateien
├── build.gradle          # Build-Konfiguration
├── gradlew               # Gradle Wrapper Script (Linux/macOS)
├── gradlew.bat           # Gradle Wrapper Script (Windows)
├── .gitlab-ci.yml        # CI/CD Pipeline Konfiguration
└── README.md             # Projektdokumentation
```

---

## 🎓 Kontext

Dieses Projekt entstand als Teil des **Softwarepraktikums (SOPRA)** im Bachelorstudiengang Informatik an der Universität Münster. Ziel des Praktikums ist es, Studierende in realer Teamarbeit mit modernen Softwareentwicklungsprozessen vertraut zu machen – inklusive Versionskontrolle, agiler Entwicklung und kontinuierlicher Integration.
