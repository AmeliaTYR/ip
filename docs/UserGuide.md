# User Guide

## Introduction

Tootie is a task list manager program

## Quick Start

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Tootie` from [here](https://github.com/AmeliaTYR/ip/releases/tag/v1.1-alpha).
  1. The current version is v1.1-alpha

Notes:
* All comments starting with # will be ignored.
* Change your console font to one which supports Unicode for the optimal user experience.
  * For Windows, the instructions are as follows: 
    * save the ip.jar file to your desired folder
    * open the command line in the folder that the ip.jar is in 
        * you may do this by typing `cmd` in the location bar of Windows Explorer and pressing the Enter key
    * run `chcp 65001` to change to UTF-8
    * right click the bar above your command line app to open properties
    * change the font to NSimSun
    * run `java -Dfile.encoding=UTF-8 -jar ip.jar`


## Features 

### 1) Basic CLI commands
Basic commands to use the program

### Bring up help guide: `help`
Displays a list of commands tootie understands, or search for a specific command for the command description

Format: `help [COMMAND]`

* `COMMAND` is a command the user may specifically search for. If no command matching the search command is found an error message will be displayed.
* The command can have no arguments, and will print the full list of commands

Example of usage: 

`help`

`help full`

`help list`

Expected outcome:

for the full list of commands
```
help full
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
Here is the list of commands I understand:

help: displays a list of commands tootie understands
  or search for a specific command for the command description
  Parameters:  help [COMMAND]
  Example:  help
  Example:  help filter

filepath: Display file paths of save files
  Example:  filepath
  Example:  filepaths
  Note: The command can be spelled with or without the 's' at the end

save: manually save the list of tasks without closing the program
  Example:  save

bye: closes the program
  Example:  bye

todo: add a todo task to the list
  Parameters:  todo t/TASKNAME
  Example:  todo t/clean room

deadline: add a task with a deadline to the list
  Parameters:  deadline t/TASKNAME d/DUE_DATE
  Example:  deadline t/write essay d/31-12-2020 04:55
  Example:  deadline t/submit report d/30-10-2020

event: add a scheduled event task to the list
  Parameters:  event t/TASKNAME s/START_TIME e/END_TIME
  Example:  event t/clean room s/31-12-2020 04:55 e/31-12-2020 05:45
  Example:  event t/clean room s/31-12-2020 e/31-12-2020

load: add tasks from existing file
  Example:  load

done: marks indicated task done (choose number from list)
  Parameters:  done TASK_INDEX
  Example:  done 1

undone: marks indicated task undone (choose number from list)
  Parameters:  undone TASK_INDEX
  Example:  undone 1

delete: deletes indicated task (choose number from list)
  Parameters:  delete TASK_INDEX
  Example:  delete 1

list: displays the complete list of tasks entered
  Example:  list

filter: filters out tasks from the list according to the parameters
  Parameters:  filter st/SEARCH_TERM sb/START_BEFORE sa/START_AFTER eb/END_BEFORE
        ea/END_AFTER db/DUE_BEFORE da/DUE_AFTER tt/TASK_TYPES cs/COMPLETION_STATUS
  Example:  filter tt/event sb/13-01-2019 ea/31-01-2020
  Example:  filter tt/event todo st/homework
  Example:  filter tt/deadline,todo db/14-04-2020 16:40
  Note: Check user guide for more verbose description

username: allows user to set username
  Parameters:  username USERNAME
  Example:  username Sophia

divider: select a divider for customisation
  dividers available:
1) SPARKLY ─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
2) PLAIN ----------------------------------------------
3) SIMPLE *---*---*---*---*---*---*---*---*---*---*---*
4) DOUBLE ==============================================
  Parameters:  divider DIVIDER_INDEX
  Example:  divider 1

-----
NOTE: datetime entries can be of the format
    "dd-MM-yyyy HH:mm" with time in 24-Hour format
    OR "dd-MM-yyyy"

─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```
for the shortened list of commands

```
help
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
Here is the list of commands I understand:

help: displays a list of commands tootie understands
filepath: Display file paths of save files
save: manually save the list of tasks without closing
bye: closes the program
todo: add a todo task to the list
deadline: add a task with a deadline to the list
event: add a scheduled event task to the list
load: add tasks from existing file
done: marks indicated task done (choose number from list)
undone: marks indicated task undone (choose number from list)
delete: deletes indicated task (choose number from list)
list: displays the complete list of tasks entered
filter: filters out tasks from the list according to the parameters
username: allows user to set username
divider: select a divider for customisation

