from arrays import Array
from abstractlist import AbstractList

class ArraySortedList(AbstractList):
    DEFAULT_CAPACITY = 10
    
    def __init__(self, sourceCollection = None):
        self.items = Array(ArraySortedList.DEFAULT_CAPACITY)
        AbstractList.__init__(self, sourceCollection)
        
    def __iter__(self):
        cursor = 0
        while cursor < len(self):
            yield self.items[cursor]
            cursor += 1
            
    def __getitem__(self, i):
        if i < 0 or i >= len(self):
            raise IndexError("List index out of range")
        return self.items[i]
    
    def __contains__(self, item):
        left = 0
        right = len(self) - 1
        while left <= right:
            midPoint = (left + right) // 2
            if self.items[midPoint] == item:
                return True
            elif self.items[midPoint] > item:
                right = midPoint - 1
            else:
                left = midPoint + 1
            return False
    
    def clear(self):
        self.size = 0
        self.items = Array(ArraySortedList.DEFAULT_CAPACITY)
    
    def pop(self, i = None):
        if i == None: i = len(self) - 1
        if i < 0 or i >= len(self):
            raise IndexError("List index out of range")
        item = self.items[i]
        for j in range(i, len(self) - 1):
            self.items[j] = self.items[j + 1]
        self.size -= 1
        self.incModCount()
        return item
    
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
        if self.isEmpty() or item >= self.items[len(self) - 1]:
            self.items[len(self)] = item
        else:
            targetIndex = 0
            while item > self.items[targetIndex]:
                for i in range(len(self), targetIndex, -1):
                    self.items[i] = self.items[i - 1]
                self.items[targetIndex] = item
                self.size += 1
                    
    
        
   