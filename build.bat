@echo off
title MAVEN�����������
mode con: cols=60 lines=30
color 0a

:main
echo.��ѡ��ִ�е�����:
echo [Eclipse��Ŀ��ʼ��] �밴 1
echo [��   ��   ��   Ŀ] �밴 2
echo [��             ��] �밴 `
echo.
set /p choice=���ѡ��
echo.
if "%choice%"=="1"  goto init
if "%choice%"=="2"  goto publish
if "%choice%"=="`"  goto end

goto main

:init
echo  Eclipse��Ŀ��ʼ����ʼ....
start mvn eclipse:eclipse
echo  ��Ŀ��ʼ�����
echo.
echo. 
goto main

:publish
echo  ��Ŀ�����ʼ....
start mvn clean install
echo  ��Ŀ������
echo.
echo. 
goto main

��end