-----
  NOTE: search for a specific definition by typing help [COMMAND]
    Example: help filter  
  NOTE: type "full" as the argument for a full list of commands
    Example: help full
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```
searching for a specific command
```
help divider
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
divider: select a divider for customisation
  dividers available:
1) SPARKLY ─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
2) PLAIN ----------------------------------------------
3) SIMPLE *---*---*---*---*---*---*---*---*---*---*---*
4) DOUBLE ==============================================
  Parameters:  divider DIVIDER_INDEX
  Example:  divider 1
```

### Display file paths: `filepath` or `filepaths`
Display file paths of save files

Format: `filepath` or `filepaths`

* The command can be spelled with or without the 's' at the end

Example of usage: 

`filepath`

`filepaths`

Expected outcome:

```
filepath
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
The list of saved tasks can be found at:
C:/Users/Amelia/Documents/GitHub/ip/text-ui-test/data/allTasks.txt
The list of saved settings can be found at:
C:/Users/Amelia/Documents/GitHub/ip/text-ui-test/data/tootieSettings.txt
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```
OR
```
filepaths
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
The list of saved tasks can be found at:
C:/Users/Amelia/Documents/GitHub/ip/text-ui-test/data/allTasks.txt
The list of saved settings can be found at:
C:/Users/Amelia/Documents/GitHub/ip/text-ui-test/data/tootieSettings.txt
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```

### Manually save all tasks: `save`
Allows user to manually save all tasks in the list to the save file

Format: `save`

* It is not necessary to use this function as the list is automatically saved at the end of the program, but the user may do so anyway in case the program was closed accidentally.

Example of usage: 

`save`

Expected outcome:

```
save
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
All tasks saved!
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```

### Close the program: `bye`
Signals the end of the program use and automatically saves settings and tasks in the task list

Format: `bye`

Example of usage: 

`bye`

Expected outcome:

```
bye
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
Bye Sophia! Hope to see you again soon! (◠‿◠✿)
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
All tasks saved.
All settings saved.
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```

### 2) Add Task items
Add a `todo`, `deadline` or`event` to the list of tasks.

### Add a todo: `todo`
Adds a new todo task to the list of tasks.

Format: `todo n/TASKNAME`

* The `TODO_NAME` cannot contain slashes.  

Example of usage: 

`todo n/Write the rest of the User Guide`

`todo n/Refactor the User Guide to remove passive voice`

Expected outcome:

```
todo t/clean turtle tank
==============================================
added todo: clean turtle tank
=============================================
```
Formatting errors caught:
```
todo
==============================================
Check todo input formatting!

todo: add a todo task to the list
  Parameters:  todo t/TASKNAME
  Example:  todo t/clean room

==============================================
```

```
todo t/
==============================================
todo taskname is empty? (・∧‐)ゞ
==============================================
```

### Add a deadline: `deadline`
Adds a new deadline task to the list of tasks.

Format: `deadline t/TASKNAME d/DUE_DATE`

* The `TASKNAME` cannot contain slashes.  
* The `DUE_DATE` can be of the format "dd-MM-yyyy HH:mm" with the time in 24-Hr format or "dd-MM-yyyy".
* The `DUE_DATE` must be a valid calendar date. 
* The parameters can be in any order

Example of usage: 

`deadline t/write essay d/31-12-2020 04:55`

`deadline t/submit report d/30-10-2020`

`deadline d/30-10-2020 t/submit report `

Expected outcome:

if no time is added the time defaults to 12:00AM
```
deadline t/write essay d/31-12-2020
==============================================
added deadline:
write essay (by: Thu 31 Dec 2020 12:00 AM)
==============================================
```
time entered should be in 24-Hr format
```
deadline t/do project d/30-01-2020 04:55
==============================================
added deadline:
do project (by: Thu 30 Jan 2020 04:55 AM)
==============================================
```

### Add an event: `event`
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

Expected outcome:

if no time is added the time defaults to 12:00AM
```
event t/clean kitchen s/12-12-2020 e/31-12-2020
==============================================
added event:
clean kitchen (from: Sat 12 Dec 2020 12:00 AM to Thu 31 Dec 2020 12:00 AM)
==============================================
```
time entered should be in 24-Hr format
```
event t/clean shoes s/01-02-2020 e/31-12-2020 05:45
==============================================
added event:
clean shoes (from: Sat 1 Feb 2020 12:00 AM to Thu 31 Dec 2020 05:45 AM)
==============================================
```

### Add tasks from existing file: `load`
Load tasks from an existing allTasks.txt file and add them to the list of tasks in the current session.

Format: `load`

* The user will be prompted to indicate the full file path of the existing file. 
* The user may cancel the operation by typing `cancel` instead of the file path when prompted.

Example of usage: 

`load`

Expected outcome:
```
load
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
Loading allTasks.txt save file...
Enter the full path to existing file (type "cancel" to cancel):
C:\Users\Amelia\Documents\GitHub\ip\text-ui-test\data\allTasks.txt
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
3 tasks expected from file.
3 tasks read successfully!
Total tasks in list: 11
```

It is possible to cancel the operation.
```
load
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
Loading allTasks.txt save file...
Enter the full path to existing file (type "cancel" to cancel):
cancel
Cancelled "load save file" operation
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```

### 3) Modify task list
Mark a task as `done` or `undone`, or `delete` the task from the list.

### Mark a task done: `done`
Marks an indicated task done by index

Format: `done TASK_INDEX`

* The `TASK_INDEX` should be within range of tasks in the list

Example of usage: 

`done 1`

Expected outcome:

```
done 3
==============================================
Nice! I've marked this task as done:
    [E][✓] clean kitchen (from: Thu 31 Dec 2020 04:55 AM to Thu 31 Dec 2020 05:45 AM)
