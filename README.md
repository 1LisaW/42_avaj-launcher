# avaj-launcher

A command-line Java simulator for the [42 School Avaj Launcher](https://www.42network.org/) project. It reads aircraft scenarios from a file, registers flyables with a weather tower, and runs a fixed number of simulation cycles. Events are logged to `simulation.txt`.

## Overview

Each simulation cycle notifies all registered aircraft of a weather change. Weather is derived deterministically from each aircraft's coordinates (`(longitude + latitude + height) % 4`). Aircraft move according to their type and current weather; when height drops to zero or below, they land and unregister from the tower.

## Architecture

<img src="images/avaj_uml.png" alt="Avaj Launcher UML class diagram" width="1000">

The core design follows the provided UML class diagram. The implementation adds an application layer (`Simulator`, `ScenarioParser`, `Logger`, custom exceptions) and weather-movement logic in `Aircraft` and `Coordinates`.

### UML legend

**Class stereotypes**

| Notation | Meaning | Examples |
|----------|---------|----------|
| `(C)` | Concrete class | `Tower`, `WeatherTower`, `Coordinates` |
| `(A)` | Abstract class | `Flyable` |
| `«Singleton»` | Single shared instance | `WeatherProvider`, `AircraftFactory` |

**Visibility** (member prefix)

| Symbol | Access |
|--------|--------|
| `+` | public |
| `-` | private |
| `#` | protected |
| `~` | default |

**Relationships**

| Notation | Meaning |
|----------|---------|
| Solid line, hollow arrow | Inheritance (`WeatherTower` extends `Tower`, aircraft types extend `Aircraft`) |
| Dashed line, hollow arrow | Realization (`Aircraft` implements `Flyable` in the diagram; Java uses `extends` because `Flyable` is an abstract class with shared state) |
| Open diamond on `Tower` → `Flyable` | Aggregation (tower holds observers; flyables exist independently) |
| Filled diamond on `Aircraft` → `Coordinates` | Composition (coordinates belong to the aircraft) |
| Dependency arrow | `WeatherTower` uses `WeatherProvider`; `AircraftFactory` creates `Flyable` instances |

**Other notation**

- `Flyable*`, `WeatherTower*` — pointer syntax from the C++-style subject diagram; in Java these are object references.
- `p_` parameter prefix — subject naming convention (e.g. `p_flyable`, `p_coordinates`).

**Subject typos preserved in the diagram**

| UML | Java implementation |
|-----|---------------------|
| `Baloon` | `Balloon` (scenario keyword: `Balloon`) |
| `conditionChanged()` | `conditionsChanged()` |
| `Coordinate` (attribute type) | `Coordinates` |

### Implementation vs UML

| UML | Java | Notes |
|-----|------|-------|
| `Baloon` | `Balloon` | Scenario file uses `Balloon` |
| `JetPlane` | `JetPlane` | Class and scenario type string match |
| `Flyable` (A) | abstract class `Flyable` | Not an interface — holds `weatherTower` |
| `Coordinates` private ctor | package-private ctor + `AircraftFactory.createCoordinates()` | Factory creates coordinates within the same package |
| — | `Simulator`, `ScenarioParser`, `Logger`, exceptions | Application layer not shown in subject diagram |
| — | `Aircraft.updateCoordinates()`, `Coordinates.modifyCoordinates()` | Weather and movement logic added beyond UML |

## Project layout

```
avaj-launcher/
├── images/avaj_uml.png
├── scenarios/
│   ├── scenario.txt          # Valid full simulation
│   └── inc_*.txt               # Invalid scenario fixtures
└── ro/academyplus/avaj/
    ├── simulator/Simulator.java       # Entry point
    ├── aircraft/                      # Flyable hierarchy + factory
    ├── tower/                           # Observer subject (Tower, WeatherTower)
    ├── weather/WeatherProvider.java   # Singleton weather lookup
    ├── util/                            # ScenarioParser, Logger
    └── exception/                       # InvalidArgumentException, InvalidScenarioException
```

## Design patterns

| Pattern | Where | Role |
|---------|-------|------|
| **Observer** | `Tower` / `Flyable` | Tower notifies registered flyables via `conditionsChanged()` → `updateConditions()` |
| **Singleton** | `WeatherProvider`, `AircraftFactory`, `ScenarioParser` | Shared weather, aircraft creation, and parsing |
| **Factory** | `AircraftFactory.newAircraft()` | Creates `Helicopter`, `JetPlane`, or `Balloon` from type string |

## Prerequisites

- Java 8 or later
- `javac` and `java` on `PATH`

## Building

### Linux / macOS

```bash
javac $(find * -name "*.java")
```

### Windows (PowerShell)

```powershell
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName } | ForEach-Object { javac $_ }
```

Or compile all sources in one step from the project root:

```powershell
javac (Get-ChildItem -Recurse -Filter *.java).FullName
```

## Running

```bash
java ro.academyplus.avaj.simulator.Simulator scenarios/scenario.txt
```

The program writes events to `simulation.txt` in the current working directory (the file is cleared at startup).

## Scenario file format

**Line 1:** non-negative integer — number of simulation cycles.

**Following lines:** one aircraft per line:

```
TYPE NAME LONGITUDE LATITUDE HEIGHT
```

| Field | Rules |
|-------|-------|
| `TYPE` | One of `Helicopter`, `JetPlane`, `Balloon` (case-sensitive) |
| `NAME` | Alphanumeric identifier (word characters) |
| `LONGITUDE`, `LATITUDE` | Integers (negative values allowed) |
| `HEIGHT` | Integer from 0 to 100 |

Blank lines between aircraft entries are ignored.

**Example** (from `scenarios/scenario.txt`):

```
25
Balloon B1 2 3 20
JetPlane J1 23 44 32
Helicopter H1 654 33 20
```

## Output format

Three event types are written to `simulation.txt`:

```
Tower says: Helicopter#H1(1) registered to weather tower.
Helicopter#H1(1): 🚁 + ☀ Smooth flying today. The rotors approve.
Helicopter#H1(1) landing.
```

| Event | Format |
|-------|--------|
| Registration | `Tower says: {TYPE}#{NAME}({ID}) registered to weather tower.` |
| Weather change | `{TYPE}#{NAME}({ID}): {message}` |
| Landing | `{TYPE}#{NAME}({ID}) landing.` |

## Error handling

All errors are printed to stderr; the program exits with code `1`.

| Condition | Message prefix |
|-----------|----------------|
| Wrong argument count | `Invalid argument:` |
| Path is not a regular file | `Invalid argument:` |
| Invalid scenario content | `Invalid scenario:` |
| Scenario file not found | `Error: Scenario file not found.` |

## Test scenarios

| File | Purpose |
|------|---------|
| `scenarios/scenario.txt` | Valid full simulation (25 cycles, 9 aircraft) |
| `scenarios/inc_1.txt` | Missing iteration count |
| `scenarios/inc_2.txt` | Unknown type `Bolloon` |
| `scenarios/inc_3.txt` | Malformed line (missing name) |
| `scenarios/inc_4.txt` | Non-numeric coordinate |
| `scenarios/inc_empty.txt` | Invalid empty scenario |

## License

MIT License — see [LICENSE](LICENSE).
