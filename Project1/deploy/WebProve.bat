@echo off
:: ---------------------------------------------------------------
:: Windows startup batch file for RDDDaemon!
:: Version 0.1
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

