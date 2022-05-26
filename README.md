README: ACOL Project
Loïc SIMEON
Nicolas PEYRICHOU
Nathan SALLEYRETTE
Matthieu RAMBAUD

Grenoble INP-Ensimag, UGA

13/04/2021

Pour compiler le projet, il faut:

Il faut d'abord se placer dans le répertoire acol_2021-master puis lancer les commandes suivantes pour pouvoir jouer au jeu :

javac -d bin -classpath ojdbc6.jar -sourcepath src src/Main.java

java -classpath bin:ojdbc6.jar Main

Pour clean on peut lancer:
rm -rf bin/*.class


Le dossier “Rapport” contient un unique fichier au format PDF (Rapport-ACOL-2021.pdf) qui regroupe les documents demandés dans 
l’ordre suivant: 1. Cahier des charges, 2. Document d’analyse, 3. Document de conception, 5. Manuel Utilisateur et 6. Bilan
