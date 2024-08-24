@ECHO OFF
echo "====== BattleShip Script on Windows ======"
echo "Compiling..."
javac -sourcepath src src/main/java/Main.java
echo "Compiling test suit..."
javac -cp .\lib\junit-jupiter-api-5.4.2.jar;.\lib\apiguardian-api-1.0.0.jar;. -sourcepath src src/test/java/*.java
echo "Compilation complete. Generating Javadoc..."
cd .\src
javadoc -author -version -encoding UTF-8 src.main.java -d ..\docs
java -classpath .\src src.main.java.Main.java