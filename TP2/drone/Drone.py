from Miscellaneous import debugPrint
global noDrone
noDrone = 0
class Drone:
    capaciteMaximal = 1000
    capacite = capaciteMaximal
    destination = False
    position = False
    nbrColis = 0
    nom = "Drone "

    def __init__(self):
        global noDrone
        self.nom =self.nom + str(noDrone)
        noDrone +=1

    def get_Nom(self):
        return self.nom

    def getCapacite(self):
        return self.capacite

    def recevoirDestination(self, destination):
        self.destination = destination

    def ajouterColis(self, weight):
        debugPrint(self.nom+" prend un colis")
        if self.capacite-weight>= 0 :
            self.nbrColis += 1
            self.capacite -= weight
        else: return False

    def reset(self):
        self.capacite = self.capaciteMaximal
        self.nbrColis = 0
        if self.position !=False :
            self.position.remove_occupant(self)
        self.position = False
        self.destination=False

    def get_nbrColis(self):
        return self.nbrColis

    def deliver(self):
        debugPrint(self.nom+" dépose un colis")
        self.nbrColis -= 1

    def set_destination(self,destination):
        self.destination=destination

    def get_destination(self):
        return self.destination

    def set_position(self,position):
        self.position=position

    def get_position(self):
        return self.position

    def get_type(self):
        return 0
class Drone1(Drone):
    capaciteMaximal = 1000

    def __init__(self):
        super(Drone1, self).__init__()

    def get_type(self):
        return 1

class Drone2(Drone):
    capaciteMaximal = 5000

    def __init__(self):
        super(Drone2, self).__init__()


    def get_type(self):
        return 2