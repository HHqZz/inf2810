from Stations.StationController import StationController
from automates.RecogniseStateEngine import RecogniseStateEngine
from automates.Transition import Transition
from Miscellaneous import returnFalse, lireFichier

choixMenu=""

# stationController = StationController()
# stationController.traiterLesRequetes('requetes1.txt')
# # stationController.printStats()
# stationController.depotDrones()
# stationController.equilibrerFlotte()
# stationController.chargementDrones()
# # stationController.printStats()
# stationController.déplacmentDrones()
# stationController.printStats()
# stationController.depotDrones()
# stationController.printStats()
stationController = False
print ('test')
while choixMenu != 'd':
    choixMenu = input("Menu Principal\n"
                      "(a) Creer l automate\n"
                      "(b) Traiter des requetes\n"
                      "(c) Afficher les statistiques\n"
                      "(d) Quitter\n\n"
                      "-->")
    if choixMenu == 'a':
        stationController = StationController()
    elif choixMenu == 'b':
        if stationController !=False:
            choixFile = input("entrez nom du fichier (se trouvant dans le dossier DataFiles)\n\n"
                              "-->")
            stationController.traiterLesRequetes(choixFile)
        else:
            print("___________________________\n"
                "l'automate n'a pas été créé\n"
                  "___________________________\n")
    elif choixMenu == 'c':
        if stationController !=False:
            stationController.printStats()
        else:
            print("___________________________\n"
                  "l'automate n'a pas été créé\n"
                  "___________________________\n")
    elif choixMenu != 'a' or choixMenu != 'b'or choixMenu != 'c':
        print("______________________________________________\n"
            "Invalid index. Veuillez entrer un nouvel index:\n"
              "______________________________________________")


print("Fin du programme")
