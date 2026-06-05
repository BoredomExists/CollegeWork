"""
File: testdirected.py

Project 12.1

Tests preconditions on methods.

Uncomment lines of code to test for exceptions.
"""

from graph import LinkedDirectedGraph

# Create a directed graph using an adjacency list
g = LinkedDirectedGraph()

# Add vertices labeled A, B, and C to the graph and print it      
g.addVertex("A")
g.addVertex("B")
g.addVertex("C")
print("Expect vertices ABC and no edges: \n" + str(g))

# Insert edges with weight 2.5 and print the graph
g.addEdge("A", "B", 2.5)
g.addEdge("B", "C", 2.5)
g.addEdge("C", "B", 2.5)
print("Expect same vertices and edges AB BC CB all with weight 2.5: \n" + str(g))

#g.addVertex("A")

#g.addEdge("A", "D", 2.5)

#g.addEdge("A", "B", 2.5)

print("Expect A: ", g.getVertex("A"))
#print(g.getVertex("D"))

print("Edge from A to B:", g.getEdge("A", "B"))
print("Edge from B to A:", g.getEdge("B", "A"))
#print(g.getEdge("D", "A"))

print("Incident edges of A:", list(map(str, g.incidentEdges("A"))))
#print(list(map(str, g.incidentEdges("D"))))
#print(list(map(str, g.neighboringVertices("D"))))

