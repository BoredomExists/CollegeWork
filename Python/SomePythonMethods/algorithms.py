from profiler import Profiler as p

def selectionSort(lyst, profiler):
    i = 0
    while i < len(lyst) - 1:
        minIndex = i
        j = i + 1
        while j < len(lyst):
            p.comparison()
            if lyst[j] < lyst[minIndex]:
                minIndex = j
            j += 1
        if minIndex != i:
            swap(lyst, minIndex, i, profiler)
            
def swap(lyst, i, j, profiler):
    p.exchange()
    temp = lyst[i]
    lyst[i] = lyst[j]
    lyst[j] = temp