// N° AUDITEUR = GET523233

package ProjetMm;

import java.util.Scanner;

public class IndicageNaturel {


    public static void main(String[] args) {
        char monTab [] = {'c','n','a','m','z','e','y','a','c','w','t'};
        int userChoice = 0;
         Scanner sc = new Scanner(System.in);

        do {
            System.out.println("\nQue voulez-vous faire ? \n 1/ Afficher mon tableau original \n 2/ Ajouter un élément par indice \n 3/ Supprimer un élément par indice \n 4/ Supprimer un élément par recherche d'élément \n 5/ Quitter le programme");
             userChoice = Integer.parseInt(sc.nextLine());

             if (userChoice == 1){affichageTab(monTab);}
             else if (userChoice == 2){
                 affichageTab(monTab);
                System.out.println("Element à ajouter : ");
                char monElement = sc.nextLine().charAt(0);
                System.out.println("A quel indice ? \n Pour info, min = 1 et max = " + monTab.length + "\n sinon pour ajouter à la fin mettre 0.");
                int monIndice = Integer.parseInt(sc.nextLine());
                if (monIndice == 0){
                    ajouterElementNoIndice(monTab,monElement);
                }
                else {ajouterElement(monTab,monIndice,monElement);}
            }
             else if (userChoice == 3){
                 affichageTab(monTab);

                System.out.println("A quel indice supprimer l'élément ? \n Pour info, min = 1 et max = " + monTab.length);
                int monIndice = Integer.parseInt(sc.nextLine());
             supprimerElement(monTab,monIndice);
             }
             else if (userChoice == 4){
                 affichageTab(monTab);
                 System.out.println("Element à supprimer : ");
                 char monElement = sc.nextLine().charAt(0);

                 supprimerElementExplicite(monElement, monTab);
             }
             else if (userChoice == 5){
                 System.out.println("Bye !");
                 break;
             }

            userChoice = 10;
        }
        while (userChoice<1 || userChoice>5);


    }

    // afficher un tabeau unidimensionnel
    static char [] affichageTab(char [] monTab){
        System.out.print(("|\t"));
        for(int i=0;i<monTab.length;i++){
            System.out.print(monTab[i] + "\t|\t");
        }
        System.out.println("\n");
        return monTab;
    }

    static char [] ajouterElement(char [] monTab, int monIndiceNat, char monElementAjoute){
        Scanner sc = new Scanner(System.in);

        // vérif de l'indice
        int monIndiceReel = monIndiceNat - 1;
        boolean verifIndice = false;
        while (verifIndice==false){
            if (monIndiceReel>monTab.length||monIndiceReel<0){
                System.out.println("Merci d'indiquer un indice valide, càd un entier > 0 et <= " + (monTab.length+1+1) + " : " );
                monIndiceNat = Integer.parseInt(sc.nextLine());
                monIndiceReel = monIndiceNat - 1;

            }
            else {
                verifIndice = true;
            }
        }

        //séparation du tableau initial
        char [] tab1 = new char[monIndiceReel];
        for (int i =0;i<monIndiceReel;i++){tab1[i] = monTab[i];};

        char [] tab2 = new char[monTab.length-monIndiceReel];
        for (int i =0;i<monTab.length - monIndiceReel;i++){tab2[i] = monTab[i+monIndiceReel];};

        char [] tabConcat = new char[tab1.length + tab2.length +1];

        //remplissage du tableau concaténé
        for (int i = 0; i<tab1.length;i++){
            tabConcat[i] = tab1[i];
        }
        tabConcat[monIndiceReel] = monElementAjoute;
        for (int i = 0;i<tab2.length;i++){
            tabConcat[tab1.length+1+i] = tab2[i];
        }
        System.out.println("Tableau avec élément ajouté : \n" );
        affichageTab(tabConcat);
        System.out.println("\n");


        return tabConcat;
    }

    static char [] ajouterElementNoIndice(char [] monTab, char monElementAjoute){
        char [] tabConcat = ajouterElement(monTab,monTab.length+1,monElementAjoute);
        return tabConcat;
    }

    static char [] supprimerElement(char [] monTab, int monIndiceNat){
        Scanner sc = new Scanner(System.in);

        // vérif de l'indice

        int monIndiceReel = monIndiceNat - 1;
        boolean verifIndice = false;
        while (verifIndice==false){
            if (monIndiceReel>monTab.length-1||monIndiceReel<0){
                System.out.println("Merci d'indiquer un indice valide, càd un entier > 0 et <= " + (monTab.length) + " : " );
                monIndiceNat = Integer.parseInt(sc.nextLine());
                monIndiceReel = monIndiceNat - 1;

            }
            else {
                verifIndice = true;
            }
        }

        //séparation du tableau initial
        char [] tab1 = new char[monIndiceReel];
        for (int i =0;i<monIndiceReel;i++){tab1[i] = monTab[i];};

        char [] tab2 = new char[monTab.length-monIndiceReel-1];
        for (int i =0;i<monTab.length-1 - monIndiceReel;i++){tab2[i] = monTab[i+1+monIndiceReel];};

        char [] tabConcat = new char[tab1.length + tab2.length];

        //remplissage du tableau concaténé
        for (int i = 0; i<tab1.length;i++){
            tabConcat[i] = tab1[i];
        }
        for (int i = 0;i<tab2.length;i++){
            tabConcat[tab1.length+i] = tab2[i];
        }
        System.out.println("Tableau avec élément supprimé : \n"); affichageTab(tabConcat);
        System.out.println("\n");

        return tabConcat;
    }

    static char[] supprimerElementExplicite(char element, char[] tabElement) {
        int i;
        int elementPosition = 0;
        char tabSupp [] = new char[0];
        boolean matchElement = false;
        System.out.println("Comparaisons : ");
        for (i = 0; i < tabElement.length; i++) {
            System.out.println(tabElement[i] + " et " + element);
            if (tabElement[i] == element) {
                matchElement = true;
                elementPosition = i+1;
                break;
            }
        }
        if (!matchElement) System.out.println("Pas d'élément trouvé");
        else {
            System.out.println("Match OK : élément trouvé \n");
            tabSupp = supprimerElement(tabElement, elementPosition);
        }
        return tabSupp;

    }

}
