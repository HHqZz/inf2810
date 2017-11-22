from Miscellaneous import functionReturningTrueIfEqualsString, lireFichier
from automates.StateEngine import StateEngine


class RecogniseStateEngine(StateEngine):
    def __init__(self):
        StateEngine.__init__(self)

    def run(self,string):
        for char in string:
            self.input(char)

        return self.currentState.get_isFinal()


    def build (self, params):
        for string in params:
            for char in string:
                self.inputWileBuilding(char,functionReturningTrueIfEqualsString)
            self.reset()
        self.reset()

    @staticmethod
    def creerArbreAdresses(path):
        codes = list(set(lireFichier(path)))
        arbre = RecogniseStateEngine()
        arbre.build(codes)
        arbre.reset()
        return arbre
