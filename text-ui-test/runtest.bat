@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
del ACTUAL.TXT

REM compile the code into the bin folder
javac -cp ..\src -Xlint:none -d ..\bin ..\src\main\java\duke\*.java ..\src\main\java\duke\task\*.java ..\src\main\java\duke\storage\*.java ..\src\main\java\duke\exceptions\*.java ..\src\main\java\duke\tootieFunctions\*.java ..\src\main\java\duke\parsers\*.java ..\src\main\java\duke\constants\*.java ..\src\main\java\duke\ui\*.java

IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -Dfile.encoding=UTF8 -classpath ..\bin duke.Duke < input.txt > ACTUAL.TXT


REM compare the output to the expected output USE INTELLIJ INSTEAD FC BAD :(
echo -------------------
echo check output by selecting both files in intellij
echo and then right click and find compare files
echo or press ctrl + D after selecting
REM FC ACTUAL.TXT EXPECTED.TXT
