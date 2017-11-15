def returnFalse():
    return False

def functionReturningTrueIfEqualsString(string):
    def customFuntion (param):
        return param==string
    return customFuntion
