# Task 6 - Interactive Web Analyzer

## Objective

Build a web scraper and HTML document analyzer in Java that fetches, parses, and extracts structural data from web pages using the JSoup library.

## Description

The Interactive Web Analyzer connects to a specified URL, downloads the HTML document, and presents an interactive menu to explore page titles, headings, links, images, and overall page statistics.

## Features

- Fetch and parse remote web pages using HTTP/HTTPS
- Display page title and meta information
- Extract hierarchical headings (H1 to H6)
- Extract hyperlinks with absolute URLs and anchor text
- Extract image sources and alt attributes
- Generate page statistics (total links, images, headings, word count)
- Produce a complete analytical summary
- Graceful error handling for invalid URLs, timeouts, and network connectivity issues

## External Libraries

- **JSoup**: `jsoup-1.23.2.jar` (located in `.vscode/`)

## Technologies Used

- Java
- JSoup HTML Parser
- Scanner

## How to Run

Compile the program:

```bash
javac -cp ../.vscode/jsoup-1.23.2.jar WebAnalyzer.java
```

Run the program:

```bash
java -cp .;../.vscode/jsoup-1.23.2.jar WebAnalyzer
```
