class Node(object):
    def __init__(self, data, next = None):
        self.data = data
        self.next = next

class LinkedList():
    def __init__(self):
        self.head = None
        
    def PrintListLength(self):
        head = None
        counter = 0
        for count in range(1,9):
            head = Node(count, head)
        
        while head != None:
            counter += 1
            print(head.data)
            head = head.next
        
        print("\n" + "Number of Nodes:", counter)
        
        