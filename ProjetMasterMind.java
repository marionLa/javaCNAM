// N° AUDITEUR = GET523233

package ProjetMm;

import java.util.Arrays;
import java.util.Scanner;

/* Philosophie générale du code
* J'ai choisi de créer deux tableaux à double entrée :    tabGeneral = concatTabs(tabResult, tabGoodResults);
* - tabResult (5 cols et 13 rows) qui reprend la combinaision choisie par l'utilisateur ou tour n avec les libéllés de colonnes et de lignes (qui sont construits dans le code)
* - tabGoodResult (2 cols et 13 rows) qui reprend le nombre de couleurs bien placées et le nombre de bonnes couleurs.
* Ces deux tableaux ont des valeurs null et se remplissent au fur et à mesure des tours.
* > Enfin, tabGeneral est une concaténation des deux tableaux tabResult et tabGoodResults qui est affiché à l'utilisateur.
* Ainsi, il est possible de modifier le nombre de tours facilement.
* */

public class ProjetMasterMind {
    static String[] TAB_REF_COLORS = {
            "rouge",
            "jaune",
            "vert",
            "bleu",
            "orange",
            "blanc",
            "violet",
            "fuchsia"
    };
    static int NB_COLORS = 4;
    static int NB_TOUR_JEU = 3;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tourCount = 0;


        String[][] tabResult = new String[NB_TOUR_JEU+1][5]; //résultat du tour
        String[][] tabGoodResults = new String[NB_TOUR_JEU+1][2]; //tableau des résultats couleurs
        String[][] tabColResLabels = {{"\t \t", "1 \t", "2 \t", "3 \t", "4 \t"}}; // labels colonnes
        String[][] tabColGoodResLabels = {{"GoodPlace", "GoodColor"}}; //labels des colonnes résultats


        //Génération tabRowLabels (labels des lignes) en fonction du nombre de tours ou des libellés initialisés.
        makeTabGoodResultLabels(tabGoodResults[0], tabColGoodResLabels[0]);
        makeTabResultLabels(tabResult,tabColResLabels,NB_TOUR_JEU);

        //Génération aléatoire de la série
        String[] combinaisonSecrete = generateRandomCombination();
        System.out.println(Arrays.toString(combinaisonSecrete));
        //info User -> interface de jeu
        helpContext();

