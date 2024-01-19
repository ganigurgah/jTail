@echo off
setlocal

REM Scriptin başlangıcında çalışma dizinini kaydet
set "original_dir=%CD%"

REM Scriptin bulunduğu dizine git
cd /d %~dp0

REM Ana işlem
java -jar jTail.jar --workDir=%original_dir% %*

REM CTRL+C ile çıkıldığında, orijinal dizine geri dön
cd /d %original_dir%

endlocal