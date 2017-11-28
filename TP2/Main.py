from Stations.StationController import StationController
from automates.RecogniseStateEngine import RecogniseStateEngine
from automates.Transition import Transition
from Miscellaneous import returnFalse, lireFichier, debugMode

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
if (debugMode):
    stationController = StationController()
    for _ in range(15):
        stationController.traiterLesRequetes('')
    stationController.printStats()
    stationController.cycleSansRequetes()
    stationController.cycleSansRequetes()
    stationController.printStats()
    stationController.cycleSansRequetes()
    stationController.cycleSansRequetes()
    stationController.cycleSansRequetes()
    stationController.cycleSansRequetes()
    stationController.printStats()
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
            choixFile = input("entrez nom du fichier (se trouvant dans le dossier DataFiles -> Ex :'requetes1.txt')\n\n"
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
