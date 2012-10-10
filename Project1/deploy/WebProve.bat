@echo off
:: ---------------------------------------------------------------
:: Windows startup batch file for RDDDaemon!
:: Version 0.1
::
:: Last Change: 
:: ---------------------------------------------------------------



:: Site URL
set siteUrl=  http://www.nextbolsa.com/cotacoes.php?action=psi20
:: Parser Plugin
set parserPlugin= eai.msejdf.web.ParseStocksPlugin



:: ===============================================================


::java WebProve <Site URL> <Analysis Plugin>
echo java  -cp  EAI-Proj1_v5.jar;lib/*  eai.msejdf.apps.WebProbe %siteUrl% %parserPlugin% 
java  -cp EAI-Proj1_v5.jar;lib/*;. eai.msejdf.apps.WebProbe %siteUrl% %parserPlugin% 

pause