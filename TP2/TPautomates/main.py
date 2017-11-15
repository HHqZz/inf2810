from automates.transition import Transition
from miscellaneous import returnFalse
from request import request
import os

print ("launch")
#a = returnFalse()
#print (a)

#trs = Transition()
#print trs.get_nextState()

#Fonction qui permet de lire un fichier dont le path est passe en parametre et return un tableau dont chaque element correspond a chaque ligne du fichier
def lireFichier(path):
    with open(path,'r') as codesPostaux:
        tableauDeCodesPostaux = codesPostaux.read().splitlines()
    return tableauDeCodesPostaux


lireFichier('../TP2ElementsFinis/CodesPostaux.txt')


#print(request.lirerequete('../TP2ElementsFinis/requetes4.txt'))


with open('../TP2ElementsFinis/requetes4.txt', 'r') as f:
    wordlist = [line.split(None, 3)[2] for line in f]
    print (wordlist)