# test-generator

--Kompilace zdrojových kódů generátoru--
mvn clean compile assembly:single

--Manuální spuštění generátoru--
1. Nutnost mít ve stejném adresáři umístěn config.xml včetně správného nastavení (umístěn v root adresáři složky generator)
2. java -jar generator.jar

--Automatické spuštění generátoru--
V případě integrace do IS je nutné mít config.xml umístěn v lokálním repozitáři (repository/com/bc/generator/1.0/config.xml), jinak dochází k chybě:
"java.io.FileNotFoundException: Config file /*/.m2/repository/com/bc/generator/1.0/config.xml was not found."

--Spuštění informačního systému--
Pomocí souboru run.sh
./run.sh
Spustí se webový server Jetty a provedé se celý životní cyklus. Ve výchozím stavu je provedena integrace generátoru do IS.
