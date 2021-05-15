package ProjetMm;

import java.util.Arrays;
import java.util.Scanner;


public class ProjetMasterMind {
    static String [] TAB_REF_COLORS = {
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
    static int NB_TOUR_JEU = 12;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tourCount = 0;
        boolean userWon = false;
        boolean mustEnd = false;
        int goodPlaceColor = 0;
        int goodColors = 0;


        String tabResult[][] = new String[13][5]; //résultat du tour
        String tabGoodResults[][] = new String[13][2]; //tableau des résultats couleurs
        String tabGeneral[][] = new String[13][7]; //résultats du jeu (tabResult + tabGoodResult)

        String tabColLabels[][] = {{"\t \t","1 \t","2 \t","3 \t","4 \t"}}; // labels colonnes
        String tabColGoodRes[][]={{"GoodPlace","GoodColor"}};
        String tabRowLabels[][] = new String[13][1]; // labels lignes



        //Génération tabRowLabels
        tabResult[0][0] = tabColLabels[0][0];
        for(int i=1;i<tabRowLabels.length;i++){
            tabResult[i][0] = "Tour n°" + i;
        }
        for(int j=1;j<tabColLabels[0].length;j++){
            tabResult[0][j]=tabColLabels[0][j];
        }
        for(int j=0;j< tabGoodResults[0].length;j++){
            tabGoodResults[0][j] = tabColGoodRes[0][j];
        }

        //Génération aléatoire de la série
        String[] combinaisonSecrete = generateRandomCombination();
       // System.out.println("Combi " + Arrays.toString(combinaisonSecrete));

        //info User
        System.out.println("But du jeu : \nTrouver la combinaison secrète de quatre couleurs DIFFERENTES en 12 coups. A chaque tour, le jeu renvoit les infos suivantes : ");
        System.out.println("- La combinaison choisie");
        System.out.println("- GoodColor = le nombre de bonnes couleurs mais mal placées");
        System.out.println("- GoodPlace = le nombre de bonnes couleurs bien placées");
        System.out.println("Remarque : les couleurs bien placées ne sont pas comtabilisées dans les couleurs mal placées et vice-versa.\nPour quitter au cours du jeu, taper 'quit'");
        String choixUser = "";
        String[] userChoice = {"yes","no"};
        while(!isIn(choixUser,userChoice))  {
            System.out.println(">> Démarrer le jeu ? yes/no");
            choixUser = sc.nextLine();
        }



        if (choixUser.equalsIgnoreCase("no")){
            System.out.println("Ok, à bientôt !");}

        else if (choixUser.equalsIgnoreCase("yes")) {
            //début du jeu
            do {
                System.out.println("\n>>Tour n°" + (tourCount + 1) + "/12");
                String tabColors[] = new String[NB_COLORS]; //on réinitialise la série utilisateur

                //---------------Choix de la série utilisateur-----------------------------------

                //remplissage du tableau user
                for (int i = 0; i < tabColors.length; i++) {
                    String choixCouleur = "color";
                    while (!isIn(choixCouleur, TAB_REF_COLORS) || isIn(choixCouleur, tabColors) || choixCouleur.equalsIgnoreCase("quit")) {
                        System.out.print("quit OU choix de la couleur n°" + (i + 1) + " (rouge, jaune, vert, bleu, orange, blanc, violet, fuschia) : ");
                        choixCouleur = sc.nextLine();
                        if(choixCouleur.equalsIgnoreCase("quit")){
                            System.out.println("\nOk, fin du jeu. Il fallait trouver : "+ Arrays.toString(combinaisonSecrete));
                            mustEnd = true;
                            break;
                        }
                            //caractérisation des erreurs pour l'user
                        else if (!isIn(choixCouleur, TAB_REF_COLORS)) System.out.println("Erreur d'entrée");
                        else if (isIn(choixCouleur, tabColors))
                            System.out.println("Couleur déjà choisie, entrer une autre couleur");

                    }
                    if(mustEnd) break;

                    tabColors[i] = choixCouleur;
                    tabResult[tourCount + 1][i + 1] = choixCouleur; //remplissage du tableau des résultats

                }
                if(mustEnd) break;

                System.out.println("Combinaison choisie = " + Arrays.toString(tabColors)); //retour pour vérif utilisateur

                //--------------------------- retours pour l'utilisateur ---------------------
                goodPlaceColor = 0;
                goodColors = 0;

                // vérif du nombre de couleurs bien placées.
                for (int i = 0; i < tabColors.length; i++) {
                    if (combinaisonSecrete[i].equals(tabColors[i])) {
                        goodPlaceColor += 1;
                    }
                }
                tabGoodResults[tourCount + 1][0] = goodPlaceColor + "/4 \t";

                // vérif si le joueur a gagné
                if (goodPlaceColor == 4) {
                    System.out.println("Bien joué, vous avez gagné ! " + Arrays.toString(combinaisonSecrete));
                    userWon = true;
                    sc.close();
                    break;
                }

                // nombre de bonnes couleurs mais mal placées
                for (int i = 0; i < tabColors.length; i++) {
                    if (isIn(tabColors[i], combinaisonSecrete) && !combinaisonSecrete[i].equals(tabColors[i])) {
                        goodColors += 1;
                    }
                }
                tabGoodResults[tourCount + 1][1] = goodColors + "/4 \t";


                //-------------------- fin de tour, on fait tourner le compteur de tour et on affiche le tableau de résultats
                tourCount += 1;
                System.out.println("Tourcount = " + tourCount);
                tabGeneral = concatTabs(tabResult, tabGoodResults);
                affichageTabDoubleEntree(tabGeneral, tourCount + 1);
            }

            //---------------------------Sortie de la boule do while si plus de 12 tours -------------------
            while (tourCount < NB_TOUR_JEU);
            if (!userWon && !mustEnd)
                System.out.println("Vous avez perdu... Voici la combinaison qu'il fallait trouver : \n" + Arrays.toString(combinaisonSecrete));
            sc.close();
        }
        };


//---------------------- fonctions -----------------------

        static String [][] concatTabs(String[][] tab1,String[][] tab2){
        int i,j,k;
        String tabConcat[][] = new String[tab1.length][tab1[0].length+tab2[0].length];
        for(i=0;i<tab1.length;i++){
            for (j=0;j<tab1[0].length;j++){
                tabConcat[i][j] = tab1[i][j];
            }
            for(k=0;k<tab2[0].length;k++){
                tabConcat[i][tab1[0].length+k]=tab2[i][k];
            }
        }
// affichage du tableau

        return tabConcat;

    }

        static String [][] affichageTabDoubleEntree(String [][] tabDoubleEntree,int nbLignes){
        for(int i=0;i<nbLignes;i++){
            for(int j=0;j<tabDoubleEntree[0].length;j++){
                System.out.print(tabDoubleEntree[i][j] + "\t");
            }
            System.out.print("\n");
        }
        return tabDoubleEntree;
    }

        static String [] generateRandomCombination() {
            String [] combination = new String[NB_COLORS];
            int currentPosition = 0;
            while(currentPosition!=NB_COLORS) {
                int indexRandom = (int)(Math.random()*TAB_REF_COLORS.length);
                String color = TAB_REF_COLORS[indexRandom];
                if(!isIn(color, combination)) {
                    combination[currentPosition] = color;
                    currentPosition++;
                }
            }
            return combination;
        }

        static boolean isIn(String iStringToFind, String [] iTab) {
            int size = iTab.length;
            for(int i=0;i<size;i++) {
                if(iStringToFind.equalsIgnoreCase(iTab[i])) return true;
            }
            return false ;

        }




}
