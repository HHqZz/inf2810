import static java.lang.System.out;
import java.util.Scanner;
import paquage.FonctionDrones;
import paquage.Graph;
import paquage.Hasse;
import java.io.IOException;

public class main 
{
	public static void affichage() {
		System.out.println("Menu:");
		System.out.println("(a) Drones");
		System.out.println("(b) Recettes");
		System.out.println("(c) Quitter");
		System.out.println("Veuillez enter un index:");
	}
	public static void affichageD() {
		System.out.println("Menu:");
		System.out.println("(a) Mettre a jour la carte");
		System.out.println("(b) Determiner le plus court chemin securitaire ");
		System.out.println("(c) Quitter");
		System.out.println("Veuillez enter un index:");
	}
	public static void affichageR() {
		System.out.println("Menu:");
		System.out.println("(a) Creer et afficher le graphe des recettes");
		System.out.println("(b) Generer et afficher le diagramme de Hasse");
		System.out.println("(c) Quitter");
		System.out.println("Veuillez enter un index:");
	}
	public static String forceABC() {
		Scanner scan = new Scanner(System.in);
		String choice=null;
		choice = scan.nextLine();
		if (!choice.equals("a")&&!choice.equals("b")&&!choice.equals("c")) {
			System.out.println("Invalid index");
			choice = forceABC();
		}
		return choice;
	}
	public static void main (String [] args ) throws IOException {
		
			affichage();
			Scanner scan = new Scanner(System.in);
			String choice=null;
			choice = scan.nextLine();
			while(!choice.equals("c")){
			switch (choice) {
				case "a":
					//System.out.println("Hello");
					Graph drone = FonctionDrones.creerGraphe();
					drone.lireGraph();
					affichageD();
					String choiceD= forceABC();
					while(!(choiceD.equals("c"))) {
						switch (choiceD) {
						case "a":
							String nameFile;
							System.out.println("Veuillez inserer le nom du fichier:");
							nameFile=scan.nextLine();
							drone=FonctionDrones.creerGraphe(nameFile);
							drone.lireGraph();
							affichageD();
							choiceD=forceABC();
							break;
						case "b":
							String pointDeDepart=null;
							System.out.println("Veuillez entrer un point de d�part:");
							pointDeDepart = scan.nextLine();
							String pointDeArrive=null;
							System.out.println("Veuillez entrer un point d'arriv�e:");
							pointDeArrive = scan.nextLine();
							int weight=1;
							System.out.println("Veuillez entrer le poids du colis: 1 pour l�ger, 2 pour moyen, 3 pour lourd ");
							weight=scan.nextInt();
							FonctionDrones.plusCourtChemin(drone,pointDeDepart,pointDeArrive,weight);
							affichageD();
							choiceD=forceABC();
							break;
						case "c":
							break;
						default :
							System.out.println("Invalid index");
							affichageD();
							choiceD=null;
							break;
						}
					} 
					affichage();
					choice=forceABC();
					break;
				case "b":
					
					/*//System.out.println("Hell");
					String choiceR=null;
					affichageR();
					do {
						switch (choiceR) {
						case "a":
							creerGrapheOriente("nomFichier");
							affichageR();
							break;
						case "b":
							genererHass();
							affichageR();
							break;
						case "c":
							break;
						default:
							System.out.println("Invalid index");
							affichageR();
							break;
						}
						
					}while(!(choiceR.equals("c"));*/
					affichage();
					choice=forceABC();
					break;
					
				case"c":
					break;
				default :
					System.out.println("Invalid index");
					affichage();
					break;
			}
		}
	}
	
}