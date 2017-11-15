from automates.state import State
from automates.transition import Transition
from miscellaneous import functionReturningTrueIfEqualsString


class stateEngine:
    startState = False
    currentState = False

    def __init__(self):
        self.startState

    def set_startState(state,self):
        self.startState = state

    def get_currentState(self):
        return self.currentState

    def reset(self):
        self.currentState = self.startState

    def input(self,params):
        for transition in self.currenState.getTransitionS():
            transitionExecutable = transition.execute(params)
            if transitionExecutable :
                self.currentState=transition.get_nextState()
                return self.currentState.get_isFinal()
        return "error"


    def inputwWileBuilding(self,params,transitionGenerator):
        for transition in self.currenState.getTransitionS():
            transitionExecutable = transition.execute(params)
            if transitionExecutable :
                self.currentState.set_isFinal(False)
                self.currentState=transition.get_nextState()
                return self.currentState.get_isFinal()
        newState = State()
        newTransition= Transition()
        newTransition.set_nextState(newState)
        newTransition.set_function(transitionGenerator(params))
        self.currentState.set_isFinal(False)
        self.currentState.addTransition(newTransition)
        self.currentState=newState




