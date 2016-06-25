@ECHO off

%~d0
cd %~p0

REM you can remove this if your JAVA_HOME is already defaulted to 1.7 
set JAVA_HOME="c:\progra~1\Java\jdk1.7.0_13"

REM uncomment this to debug remotely to Jetty
REM set MAVEN_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n

call mvn jetty:run -Pjetty

REM Allows you to see the stacktrace if jetty exits with exception
pause