class State:
    isFinal = True
    transitionS = False
    name = False

    def __init__(self):
        self.transitionS = []

    def add_transition(self, transition):
        self.transitionS.append(transition)

    def rm_transition(self, transition):
        self.transitionS.remove(transition)

    def set_isFinal(self, final):
        self.isFinal = final

    def get_isFinal(self):
        return self.isFinal

    def get_transitionS(self):
        return self.transitionS

    def set_name(self,name):
        self.name=name

    def get_name(self):
        return self.name