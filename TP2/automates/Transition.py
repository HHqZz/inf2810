import Miscellaneous
class Transition:
    nextState = False
    function = Miscellaneous.returnFalse

    def __init__(self):
        self


    def get_function (self):
        return self.function

    def set_function(self,function):
        self.function = function


    def get_nextState (self):
        return self.nextState

    def set_nextState (self,state ):
        self.nextState = state





    def execute (self,params):
        return self.function(params)if hasattr(self,"function")else False