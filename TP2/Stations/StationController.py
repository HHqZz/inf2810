from Miscellaneous import lireFichier,debugPrint,debugMode,numberOfRequestCycle,maxRequestFiles
from Stations.request import request
from Stations.station import Station
from automates.RecogniseStateEngine import RecogniseStateEngine
from drone.Drone import Drone2, Drone1


class StationController:
    drones1List = False
    drones2List = False
    allDronesList = False
    stationList = False
    requetesATraiter = 0
    requetesTraitees = 0
    requetesInvalides = 0
    stationRecogniser = False
    drone1Deliveries = []
    drone2Deliveries = []

    # 1 on lis des nulles requetes si yen a plus --2 dépot des colis, retour au "hangar à drones" --3 équilibre en donnant des destinations aux drones en manque vers les stations en manque -- 4 on charge les drones en place -- 5 on fait faire les trajets

    def __init__(self):
        self.drone1List = [Drone1() for _ in range(10)]
        self.drone2List = [Drone2() for _ in range(5)]
        self.allDronesList = self.drone2List+self.drone1List # attention a ne pas changer l'ordre, ça permet d'envoyer les gros drones en priorité donc minimiser les trajets et permettre le transport de gros colis (>1000)
        codes = list(set(lireFichier('./DataFiles/CodesPostaux.txt')))  # un utilise un set pour virer les redondants
        self.stationRecogniser = RecogniseStateEngine.creerArbreAdresses('./DataFiles/CodesPostaux.txt')  # il y a une redondance de l'appel au fichier mais c'est juste histroire d'utiliser la fonction créerArbre comme demandé
        self.stationList = [Station(i) for i in codes]

    def traiterLesRequetes(self, fileName):
        self.lireReqetes(fileName)
        self.equilibrerFlotte()
        self.chargementDrones()
        self.déplacementDrones()
        self.depotDrones()
    def cycleSansRequetes(self):
        self.equilibrerFlotte()
        self.chargementDrones()
        self.déplacementDrones()
        self.depotDrones()


    def lireReqetes(self, fileName):
        if debugMode:
            global numberOfRequestCycle
            numberOfRequestCycle +=1
            fileName="requetes"+str(numberOfRequestCycle)+".txt"
            if numberOfRequestCycle>=maxRequestFiles:
                numberOfRequestCycle=0

        file = lireFichier('./DataFiles/' + fileName)
        if file == [] :
            return False
        for line in file:
            code1 = line.split(None, 1)[0]
            code2 = line.split(None, 2)[1]
            weight = line.split(None, 3)[2]
            self.stationRecogniser.reset()
            code1_Ok = self.stationRecogniser.run(code1)
            self.stationRecogniser.reset()
            code2_Ok = self.stationRecogniser.run(code2)
            self.stationRecogniser.reset()
            if code1_Ok and code2_Ok:
                self.requetesATraiter += 1
                requete = request(code2, code1, weight)
                startStation = self.getStationInStationList(
                    code1)  # on ne traite pas l'exeption, il ne devrait pas y en avoir dans le process
                startStation.add_requete(requete)
            else:
                self.requetesInvalides += 1
        return True

    def equilibrerFlotte(self):
        needingStations1,needingStations2 = self.getNeedingStations()
        for drone in self.drone2List: #on affecte ce qu'on peut de drone lourd en priorité
            if drone.get_nbrColis() == 0 and drone.get_position()==False and len(needingStations2) != 0:
                station = needingStations2.pop()
                drone.set_position(station)
                station.get_ocupant().append(drone)
                debugPrint(drone.get_Nom()+ " dispatché à "+str(station.get_nom()))
        for drone in self.drone1List: #on affecte ce qu'on peut de drone léger
            if drone.get_nbrColis() == 0 and drone.get_position()==False and len(needingStations1) != 0:
                station = needingStations1.pop()
                drone.set_position(station)
                station.get_ocupant().append(drone)
                debugPrint(drone.get_Nom()+ " dispatché à "+str(station.get_nom()))
        # si des requetes ou des drones ne sont pas affectés, on les affecte à la volée
        remainingStations = needingStations1+needingStations2
        if len(remainingStations)!=0:
            for drone in self.allDronesList:
                if drone.get_nbrColis() == 0 and drone.get_position()==False and len(remainingStations) != 0:
                    station = remainingStations.pop()
                    drone.set_position(station)
                    station.get_ocupant().append(drone)
                    debugPrint(drone.get_Nom()+ " dispatché à "+str(station.get_nom()))



    def chargementDrones(self):
        for drone in self.allDronesList:
            if drone.get_nbrColis() == 0 and drone.get_position():  # si un drone a vidé ses colis, on le décharge completement
                requetesLocales = drone.get_position().get_listeRequete()
                for request in requetesLocales:
                    if drone.get_nbrColis() == 0 and drone.getCapacite() - request.get_poids() >= 0: # la ou est le drone on prend la première requete qu'il est capable de prendre
                        drone.ajouterColis(request.get_poids()) # on ajoute le colis aau drone
                        self.requetesATraiter -= 1 # ca fait un colis de mois à traiter en statistiques globales
                        drone.set_destination(self.getStationInStationList(request.get_destination()))  #on donne une destination au drone
                        requetesLocales.remove(request) #on retire cette requete de la station
                if len(requetesLocales) != 0 and drone.get_nbrColis() == 1:
                    for request in requetesLocales: #on regarde ce qui reste
                        a= drone.getCapacite() - request.get_poids() >= 0
                        b= drone.get_destination().get_nom()
                        c= request.get_destination()
                        d = c==b
                        if drone.getCapacite() - request.get_poids() >= 0 and drone.get_destination().get_nom() == request.get_destination():#si je peux prendre la requete car j'ai le poids suffisant et que je vais ou ele doit aller
                            drone.ajouterColis(request.get_poids())
                            requetesLocales.remove(request)
                            self.requetesATraiter -= 1

    def déplacementDrones(self):
        for drone in self.allDronesList:
            if drone.get_destination() and drone.get_destination() != drone.get_position(): # si un drone a besoin d'etre déplacé
                self.drone1Deliveries.append(drone.get_nbrColis()) if drone.get_type() == 1 else self.drone2Deliveries.append(drone.get_nbrColis())  # c'est peut être pas intuitif mais c'est à ce moment là qu'on est le mieux placé pour savoir combien de requetes par drones sont prises
                debugPrint(drone.get_Nom()+ " se déplace de "+str(drone.get_position().get_nom())+ " à "+ str(drone.get_destination().get_nom()))
                drone.get_position().remove_occupant(drone) #la station de départ enregistre son départ
                drone.set_position(drone.get_destination()) #le drone arrive a destination
                drone.get_destination().get_ocupant().append(drone) #la station enregistre son arrivée

    def depotDrones(self):
        for drone in self.allDronesList:
            if drone.get_nbrColis() == 0:  # si un drone a vidé ses colis, on le décharge completement
                drone.reset()
            elif (drone.get_position() == drone.get_destination()) and (drone.get_position()!= False) : # si le drone est à destination et que sa position n'est pas le hangar de garage noté False
                drone.deliver()
                self.requetesTraitees += 1
                if drone.get_nbrColis() == 0:
                    drone.reset()


    def printStats(self):
        print("/* AFFICHAGE DES STATISTIQUES */")
        print("REQUETES TRAITEES :", end=" ")
        print(self.requetesTraitees)
        print("REQUETES INVALIDES :", end=" ")
        print(self.requetesInvalides)
        print("______________________________")
        print("REPARTITION DE LA FLOTTE")
        print("Quartier |  Drones faible capacite   |   Drones forte capacite")
        for station in self.stationList:
            if len(station.get_ocupant()) != 0:
                print(station.get_nom() + "   |              " + str(station.getDroneNumberByType(1)) + "            |             " + str(station.getDroneNumberByType(2)))
        print("______________________________")
        Drone1Requetes = 0
        Drone2Requetes = 0
        for element in self.drone1Deliveries:
            Drone1Requetes += element
        for element in self.drone2Deliveries:
            Drone2Requetes += element
        print("NOMBRE MOYEN DE COLIS PAR DRONE :")
        print("FAIBLE CAPACITE :", end=" ")
        print(0 if Drone1Requetes==0 else Drone1Requetes / len(self.drone1Deliveries))
        print("FORTE CAPACITE :", end=" ")
        print(0 if Drone2Requetes==0 else Drone2Requetes / len(self.drone2Deliveries))
        print("/* FIN */")


    def getNeedingStations(self):
        stationsNeeding1 = []
        stationsNeeding2 = []
        if self.requetesATraiter == 0:
            return stationsNeeding1,stationsNeeding2
        else:
            for station in self.stationList:
                if len(station.get_listeRequete())>0 :
                    destinationNumber1,destinationNumber2=station.getDestinationNumber()
                    if destinationNumber1 != 0:
                        for _ in range(destinationNumber1):
                            stationsNeeding1.append(station)
                    if destinationNumber2 != 0:
                        for _ in range(destinationNumber2):
                            stationsNeeding2.append(station)
            return stationsNeeding1,stationsNeeding2

    def getStationInStationList(self, stationName):
        for station in self.stationList:
            if station.get_nom() == stationName:
                return station
        return False
