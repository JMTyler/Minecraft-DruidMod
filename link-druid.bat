@echo off

pushd .

set source_path="C:\Users\jtyler\Documents\Programming\Java\Minecraft\Druid"
set dest_path=".\src\minecraft\net\minecraft\src"


REM remove old links or files
del /s /q  %dest_path%\EntityLiving.java
del /s /q  %dest_path%\ItemHealingBark.java
del /s /q  %dest_path%\ItemMagicLeaf.java
del /s /q  %dest_path%\mod_Druid.java


REM rebuild links
mklink	%dest_path%\EntityLiving.java			%source_path%\EntityLiving.java
mklink	%dest_path%\ItemHealingBark.java		%source_path%\ItemHealingBark.java
mklink	%dest_path%\ItemMagicLeaf.java			%source_path%\ItemMagicLeaf.java
mklink	%dest_path%\mod_Druid.java				%source_path%\mod_Druid.java


popd

pause