        if (yesNoChoice(sc, "Démarrer le jeu")) return;
        boolean hadHint = false;
        boolean newGame = true;
        int numberOfGames = 1;
        int winGames = 0;
        //début du jeu, boucle while sur NB_TOUR_JEU
            do {
                if (!newGame){
                    hadHint = false;
                    combinaisonSecrete = generateRandomCombination();
                    System.out.println(Arrays.toString(combinaisonSecrete));
                    tourCount = 0;
                    numberOfGames++;
                    System.out.println(numberOfGames);
                    newGame = true;

                }
                System.out.println("\n>> Tour n°" + (tourCount + 1) + "/" + NB_TOUR_JEU + " <<");
                String[] tabColors = new String[NB_COLORS]; //on réinitialise la série de l'utilisateur
                //---------------Choix de la série utilisateur----------------------------------
                // boucle while sur les choix des 4 couleurs
                {
                    int i = 0;
                    String choixCouleur;
                    int userEnter;

                        do {
                            System.out.print(">> quit / help / hint OU choix de la couleur n°" + (i + 1) + " (rouge, jaune, vert, bleu, orange, blanc, violet, fuchsia) : ");
                            choixCouleur = sc.nextLine();

                            // on teste l'entrée utilisateur avec la fonction qui renvoie un int de cas.
                            userEnter = testUserEnter(choixCouleur,TAB_REF_COLORS,tabColors);

                           switch (userEnter){
                               case 0: //cas le plus probable
                                   tabColors[i] = choixCouleur;
                                   tabResult[tourCount + 1][i + 1] = choixCouleur; //remplissage du tableau des résultats
                                   i++;
                                   continue;
                               case 1 : //le joueur lâche l'affaire
                                   if(yesNoChoice(sc,"Êtes-vous sûr de vouloir quitter")){
                                       break;}
                                   else{
                                   System.out.println("\nOk, fin du jeu. Il fallait trouver : " + Arrays.toString(combinaisonSecrete));
                                       return;}
                               case 2: //deux fois la même couleur...
                                   System.out.println("Couleur déjà choisie, entrer une autre couleur");
                                   break;
                               case 3: //mauvaise entrée
                                   System.out.println("Erreur d'entrée");
                                   break;
                               case 4: //help
                                   helpContext();
                                   break;
                               case 5: //
                                   if (!hadHint){ getHint(combinaisonSecrete);}
                                   else{
                                   System.out.println("Désolé, il semblerait que vous ayez déjà eu un indice...");
                                   }
                                   hadHint = true;
                                   break;
                               default: //cas bonus... on ne sait pas comment il peut arriver...
                                   System.out.println("Quelque chose ne colle vraiment pas... je ne sais même pas comment cette erreur pourrait arriver...");
                                   break;
                                    }
                        }
                        while (i < tabColors.length);
                    }


                //--------------------------- retours pour l'utilisateur ---------------------

                //affichage de la combinaison
                System.out.println("Combinaison choisie = " + Arrays.toString(tabColors)); //retour pour vérif utilisateur

                //nombre de couleurs bien placées + sortie si gagné
                int goodPlaceColor = getGoodPlaceColor(tourCount, tabGoodResults, combinaisonSecrete, tabColors);
                tabGoodResults[tourCount + 1][0] = goodPlaceColor + "/4 \t";
                boolean userWin = doesUserWin(goodPlaceColor,combinaisonSecrete);
                if (userWin) {
                    if (hadHint) System.out.println("Mais vous avez eu un indice...");
                    winGames++;
                    newGame = yesNoChoice(sc,"On refait une partie");
                    System.out.println(newGame);
                    if (newGame) return;
                else continue;
                }

                // nombre de bonnes couleurs mais mal placées
                int goodColors = getGoodColors(tourCount, tabGoodResults, combinaisonSecrete, tabColors);
                tabGoodResults[tourCount + 1][1] = goodColors + "/4 \t";


                //-------------------- fin de tour, on fait tourner le compteur de tour et on affiche le tableau de résultats
                tourCount += 1;
                String[][] tabGeneral = concatTabs(tabResult, tabGoodResults);
                affichageTabDoubleEntree(tabGeneral, tourCount + 1);

                 if (tourCount == NB_TOUR_JEU){
                     System.out.println("\nVous avez perdu... Voici la combinaison qu'il fallait trouver : \n" + Arrays.toString(combinaisonSecrete));
                     newGame = yesNoChoice(sc,"On refait une partie");
                 }
                }
            //---------------------------Sortie de la boule do while si plus de 12 tours -------------------
            while (tourCount < NB_TOUR_JEU || !newGame);
        System.out.println("Vous avez gagne " + winGames + " manches sur " + numberOfGames + ". \nBye !");
        sc.close();

    }



    //---------------------- fonctions -----------------------//

    //(re)afficher le fichier d'aide
    static void helpContext() {
        System.out.println("\nBut du jeu : \nTrouver la combinaison secrète de quatre couleurs DIFFERENTES en 12 coups. A chaque tour, le jeu renvoit les infos suivantes : ");
        System.out.println("- La combinaison choisie");
        System.out.println("- GoodColor = le nombre de bonnes couleurs mais mal placées");
        System.out.println("- GoodPlace = le nombre de bonnes couleurs bien placées");
        System.out.println("Remarque : les couleurs bien placées ne sont pas comtabilisées dans les couleurs mal placées et vice-versa.");
        System.out.println("Pour quitter au cours du jeu, tapez 'quit', \npour re-afficher ce menu d'aide, tapez 'help', \npour avoir un indice tapez 'hint', \nou sinon entrez des couleurs.\n");
    }

    // questions yes/no utilisateur

     static boolean yesNoChoice(Scanner sc, String userYesNoMessage) {
        String choixUser;
        String[] userChoice = {"yes", "no", "y", "n"};

        do {
             System.out.println(">> " + userYesNoMessage + " ? yes (Y) / no (N)");
             choixUser = sc.nextLine();
         }
        while (!isIn(choixUser, userChoice));


        if (choixUser.equalsIgnoreCase("no") || choixUser.equalsIgnoreCase("n")) {
            System.out.println("Ok !");
            return true;
        }
        return false;
    }

    static String[][]  makeTabResultLabels(String[][] tabResult, String[][] tabColResLabels, int NB_TOUR_JEU) {
        tabResult[0][0] = tabColResLabels[0][0];
        for (int i = 1; i < NB_TOUR_JEU+1; i++) {
            tabResult[i][0] = "Tour n°" + i;
        }
        for (int j = 1; j < tabColResLabels[0].length; j++) {
            tabResult[0][j] = tabColResLabels[0][j];
        }

        return tabResult;

    }

    static String[]  makeTabGoodResultLabels( String[] tabGoodResult,String[] tabColGoodResLabel) {

        for (int j = 0; j < tabGoodResult.length; j++) {
            tabGoodResult[j] = tabColGoodResLabel[j];
        }
        return tabGoodResult;
    }

    static int getGoodColors(int tourCount, String[][] tabGoodResults, String[] combinaisonSecrete, String[] tabColors) {
        int goodColors = 0;
        for (int i = 0; i < tabColors.length; i++) {
            if (isIn(tabColors[i], combinaisonSecrete) && !combinaisonSecrete[i].equals(tabColors[i])) {
                goodColors += 1;
            }
        }

        return goodColors;
    }

    static int getGoodPlaceColor(int tourCount, String[][] tabGoodResults, String[] combinaisonSecrete, String[] tabColors) {
        int goodPlaceColor = 0;
        // vérif du nombre de couleurs bien placées.
        for (int i = 0; i < tabColors.length; i++) {
            if (combinaisonSecrete[i].equals(tabColors[i])) {
                goodPlaceColor += 1;
            }
        }


        return goodPlaceColor;
    }

    // vérif si le joueur a gagné

    static boolean doesUserWin(int goodPlaceColor, String[] combinaisonSecrete){
    boolean userWin = false;
    if (goodPlaceColor == 4) {
        System.out.println("Bien joué, vous avez gagné ! " + Arrays.toString(combinaisonSecrete));
        userWin = true;

    }
        return userWin;
}

    // concaténation de deux tableaux

    static String[][] concatTabs(String[][] tab1, String[][] tab2) {
        int i, j, k;
        String[][] tabConcat = new String[tab1.length][tab1[0].length + tab2[0].length];
        for (i = 0; i < tab1.length; i++) {
            for (j = 0; j < tab1[0].length; j++) {
                tabConcat[i][j] = tab1[i][j];
            }
            for (k = 0; k < tab2[0].length; k++) {
                tabConcat[i][tab1[0].length + k] = tab2[i][k];
            }
        }

        return tabConcat;

    }

