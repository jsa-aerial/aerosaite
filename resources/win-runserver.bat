@echo off

set PATH=%PATH%;SAITEDIR\Libs

START /B java -cp "SAITEDIR\.saite\resources;SAITEDIR\aerial.aerosaite-VERSION-standalone.jar" aerial.aerosaite.core --port 3000 --repl-port 4100 > start.log 
