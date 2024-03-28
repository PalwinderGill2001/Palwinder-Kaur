:: ---------------------------------------------------------------------
:: JAP COURSE - SCRIPT
:: ASSIGNMENTS - CST8221 - Fall 2022
:: ---------------------------------------------------------------------
:: Begin of Script (Assignments - F22)
:: ---------------------------------------------------------------------


CLS

:: LOCAL VARIABLES ....................................................
SET SRCDIR=src
SET BINDIR=bin

SET BINOUT=game-javac.out
SET BINERR=game-javac.err

SET BINOUT2=server-javac.out
SET BINERR2=server-javac.err

SET JARNAME=NumPuz.jar
SET JAROUT=numpuz-jar.out
SET JARERR=numpuz-jar.err

SET JARNAME2=NumPuzServer.jar
SET JAROUT2=numpuzserver-jar.out
SET JARERR2=numpuzsrver-jar.err

SET DOCDIR=doc
SET DOCPACK=Game
SET DOCOUT=game-javadoc.out
SET DOCERR=game-javadoc.err
SET MAINCLASSSRC=src/Game/NumPuz.java
SET MAINCLASSSRC2=src/Game/NumPuzServer.java
SET MAINCLASSBIN=Game.NumPuz
SET MAINCLASSBIN2=Game.NumPuzServer

@echo off

ECHO " _________________________________ "
ECHO "|     __    _  ___    ___  _      |"
ECHO "|    |  |  / \ \  \  /  / / \     |"
ECHO "|    |  | /   \ \  \/  / /   \    |"
ECHO "|    |  |/  _  \ \    / /  _  \   |"
ECHO "|  __|  |  / \  \ \  / /  / \  \  |"
ECHO "|  \____/_/   \__\ \/ /__/   \__\ |"
ECHO "|                                 |"
ECHO "| .. ALGONQUIN COLLEGE - 2022F .. |"
ECHO "|_________________________________|"
ECHO "                                   "
ECHO "[ASSIGNMENT SCRIPT ---------------]"

ECHO "1. Compiling ......................"
javac -Xlint -cp ".;%SRCDIR%" %MAINCLASSSRC% -d %BINDIR% > %BINOUT% 2> %BINERR%
javac -Xlint -cp ".;%SRCDIR%" %MAINCLASSSRC2% -d %BINDIR% > %BINOUT2% 2> %BINERR2%
:: ECHO "Running  ........................."
:: start java -cp ".;%BINDIR%;%JAVAFXDIR%/*" %MAINCLASSBIN%

ECHO "2. Creating Jars ..................."
cd bin
jar cvfe %JARNAME% %MAINCLASSBIN% . > %JAROUT% 2> %JARERR%
jar cvfe %JARNAME2% %MAINCLASSBIN2% . > %JAROUT2% 2> %JARERR2%

ECHO "3. Creating Javadoc ..............."
cd ..
javadoc -cp ".;%BINDIR%" -d %DOCDIR% -sourcepath %SRCDIR% -subpackages %DOCPACK% > %DOCOUT% 2> %DOCERR%

cd bin
ECHO "4. Running Jar ...................."
start java -jar %JARNAME%
start java -jar %JARNAME%
start java -jar %JARNAME2%
cd ..

ECHO "[END OF SCRIPT -------------------]"
ECHO "                                   "
@echo on

:: ---------------------------------------------------------------------
:: End of Script (Assignments - F22)
:: ---------------------------------------------------------------------
