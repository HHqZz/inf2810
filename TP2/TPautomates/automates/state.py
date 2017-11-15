class State:
    isFinal = True
    transitionS = []

    def __init__(self):
        self

    def add_transiton(self, transition):
        self.transitionS.append(transition)

    def rm_transiton(self, transition):
        self.transitionS.remove(transition)

    def set_isFinal(self, final):
        self.isFinal = final

    def get_isFinal(self):
        return self.isFinal

    def get_transitionS(self):
        return self.transitionS
