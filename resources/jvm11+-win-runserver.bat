@echo off

START /B java ^
         --add-opens=java.base/jdk.internal.ref=ALL-UNNAMED ^
         --add-opens=java.base/sun.nio.ch=ALL-UNNAMED ^
         -cp "SAITEDIR\resources;SAITEDIR\aerial.aerosaite-VERSION-standalone.jar" aerial.aerosaite.core ^
         --port 3000 --repl-port 4100 > start.log
