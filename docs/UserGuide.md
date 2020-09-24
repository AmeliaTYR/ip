# User Guide

## Introduction

Tootie is a task list manager program

## Quick Start

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

### 1) Basic CLI commands
Basic commands to use the program

#### Bring up help guide: `help`
Displays a list of commands tootie understands

Format: `help`

Example of usage: 

`help`

#### Display file paths: `filepath` or `filepaths`
Display file paths of save files

Format: `filepath` or `filepaths`

* The command can be spelled with or without the 's' at the end

Example of usage: 

`filepath`

`filepaths`

#### Close the program: `bye`
Signals the end of the program use and automatically saves settings and tasks in the task list

Format: `bye`

Example of usage: 

`bye`

### 2) Add Task items
Add a `todo`, `deadline` or`event` to the list of tasks.

#### Add a todo: `todo`
Adds a new todo task to the list of tasks.

Format: `todo n/TASKNAME`

* The `TODO_NAME` cannot contain slashes.  

Example of usage: 

`todo n/Write the rest of the User Guide`

`todo n/Refactor the User Guide to remove passive voice`

#### Add a deadline: `deadline`
Adds a new deadline task to the list of tasks.

Format: `deadline t/TASKNAME d/DUE_DATE`

* The `TASKNAME` cannot contain slashes.  
* The `DUE_DATE` can be of the format "dd-MM-yyyy HH:mm" with the time in 24-Hr format or "dd-MM-yyyy".
* The parameters can be in any order

Example of usage: 

`deadline t/write essay d/31-12-2020 04:55`

`deadline t/submit report d/30-10-2020`

`deadline d/30-10-2020 t/submit report `

#### Add an event: `event`
Adds a new scheduled event task to the list of tasks.

Format: `event t/TASKNAME s/START_TIME e/END_TIME`

* The `TASKNAME` cannot contain slashes.  
* The `START_TIME` and `END_TIME` can be of the format "dd-MM-yyyy HH:mm" with the time in 24-Hr format or "dd-MM-yyyy".
* The `START_TIME` should be before the `END_TIME`.
* The parameters can be in any order

Example of usage: 

`event t/clean room s/31-12-2020 04:55 e/31-12-2020 05:45`

`event t/clean room s/31-12-2020 e/31-12-2020`

`event e/31-12-2020 t/clean room s/31-12-2020`

### 3) Modify task list
Mark a task as `done` or `undone`, or `delete` the task from the list.

#### Mark a task done: `done`
Marks an indicated task done by index

Format: `done TASK_INDEX`

* The `TASK_INDEX` should be within range of tasks in the list

Example of usage: 

`done 1`

#### Mark a task undone: `undone`
Marks an indicated task undone by index

Format: `undone TASK_INDEX`

* The `TASK_INDEX` should be within range of tasks in the list

Example of usage: 

`undone 1`

#### Delete a task: `delete`
Delete a task from the task list by index

Format: `delete TASK_INDEX`

* The `TASK_INDEX` should be within range of tasks in the list

Example of usage: 

`delete 1`

### 4) List view
View all tasks in the list, or run a filtered search for specific tasks

#### List all tasks: `list`
Displays all the tasks in the list

Format: `list`

Example of usage: 

`list`

#### Filtered search: `filter`
Filters out tasks from the list according to the parameters

Format: `filter st/SEARCH_TERM sb/START_BEFORE sa/START_AFTER eb/END_BEFORE ea/END_AFTER db/DUE_BEFORE da/DUE_AFTER tt/TASK_TYPES`

* The parameters can be in any order.
* The command should contain at least one search parameter.
* The `SEARCH_TERM` cannot contain slashes and is case-sensitive.  
* The `TASK_TYPES` should not contain slashes, and should contain desired task types, which are `todo`, `deadline` and`event`.
  * If no `TASK_TYPES` parameter is specified, the function will filter for all 3 types.
* The `TASK_TYPES` can be separated by any delimiter of your choice, and may be in any order, and is not case-sensitive. 
  * For example, `tt/event todo`, `tt/TODO,deadline` and `tt/eventdeadline` are all valid.
* The `START_BEFORE`, `START_AFTER`, `END_BEFORE`, `END_AFTER`, `DUE_BEFORE`, `DUE_AFTER`, are dates and can be of the format "dd-MM-yyyy HH:mm" with the time in 24-Hr format or "dd-MM-yyyy".
  *  `START_BEFORE` applies to `Event` tasks and filters for events which start time is before this date.
  *  `START_AFTER` applies to `Event` tasks and filters for events which start time is after this date.
  *  `END_BEFORE` applies to `Event` tasks and filters for events which end time is before this date.
  *  `END_AFTER` applies to `Event` tasks and filters for events which end time is after this date.
  *  `DUE_BEFORE` applies to `Deadline` tasks and filters for deadlines which due date is before this date.
  *  `DUE_AFTER` applies to `Deadline` and filters for deadlines which due date is after this date.
* If the "before" dates are after the "after" dates, they will be automatically swapped.

Example of usage: 

`filter tt/event sb/13-01-2019 ea/31-01-2020`

`filter tt/event todo st/homework`

`filter tt/deadline,todo`

`filter st/clean`

### 5) Customisation
Customise Tootie by changing your username, and the style of line divider used.

#### Change username: `username`
Change username stored in system

Format: `username USERNAME`

* The `USERNAME` may have spaces

Example of usage: 

`username Tootie`

#### Select a new divider: `divider`
Select a divider from the list of dividers for customisation

  dividers avaliable:
1. SPARKLY ─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
1. PLAIN ----------------------------------------------
1. SIMPLE *---*---*---*---*---*---*---*---*---*---*---*
1. DOUBLE ==============================================

Format: `divider DIVIDER_INDEX`

* The `DIVIDER_INDEX` should be a number from 1 to 4

Example of usage: 

`divider 1`


## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Copy the allTasks.txt and specific the file path when prompted. The saved settings file is automatically generated.

**Q**: How do I find the save files for allTasks.txt and tootieSettings.txt?

**A**: Use the `filepath` command in the program to retrieve the absolute file paths for both files. \(Note: they may not be in the same location\)

**Q**: Can I change the Tootie logo at the start of the program?

**A**: No. It is randomly decided by the program.

## Command Summary

 Command | Purpose | Syntax
---------|---------|-------
help|Bring up help guide|_help_
filepath|Display file paths|_filepath_ or _filepaths_ 
bye|Close the program|_bye_ 
todo|Add a todo|_todo n/TASKNAME_
deadline|Add a deadline|_deadline t/TASKNAME d/DUE_DATE_
event|Add an event|_event t/TASKNAME s/START_TIME e/END_TIME_
done|Mark a task done|_done 1_
undone|Mark a task undone|_undone_
delete|Delete a task|_delete 1_
list |List all tasks|_list_
filter|Filtered search|_filter st/SEARCH_TERM sb/START_BEFORE sa/START_AFTER eb/END_BEFORE ea/END_AFTER db/DUE_BEFORE da/DUE_AFTER tt/TASK_TYPES_
username|Change username|_username Ames_
divider|Mark a task done|_divider_
