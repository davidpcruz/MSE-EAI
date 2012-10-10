@echo off
:: ---------------------------------------------------------------
:: Windows startup batch file for HTMLDaemon!
:: Version 0.1
:: Created by the eepMW-Team09
::
:: Last Change: 
:: ---------------------------------------------------------------



:: Site URL
set siteUrl=  http://www.nextbolsa.com/cotacoes.php?action=psi20
:: Analysis Plugin
set analysisPlugin= eai.msejdf.web.ParseStocksPlugin



:: ===============================================================


::java WebProve <Site URL> <Analysis Plugin>
echo java  -jar lib/eepMW.jar %siteUrl% %analysisPlugin% 
java  -jar lib/eepMW.jar %pathLogin% %analysisPlugin% 

pause