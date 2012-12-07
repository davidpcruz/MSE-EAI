@echo off
:: ---------------------------------------------------------------
:: Prepare files for deployment
:: Version 0.1
::
:: Last Change: 
:: ---------------------------------------------------------------

:: EAR Source dir
set EARSrc=..\Project3\P2EARDeploy\deploy\
:: EAR Destination dir
set EARDest=ear

:: SRC Source dir
set JavaSrc=..\Project3\
:: EAR Destination dir
set JavaDest=src

:: ESB Source dir
set ESBsrc=..\Project3\ESBServices\deploy\
:: ESB Destination dir
set ESBDest=esb

:: JBPM Source dir
set JBPMsrc=..\Project3\JBPMServices\deploy\
:: ESB Destination dir
set JBPMDest=jbpm

:: Report Destination dir
set ReportDest=report

:: Zip File
set zipFile=P3Deploy.zip

:: ===============================================================


:: copy source files
echo copying Source
if exist %JavaDest% rmdir /s /q %JavaDest%

:: Changed P1
xcopy /E /I %JavaSrc%\Project1 %JavaDest%\Project1

:: Changed P2 
xcopy /E /I %JavaSrc%\EAICommon %JavaDest%\EAICommon
xcopy /E /I %JavaSrc%\EJBServer %JavaDest%\EJBServer 
xcopy /E /I %JavaSrc%\EJBServerInterfaces %JavaDest%\EJBServerInterfaces
xcopy /E /I %JavaSrc%\JPAData %JavaDest%\JPAData
xcopy /E /I %JavaSrc%\P2EARDeploy %JavaDest%\P2EARDeploy
xcopy /E /I %JavaSrc%\WebBackOffice %JavaDest%\WebBackOffice
xcopy /E /I %JavaSrc%\WebFrontOffice %JavaDest%\WebFrontOffice

:: Changed P3
xcopy /E /I %JavaSrc%\ESBServices %JavaDest%\ESBServices
xcopy /E /I %JavaSrc%\ESBWebServices %JavaDest%\ESBWebServices
xcopy /E /I %JavaSrc%\JBPMServices %JavaDest%\JBPMServices

:: copy ear files
echo copying EAR
if exist %EARDest% rmdir /s /q %EARDest%
xcopy /I /Y %EARSrc%*.ear %EARDest%

:: copy ESB files
echo copying ESB
if exist %ESBDest% rmdir /s /q %ESBDest%
xcopy /I /Y %ESBsrc%*.esb %ESBDest%

:: copy JBPM files
echo copying JBPM
if exist %JBPMDest% rmdir /s /q %JBPMDest%
xcopy /I /Y %JBPMsrc%*.par %JBPMDest%

:: Zipping it
echo Zipping it
if exist %zipFile% del zipFile
7z.exe a %zipFile% %EARDest% %JavaDest% %ESBDest% %ReportDest% %JBPMDest%

pause