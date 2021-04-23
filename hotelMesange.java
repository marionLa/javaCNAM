package DM;
import java.util.Scanner;

public class hotelMesange {
    public static void main(String[] args) {

//        Initialisation des variables

        Scanner sc = new Scanner(System.in);
        int choixUtilisateur = 0;
        int i=0;

//        Initialisation du tableau d'occupation des chambres et le tableau des disponibilités

        // int[] tabOccCh = {4,2,0,2,4}; //hotel plein
        // int[] tabOccCh = {0,0,0,0,0}; //hotel vide
         int[] tabOccCh = {0,2,1,0,0}; //cas du TD
        // int[] tabOccCh = {4,0,4,2,4}; //une chambre de 2 disponible
        // int[] tabOccCh = {4,2,0,2,4}; //une chambre de 4 disponible
        // int[] tabOccCh = {4,2,0,0,4}; //une chambre de 4 et une chambre de 2 disponibles.

 // départ du programme pour l'utilisateur, avec MAJ des dispos à chaque retour de la boucle while

        while (choixUtilisateur !=3) {
            System.out.println("\n  _______________________  \n Etat de l'occupation des chambres : \n **************");
                for (i = 0; i < tabOccCh.length; i++) {
                    System.out.println("Chambre " + (i + 1) + ": " + tabOccCh[i] + " personne(s)");
                }

// on vérifie s'il y a des chambres disponibles => plus possible de remplir de chambre si l'hotel est plein

            int j = 0;
            boolean hotelPlein = true;
            for (i = 0; i < tabOccCh.length; i++) {
                if (tabOccCh[i] == 0) {
                    // System.out.println("\n Il y a au moins une chambre de dispo");
                    j = i; // on anticipe pour le cas où il y a une ou deux personnes dans le groupe pour avoir le numéro de chambre (pour l'attribution des chambres)
                    hotelPlein = false;
                    break;
                }
            }

// on vérifie si l'hôtel est vide => plus possible de libérer de chambre

            boolean hotelVide = true;
            //vérification que l'hôtel n'est pas vide
            for (i = 0; i < tabOccCh.length; i++) {
                if (tabOccCh[i] != 0) {
                    hotelVide = false;
                    break;
                }
            }

          System.out.println("\n  _______________________  \n");

//       Distinction des cas > si l'hôtel a une chambre disponible > si l'hotel est plein > si l'hôtel est vide

            if (hotelPlein) {
                System.out.println("Hotel plein, pas d'arrivée possible. \n \n Que voulez-vous faire ? \n *************** \n Gérer un départ = 2 \n Quitter le programme = 3");
                choixUtilisateur = Integer.parseInt(sc.nextLine());
                while (choixUtilisateur != 2 && choixUtilisateur != 3 ) {
                    System.out.println("Merci d'entrer un choix valide : 2|3");
                    choixUtilisateur = Integer.parseInt(sc.nextLine());
                }

            } else if (hotelVide) {
                System.out.println("Hotel vide, pas de départ possible. \n \n Que voulez-vous faire ? \n *************** \n Gérer une arrivée = 1 \n Quitter le programme = 3");
                choixUtilisateur = Integer.parseInt(sc.nextLine());
                while (choixUtilisateur != 1 && choixUtilisateur != 3 ) {
                    System.out.println("Merci d'entrer un choix valide : 1|3");

                    choixUtilisateur = Integer.parseInt(sc.nextLine());
                }
            }

            else {
                System.out.println("Que voulez-vous faire ? \n ************* \n Gérer une arrivée = 1 \n Gérer un départ = 2 \n Quitter le programme = 3");
                choixUtilisateur = Integer.parseInt(sc.nextLine());
                while (choixUtilisateur != 1 && choixUtilisateur != 2 && choixUtilisateur != 3 ) {
                    System.out.println("Merci d'entrer un choix valide : 1|2|3");

                    choixUtilisateur = Integer.parseInt(sc.nextLine());
                }
            }

            System.out.println("Choix = " + choixUtilisateur);


//      Choix n°3 de quitter le programme

            if (choixUtilisateur == 3) {
                System.out.println("Fin du programme, à bientôt !");
            }

//      Choix n°1 : gestion des arrivées

            if (choixUtilisateur == 1) {
                //     Nombre de personnes du groupe

                int nbrPers = 0;
                System.out.println("Nombre de personnes du groupe (entre 1 et 4 personnes) ?");
                nbrPers = Integer.parseInt(sc.nextLine());

                while (nbrPers <= 0 || nbrPers > 4) {
                    System.out.println("Merci d'entrer un choix valide : 1|2|3|4");
                    nbrPers = Integer.parseInt(sc.nextLine());
                }

                System.out.println("\n  _______________________  \n");

                // Pour une ou deux personnes, on est sûr d'avoir une chambre dispo puisque déjà vérifié stocké dans j, donc attribution.

                if (nbrPers == 1 || nbrPers == 2) {
                    tabOccCh[j] = nbrPers;
                    System.out.println("OK, groupe placé dans la chambre N°" + (j + 1));
                }

                // Pour trois ou quatre personnes, vérifier la disponibilité des chambres impaires par le tableau des dispos.

                else {
                    for (i = 0; i < tabOccCh.length; i++) {
                        // on vérifie si i est pair et que la chambre est libre => chambre impaire donc 4 personnes, sinon chambre de deux personnes donc impossible.
                        if (i%2==0 && tabOccCh[i]==0) {
                            tabOccCh[i] = nbrPers;
                            System.out.println("OK, groupe placé dans la chambre N°" + (i + 1));
                            hotelPlein=false;
                            break;
                        } else {
                            hotelPlein = true;

                        }
                    }

                }

                if (hotelPlein) {
                    System.out.println("Pas de chambre disponible pour 3 ou 4 personnes");
                }
            }


//      Choix n°2 : gestion des départs (on a vérifié que l'hôtel n'est pas vide)

            if (choixUtilisateur == 2) {
                int numCh = 0;

                System.out.println("Numéro de chambre qui se libère ?");
                numCh = Integer.parseInt(sc.nextLine());

                // on vérifie que le numéro de chambre existe et que la chambre n'est pas vide
                while (numCh <= 0 || numCh > 5) {
                    System.out.println("Merci d'entrer un numéro de chambre valide 1|2|3|4|5");
                    numCh = Integer.parseInt(sc.nextLine());

                }

                if (tabOccCh[numCh - 1] == 0) {


                    while (tabOccCh[numCh - 1] == 0) {
                        System.out.println("Cette chambre n'est pas occupée, merci d'entrer un numéro valide");
                        numCh = Integer.parseInt(sc.nextLine());
                    }
                }


                tabOccCh[numCh - 1] = 0;
                System.out.println("OK, chambre N°" + numCh + " libre à présent");
            }
        }

        System.out.println("\n  _______________________  \n");


        sc.close();

    }
}
