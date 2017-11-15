#classe requete

class request:

#attributs request :
    destination=[]
    origine=[]
    poids=[]


#Methodes :

    def __init__(self):
        self


    def lirerequete(path):
        with open(path, 'r') as f:
            origine = [line.split(None, 1)[0] for line in f]
            destination = [line.split(None, 2)[1] for line in f]
            poids = [line.split(None, 3)[2] for line in f]

