package tourOfScala

class Graph {
  class Node(name: String) {
    val node = name
    var connectedNodes = List.empty[Graph#Node]
    def connectTo(node: Graph#Node): Unit = {
      if (connectedNodes.find(node.equals).isEmpty) {
        connectedNodes = node :: connectedNodes
      }
    }

    def printConnectedNodes: Unit = {
      print(s"${node}\nConnected nodes: ")
      connectedNodes.foreach { n =>
        print(s"${n.node}\t")
      }
      println()
    }

  }
  var nodes = List.empty[Node]
  def newNode(name: String): Node = {
    val res = new Node(name)
    nodes = res :: nodes
    res
  }

  def printGraph: Unit = {
    nodes.foreach { n =>
      n.printConnectedNodes
    }
  }
}

object InnerClasses extends App {

  val graph = new Graph
  val node1 = graph.newNode("Node 1")
  val node2 = graph.newNode("Node 2")
  val node3 = graph.newNode("Node 3")
  node1.connectTo(node2)
  node3.connectTo(node1)

  val graph2 = new Graph
  val node4 = graph2.newNode("Graph2 Node 1")
  // cannot connect nodes from different Graph object
  //  node4.connectTo(node1)

  // add different type
  // Before: `List.empty[Node]`
  // Now:    `List.empty[Graph#Node]
  node1.connectTo(node4)

  graph.printGraph
}
