# Task 5 - Persistent Task Manager

## Objective

Build a persistent task management console application in Java supporting CRUD operations with file-based persistence across sessions.

## Description

The Persistent Task Manager extends the in-memory Task Manager by saving task data to and loading from a local text file (`tasks.txt`). Tasks persist between application runs and include IDs, titles, descriptions, and completion statuses.

## Features

- Add multiple tasks
- View all tasks
- Search tasks by keyword
- Filter tasks by status
- Start tasks (PENDING to IN PROGRESS)
- Update task details
- Complete tasks (IN PROGRESS to COMPLETED)
- Delete tasks
- View progress and completion statistics
- Manual and automatic task persistence to `tasks.txt`
- Robust error handling for missing or corrupt files

## Task Status

Each task can have one of three statuses:

- PENDING
- IN PROGRESS
- COMPLETED

## Input Validation & Resilience

The program handles:

- Missing `tasks.txt` (creates a new file gracefully)
- Corrupt lines in `tasks.txt` (skips bad records with warnings without crashing)
- Empty, non-numeric, and out-of-range menu inputs
- Duplicate task selection and invalid task IDs

## Technologies Used

- Java
- Java I/O (`BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter`)
- Collections Framework (`ArrayList`, `HashSet`, `EnumMap`)
- Scanner

## How to Run

Compile the program:

```bash
javac PersistentTaskManager.java
```

Run the program:

```bash
java PersistentTaskManager
```
