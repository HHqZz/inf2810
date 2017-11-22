from automates.State import State
from automates.Transition import Transition
from Miscellaneous import functionReturningTrueIfEqualsString


class StateEngine:
    startState = False
    currentState = False

    def __init__(self):
        self.startState = State()
        self.currentState = self.startState

    def set_startState(state,self):
        self.startState = state

    def get_currentState(self):
        return self.currentState

    def reset(self):
        self.currentState = self.startState

    def input(self,params):
        for transition in self.currentState.get_transitionS():
            transitionExecutable = transition.execute(params)
            if transitionExecutable :
                self.currentState=transition.get_nextState()
                return self.currentState.get_isFinal()
        return "error"


    def inputWileBuilding(self,params,transitionGenerator):
        if self.currentState.get_transitionS()!=False :
            for transition in self.currentState.get_transitionS():
                transitionExecutable = transition.execute(params)
                if transitionExecutable :
                    self.currentState.set_isFinal(False)
                    self.currentState=transition.get_nextState()
                    return self.currentState.get_isFinal()
        self.currentState.set_isFinal(False)
        newState = State()
        newState.set_name(params)
        newTransition= Transition()
        newTransition.set_nextState(newState)
        newTransition.set_function(transitionGenerator(params))
        self.currentState.add_transition(newTransition)
        self.currentState=newState


    def run (self,params):
        # to implement in childs
        return

    def build (self,params):
        # to implement in childs
        return
