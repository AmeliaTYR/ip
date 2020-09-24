# User Guide

## Introduction

Tootie is a task list manager program

## Quick Start

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

### Bring up help guide: `help`
Displays a list of commands tootie understands

Format: `help`

Example of usage: 

`help`

### List all tasks: `list`
Displays all the tasks in the list

Format: `list`

Example of usage: 

`list`

### Adding a todo: `todo`
Adds a new todo task to the list of tasks.

Format: `todo n/TASKNAME`

* The `TODO_NAME` cannot contain slashes.  

Example of usage: 

`todo n/Write the rest of the User Guide`

`todo n/Refactor the User Guide to remove passive voice`

### Adding a deadline: `deadline`
Adds a new deadline task to the list of tasks.

Format: `deadline t/TASKNAME d/DUE_DATE`

* The `TASKNAME` cannot contain slashes.  
* The `DUE_DATE` can be of the format "dd-MM-yyyy HH:mm" with the time in 24-Hr format or "dd-MM-yyyy"
* The parameters can be in any order

Example of usage: 

`deadline t/write essay d/31-12-2020 04:55`

`deadline t/submit report d/30-10-2020`

`deadline d/30-10-2020 t/submit report `

### Adding an event: `event`
Adds a new scheduled event task to the list of tasks.

Format: `event t/TASKNAME s/START_TIME e/END_TIME`

* The `TASKNAME` cannot contain slashes.  
* The `START_TIME` and `END_TIME` can be of the format "dd-MM-yyyy HH:mm" with the time in 24-Hr format or "dd-MM-yyyy"
* The `START_TIME` should be before the `END_TIME`.
* The parameters can be in any order

Example of usage: 

`event t/clean room s/31-12-2020 04:55 e/31-12-2020 05:45`

`event t/clean room s/31-12-2020 e/31-12-2020`

`event e/31-12-2020 t/clean room s/31-12-2020`

### Mark a task done: `done`
Marks an indicated task done

Format: `done [TASK_INDEX]`

Example of usage: 

`help`

### Close the program: `bye`
Signals the end of the program use and automatically saves settings and tasks in the task list

Format: `bye`

Example of usage: 

`bye`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
