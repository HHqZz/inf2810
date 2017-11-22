#classe requete
class request:

    #attributs request :
    destination=False
    origine=False
    poids=False


    #Methodes :

    def __init__(self):
        self
    def __init__(self,destination,origine,poids):
        self.destination=destination
        self.origine=origine
        self.poids=int(poids)

    def get_destination(self):
        return self.destination
    def set_destination(self, destination):
        self.destination=destination


    def get_origine(self):
        return self.origine
    def set_origine(self, origine):
        self.origine=origine


    def get_poids(self):
        return self.poids
    def set_poids(self, poids):
        self.poids=int(poids)


    # def lirerequete(path):
    #     a = {}
    #     with open(path, 'r') as f:
    #         a.origine = [line.split(None, 1)[0] for line in f]
    #         a.destination = [line.split(None, 2)[1] for line in f]
    #         a.poids = [line.split(None, 3)[2] for line in f]
    #     return a