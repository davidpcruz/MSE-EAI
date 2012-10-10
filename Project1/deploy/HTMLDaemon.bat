@echo off
:: ---------------------------------------------------------------
:: Windows startup batch file for HTMLDaemon!
:: Version 0.1
:: Created by the eepMW-Team09
::
:: Last Change: 
:: ---------------------------------------------------------------



:: ===============================================================


::java WebProve <Site URL> <Analysis Plugin>
echo java  -cp EAI-Proj1_v5.jar;lib/*;. eai.msejdf.daemons.HTMLDaemon
java  -cp EAI-Proj1_v5.jar;lib/*;. eai.msejdf.daemons.HTMLDaemon

pause