(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧
==============================================
```
if the task index is out of bounds this error would appear

```
done 0
==============================================
No such task? (・∧‐)ゞ
==============================================
```

### Mark a task undone: `undone`
Marks an indicated task undone by index

Format: `undone TASK_INDEX`

* The `TASK_INDEX` should be within range of tasks in the list

Example of usage: 

`undone 1`

Expected outcome:

```
undone 5
==============================================
Aww... I've marked this task undone:
    [D][✗] write essay (by: Thu 31 Dec 2020 12:00 AM)
(╥﹏╥)
==============================================
```

if the task index is out of bounds this error would appear

```
undone 0
==============================================
No such task? (・∧‐)ゞ
==============================================
```

### Delete a task: `delete`
Delete a task from the task list by index

Format: `delete TASK_INDEX`

* The `TASK_INDEX` should be within range of tasks in the list

Example of usage: 

`delete 1`

Expected outcome:

```
delete 2
==============================================
Yay! I have deleted this task:
    [D][✓] do project (by: Thu 30 Jan 2020 04:55 PM)
(ﾉ◕ヮ◕)ﾉ*:･ﾟ✧
==============================================
```

if the task index is out of bounds this error would appear

```
delete 0
==============================================
No such task? (・∧‐)ゞ
==============================================
```

### 4) List view
View all tasks in the list, or run a filtered search for specific tasks

### List all tasks: `list`
Displays all the tasks in the list

Format: `list`

Example of usage: 

`list`

Expected outcome:

```
list
==============================================
You have 10 tasks, 9 not done
1. [T][✗] clean shoes
2. [D][✗] do project (by: Thu 30 Jan 2020 04:55 PM)
3. [E][✓] clean kitchen (from: Thu 31 Dec 2020 04:55 AM to Thu 31 Dec 2020 05:45 AM)
4. [T][✗] clean turtle tank
5. [D][✗] write essay (by: Thu 31 Dec 2020 12:00 AM)
6. [D][✗] do project (by: Thu 30 Jan 2020 04:55 AM)
7. [E][✗] clean house (from: Thu 31 Dec 2020 12:00 AM to Fri 5 Feb 2021 12:00 AM)
8. [E][✗] clean kitchen (from: Sat 12 Dec 2020 12:00 AM to Thu 31 Dec 2020 12:00 AM)
9. [E][✗] clean shoes (from: Sat 1 Feb 2020 12:00 AM to Thu 31 Dec 2020 05:45 AM)
10. [E][✗] clean socks (from: Sat 1 Feb 2020 04:55 AM to Thu 31 Dec 2020 05:45 AM)
==============================================
```

### Filtered search: `filter`
Filters out tasks from the list according to the parameters

Format: `filter st/SEARCH_TERM sb/START_BEFORE sa/START_AFTER eb/END_BEFORE ea/END_AFTER db/DUE_BEFORE da/DUE_AFTER tt/TASK_TYPES cs/COMPLETION_STATUS`

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
* For the `COMPLETION_STATUS`, passing the argument `done` filters for tasks that are done, while passing `undone` filters for arguments that are not done.

Example of usage: 

`filter tt/event sb/13-01-2019 ea/31-01-2020`

`filter tt/event todo st/homework`

`filter tt/deadline,todo`

`filter st/clean`

`filter cs/done`

Expected outcome:

```
filter tt/event,todo
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
1. [T][✗] clean shoes
2. [E][✓] clean kitchen (from: Thu 31 Dec 2020 04:55 AM to Thu 31 Dec 2020 05:45 AM)
3. [T][✓] clean turtle tank
5. [E][✗] clean house (from: Thu 31 Dec 2020 12:00 AM to Fri 5 Feb 2021 12:00 AM)
6. [E][✗] clean kitchen (from: Sat 12 Dec 2020 12:00 AM to Thu 31 Dec 2020 12:00 AM)
7. [E][✗] clean shoes (from: Sat 1 Feb 2020 12:00 AM to Thu 31 Dec 2020 05:45 AM)
8. [E][✗] clean socks (from: Sat 1 Feb 2020 04:55 AM to Thu 31 Dec 2020 05:45 AM)
Filtered! 7 tasks found, 5 incomplete.
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```

### 5) Customisation
Customise Tootie by changing your username, and the style of line divider used.

### Change username: `username`
Change username stored in system

Format: `username USERNAME`

* The `USERNAME` may have spaces

Example of usage: 

`username Tootie`

Expected outcome:

```
username Sophia
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
Hello Sophia!(◠‿◠✿)
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```

Also the greeting and farewell will change accordingly

```
Hello Ames! I'm Tootie!
What can I do for you?
==============================================
```

```
Bye Sophia! Hope to see you again soon! (◠‿◠✿)
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```

### Select a new divider: `divider`
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

Expected outcome:

```
==============================================
divider 3
==============================================
Divider changed! (◠‿◠✿)
*---*---*---*---*---*---*---*---*---*---*---*
divider 2
*---*---*---*---*---*---*---*---*---*---*---*
Divider changed! (◠‿◠✿)
----------------------------------------------
divider 4
----------------------------------------------
Divider changed! (◠‿◠✿)
==============================================
divider 1
==============================================
Divider changed! (◠‿◠✿)
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```

if the divider index is out of bounds this error would appear

```
divider 6
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
Divider choice not found? (・∧‐)ゞ
─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
```

### 6) Basic error handling
If an unrecognised command is used, Tootie will feedback to the user.

### Unrecognised commands
Expected outcome:

```
blah
==============================================
Command not found? (・∧‐)ゞ
Type "help" for a list of commands!
==============================================
```

### 7) Set up file paths
Initial interaction with Tootie to set up allTasks.txt and tootieSettings.txt save locations

### Ask user for allTasks.txt absolute path

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Copy the allTasks.txt into a folder labelled "data" and state the absolute file path when prompted. The saved settings file is automatically generated.

**Q**: How do I find the save files for allTasks.txt and tootieSettings.txt?

**A**: Use the `filepath` command in the program to retrieve the absolute file paths for both files. \(Note: they may not be in the same location\)

**Q**: Can I change the Tootie logo at the start of the program?

**A**: No. It is randomly decided by the program.

**Q**: Can I modify the saved list of tasks directly?

**A**: Yes, follow the format indicated in the file header for tasks to be read correctly

## Command Summary

 Command | Purpose | Syntax
---------|---------|-------
help|Bring up help guide|_help_
filepath|Display file paths|_filepath_ or _filepaths_ 
save|Save all tasks|_save_
bye|Close the program|_bye_ 
todo|Add a todo|_todo n/TASKNAME_
deadline|Add a deadline|_deadline t/TASKNAME d/DUE_DATE_
event|Add an event|_event t/TASKNAME s/START_TIME e/END_TIME_
load|Add tasks from existing file|_load_
done|Mark a task done|_done 1_
undone|Mark a task undone|_undone 1_
delete|Delete a task|_delete 1_
list |List all tasks|_list_
filter|Filtered search|_filter st/SEARCH_TERM sb/START_BEFORE sa/START_AFTER eb/END_BEFORE ea/END_AFTER db/DUE_BEFORE da/DUE_AFTER tt/TASK_TYPES_
username|Change username|_username Ames_
divider|Mark a task done|_divider 1_