// affichage d'un tableau à double entrée

    static void affichageTabDoubleEntree(String[][] tabDoubleEntree, int nbLignes) {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < tabDoubleEntree[0].length; j++) {
                System.out.print(tabDoubleEntree[i][j] + "\t");
            }
            System.out.print("\n");
        }
    }

    //génération d'une combinaison de couleurs aléatoire

    static String[] generateRandomCombination() {
        String[] combination = new String[NB_COLORS];
        int currentPosition = 0;
        while (currentPosition != NB_COLORS) {
            int indexRandom = (int) (Math.random() * TAB_REF_COLORS.length);
            String color = TAB_REF_COLORS[indexRandom];
            if (!isIn(color, combination)) {
                combination[currentPosition] = color;
                currentPosition++;
            }
        }
        return combination;
    }

    // recherche d'un chaine de caractère dans une autre

    static boolean isIn(String iStringToFind, String[] iTab) {
        for (String s : iTab) {
            if (iStringToFind.equalsIgnoreCase(s)) return true;
        }
        return false;

    }

    static int testUserEnter(String choixCouleur, String[] TAB_REF_COLORS, String[] tabColors  ){
        int userEnter;
        if(isIn(choixCouleur, tabColors)){userEnter = 2;}
        else if (isIn(choixCouleur,TAB_REF_COLORS)){userEnter=0;}
        else if (choixCouleur.equalsIgnoreCase("quit")){userEnter = 1;}
        else if(choixCouleur.equalsIgnoreCase("help")){userEnter = 4;}
        else if(choixCouleur.equalsIgnoreCase("hint")){userEnter = 5;}
        else if(!isIn(choixCouleur,TAB_REF_COLORS)){userEnter=3;}

        else userEnter = 1000;

    return userEnter;
}

static String getHint(String[] randomCombination){
    int position = (int) (Math.random() * (randomCombination.length));
    String theHint = randomCombination[position];
    System.out.println("Indice : l'une des couleurs est " + theHint);
    return  theHint;

}



}
