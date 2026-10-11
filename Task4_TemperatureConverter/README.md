# Task 4 - Temperature Converter

## Objective

Develop a menu-driven temperature conversion application in Java that converts temperatures between Celsius, Fahrenheit, and Kelvin with accurate formulas and input validation.

## Description

The Temperature Converter allows users to convert temperatures across different units using standard scientific formulas. The program validates input against non-numeric entries, empty submissions, and physically impossible temperatures below absolute zero (0 K, -273.15 °C, -459.67 °F).

## Supported Conversions

1. Celsius to Fahrenheit
2. Fahrenheit to Celsius
3. Celsius to Kelvin
4. Kelvin to Celsius
5. Fahrenheit to Kelvin
6. Kelvin to Fahrenheit

## Input Validation

The program handles:

- Empty input
- Alphabetic or non-numeric values
- Out-of-range menu selections
- Values below absolute zero across all conversion modes:
  - Celsius: minimum -273.15 °C
  - Fahrenheit: minimum -459.67 °F
  - Kelvin: minimum 0.00 K

## Concepts Used

- Methods
- switch statements
- Conditional logic
- Scanner
- Exception handling
- String trimming and parsing

## Technologies Used

- Java
- Java Development Kit (JDK)

## How to Run

Compile the program:

```bash
javac TemperatureConverter.java
```

Run the program:

```bash
java TemperatureConverter
```
