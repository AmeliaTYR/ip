# Tootie - based on the Duke project
Tootie is a task list manager program

## Setting up for use 

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Tootie` from [here](https://github.com/AmeliaTYR/ip/releases/tag/A-Release).
    1. The current version is Tootie version 2.0
1. Check out the user guide page [here](https://ameliatyr.github.io/ip/) for instructions on how to use Tootie. 

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
    
Further instructions on using Tootie can be found at the user guide [here](https://ameliatyr.github.io/ip/)

## Setting up in Intellij for editing

Prerequisites: JDK 11, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first)
1. Set up the correct JDK version, as follows:
   1. Click `Configure` > `Structure for New Projects` and then `Project Settings` > `Project` > `Project SDK`
   1. If JDK 11 is listed in the drop down, select it. If it is not, click `New...` and select the directory where you installed JDK 11
   1. Click `OK`
1. Import the project into Intellij as follows:
   1. Click `Open or Import`.
   1. Select the project directory, and click `OK`
   1. If there are any further prompts, accept the defaults.
   
1. After the importing is complete, locate the `src/main/java/duke.Duke.java` file, right-click it, and choose `Run duke.Duke.main()`. If the setup is correct, you should see something like the below:

   ```
   Hello from
      _____                    _        _            
     |_   _|   ___     ___    | |_     (_)     ___   
       | |    / _ \   / _ \   |  _|    | |    / -_)  
      _|_|_   \___/   \___/   _\__|   _|_|_   \___|  
    _|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""| 
    "`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-' 
   
   Tootie - Version 2.0
   ==============================================
   Loading tootieSettings.txt save file...
   ─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
   Loading allTasks.txt save file...
   11 tasks expected from file.
   11 tasks read successfully!
   ─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
   Hello Ames! I'm Tootie!
   What can I do for you?
   ─────── ✱*.｡:｡✱*.:｡✧*.｡✰*.:｡✧*.｡:｡*.｡✱ ───────
   ```
   
   The logo itself will vary between runs at random
   
## Name change rationale

Duke name changed to Tootie in honor of Tootie the terrapin

![Tootie-pa-tootie](https://github.com/AmeliaTYR/ip/blob/master/images/tootie-pa-tootie.png)
