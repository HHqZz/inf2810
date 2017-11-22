class Station :
    nomStation = False
    listeRequetes = False
    occupant = False

    def __init__(self):
        self.listeRequetes = []
        self.occupant= []

    def __init__(self,nom):
        self.listeRequetes = []
        self.occupant = []
        self.nomStation = nom

    def set_nom(self,nom):
        self.nomStation = nom

    def get_nom(self):
        return self.nomStation

    def set_listeRequete (self, list):
        self.listeRequetes=list

    def get_listeRequete (self):
        return self.listeRequetes

    def add_requete(self, requette):
        self.listeRequetes.append(requette)

    def remove_requete(self, requette):
        self.listeRequetes.remove(requette)

    def get_ocupant (self):
        return self.occupant

    def remove_occupant(self,occupant):
        self.occupant.remove(occupant)
    def set_occupant(self,occupant):
        self.occupant=occupant

    def getDestinationNumber(self): #on épliche les requetes pour dire combien de types de destinations différentes existent
        nbr = len(self.listeRequetes)
        if nbr<=1 :
            return nbr
        nbr = 0
        requestToIgnore =[]
        for requete1 in self.listeRequetes :
            sameDestinationDetected=False
            if requete1 not in requestToIgnore:
                for requete2 in self.listeRequetes :
                    if requete2 not in requestToIgnore:
                        if requete1.get_destination()==requete2.get_destination:
                            sameDestinationDetected = True
                            requestToIgnore.append(requete2) # vu qu'on aura requete1==requette1, il s'auto éliminera et crééra une destination
            if sameDestinationDetected :
                nbr += 1
        return nbr

    def getDroneNumberByType(self,type):
        i=0
        for drone in self.occupant:
            if drone.get_type()==type:
                i += 1
        return i