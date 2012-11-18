@echo off
:: ---------------------------------------------------------------
:: Prepare files for deployment
:: Version 0.1
::
:: Last Change: 
:: ---------------------------------------------------------------


:: EAR Source dir
set EARSrc=..\Project2\P2EARDeploy\deploy\
:: EAR Destination dir
set EARDest=ear

:: SRC Source dir
set JavaSrc=..\Project2\
:: EAR Destination dir
set JavaDest=src

:: EAR Destination dir
set SqlDest=sql

:: Zip File
set zipFile=P2Deploy.zip

:: ===============================================================


:: copy ear files
echo copying EAR
xcopy /I /Y %EARSrc%*.ear %EARDest%

:: copy source files
echo copying Source
if exist %JavaDest% rmdir /s /q %JavaDest%

xcopy /E /I %JavaSrc%\EAICommon %JavaDest%\EAICommon
xcopy /E /I %JavaSrc%\EJBServer %JavaDest%\EJBServer 
xcopy /E /I %JavaSrc%\EJBServerInterfaces %JavaDest%\EJBServerInterfaces
xcopy /E /I %JavaSrc%\JPAData %JavaDest%\JPAData
xcopy /E /I %JavaSrc%\P2EARDeploy %JavaDest%\P2EARDeploy
xcopy /E /I %JavaSrc%\WebBackOffice %JavaDest%\WebBackOffice
xcopy /E /I %JavaSrc%\WebFrontOffice %JavaDest%\WebFrontOffice

:: copy ear files
echo copying SQL
if not exist %sqlDest% mkdir %sqlDest%

:: Zipping it
echo Zipping it
if exist %zipFile% del zipFile
7z.exe a %zipFile% %EARDest% %sqlDest% %JavaDest%

pause