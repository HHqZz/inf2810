
global debugMode
debugMode = False
global numberOfRequestCycle
numberOfRequestCycle=0
global maxRequestFiles
maxRequestFiles=8

def debugPrint (thing):
    global debugMode
    if debugMode == True:
        print("----------------------debug----------------------->", end=" ")
        print(thing)

def returnFalse():
    return False

def functionReturningTrueIfEqualsString(string):
    def customFuntion (param):
        return param==string
    return customFuntion

#Fonction qui permet de lire un fichier dont le path est passe en parametre et return un tableau dont chaque element correspond a chaque ligne du fichier
def lireFichier(path):
    try :
        file = open(path,'r')
        with file:
            tableauDeLignes = file.read().splitlines()
        return tableauDeLignes
    except (FileNotFoundError,AttributeError,PermissionError):
        if "42" in path :
            print(funnyEasterEgg)
        print("le fichier demandé n'existe pas")

        return []

funnyEasterEgg = '''
,---.   .--.   ,-----.   .-------.    .-''-.   
|    \  |  | .'  .-,  '. \  _(`)_ \ .'_ _   \  
|  ,  \ |  |/ ,-.|  \ _ \| (_ o._)|/ ( ` )   ' 
|  |\_ \|  ;  \  '_ /  | |  (_,_) . (_ o _)  | 
|  _( )_\  |  _`,/ \ _/  |   '-.-'|  (_,_)___| 
| (_ o _)  : (  '\_/ \   |   |    '  \   .---. 
|  (_,_)\  |\ `"/  \  ) /|   |     \  `-'    / 
|  |    |  | '. \_/``".' /   )      \       /  
'--'    '--'   '-----'   `---'       `'-..-' '''
