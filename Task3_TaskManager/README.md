# Task 3 - CRUD Task Manager

## Objective

Develop a task management application using Java that supports
Create, Read, Update, and Delete (CRUD) operations.

## Description

The Task Manager allows users to create and manage multiple tasks.
Each task is assigned a unique ID and has a status that changes
as the task progresses.

The application supports multiple tasks being managed at the
same time.

## Features

- Add multiple tasks
- View all tasks
- Start multiple tasks
- Update multiple tasks
- Complete multiple tasks
- Delete multiple tasks
- View task progress
- Track pending tasks
- Track tasks in progress
- Track completed tasks
- Calculate remaining tasks
- Calculate completion percentage
- Unique task IDs
- Exit confirmation
- Final task summary

## Task Status

Each task can have one of three statuses:

- PENDING
- IN PROGRESS
- COMPLETED

## Input Validation

The program handles:

- Empty input
- Alphabetic input where numbers are required
- Special characters
- Decimal values where whole numbers are required
- Negative numbers
- Invalid task IDs
- Duplicate task selection
- Empty task names
- Task names longer than 100 characters
- Invalid menu choices

## Progress Calculation

The program displays:

- Total tasks
- Completed tasks
- In-progress tasks
- Pending tasks
- Remaining tasks
- Completion percentage

The completion percentage is calculated using:

Completed Tasks / Total Tasks × 100

Remaining tasks are calculated using:

Total Tasks - Completed Tasks

## Concepts Used

- Classes and Objects
- ArrayList
- Enum
- Methods
- Loops
- Conditional statements
- switch statement
- HashSet
- Scanner
- Exception handling
- Input validation
- CRUD operations

## Technologies Used

- Java
- VS Code
- Java Development Kit (JDK)

## How to Run

Compile the program:

javac TaskManager.java

Run the program:

java TaskManager

## Example Workflow

1. Add multiple tasks.
2. View all tasks.
3. Start selected tasks.
4. Update task names.
5. Complete selected tasks.
6. Delete unwanted tasks.
7. View overall progress.
8. Exit after reviewing the final summary.
