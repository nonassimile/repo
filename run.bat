@echo OFF
echo '%time% Running mvn clean package'

call cd c:\development\workspace\connectelco\connectelco-app

call mvn clean package

echo '%time% Starting Connectelco - Griot Server'

call cd c:\development\workspace\connectelco\connectelco-app\target & java -jar %~dp0target\connectelco-app-1.0-SNAPSHOT.jar %*
\ & echo SERVER Successfully Started