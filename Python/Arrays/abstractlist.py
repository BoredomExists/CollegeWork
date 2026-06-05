from abstractcollection import AbstractCollection

class AbstractList(AbstractCollection):
    
    def __init__(self, sourceCollection):
        self.modCount = 0
        AbstractCollection.__init__(self, sourceCollection)
        
    def getModCount(self):
        return self.modCount
    
    def incModCount(self):
        self.modCount += 1
        
    def index(self, item):
        position = 0
        for data in self:
            if data == item:
                return position
            else:
                position += 1     
        if position == len(self):
            raise ValueError(str(item) + " not in list.")
        
    def add(self, item):
        self.insert(len(self), item)
        
    def remove(self, item):
        position = self.index(item)
        self.pop(position)