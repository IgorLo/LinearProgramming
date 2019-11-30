package lab6

import org.graphstream.graph.Graph
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

object GraphCreator {
    val COSTS = listOf(0, 1900, 1700, 1300, 1100, 0)
    const val POWER = 0.85
    const val MACHINES = 10

    val vertexes = mutableListOf<Vertex>()
    val edges = mutableListOf<Edge>()
    val allPaths : SortedSet<Path> = TreeSet()
    var lastIndex = 0

    @JvmStatic
    fun main(args: Array<String>) {
        vertexes.clear()
        edges.clear()
        allPaths.clear()
        lastIndex = 0

        addVertex(0, 0) //Start with index 0
        addVertex(5, 0) //End with index 1

        for (i in 1..10)
            addVertex(1, i)

        for (i in 0..9)
            addVertex(2, i)

        for (i in 0..7)
            addVertex(3, i)

        for (i in 0..4)
            addVertex(4, i)

        for (vert in getVertexesOfLevel(1)) {
            edges.add(Edge(0, vert.id, calcCost(vert.quantity, COSTS[vert.level])))
        }

        for (vertLeft in getVertexesOfLevel(1)) {
            for (vertRight in getVertexesOfLevel(2)) {
                if (vertLeft.quantity + vertRight.quantity <= 10) {
                    edges.add(Edge(vertLeft.id, vertRight.id, calcCost(vertRight.quantity, COSTS[vertRight.level])))
                }
            }
        }

        for (vertLeft in getVertexesOfLevel(2)) {
            for (vertRight in getVertexesOfLevel(3)) {
                if (vertLeft.quantity + vertRight.quantity <= 10) {
                    edges.add(Edge(vertLeft.id, vertRight.id, calcCost(vertRight.quantity, COSTS[vertRight.level])))
                }
            }
        }

        for (vertLeft in getVertexesOfLevel(3)) {
            for (vertRight in getVertexesOfLevel(4)) {
                if (vertLeft.quantity + vertRight.quantity <= 10) {
                    edges.add(Edge(vertLeft.id, vertRight.id, calcCost(vertRight.quantity, COSTS[vertRight.level])))
                }
            }
        }

        for (vert in getVertexesOfLevel(4)) {
            edges.add(Edge(vert.id, 1, 0))
        }

//        printGraph()

//        val paths : Set<Path> = generateAllPaths()
        generateAllPaths()
        printAllPaths()
        displayGraph()

    }

    private fun displayGraph() {
        val graph = SingleGraph("Sapr")
        for (vert in vertexes){
            graph.addNode<Node>(vert.id.toString())
        }
        for (edge in edges){
            val addedEdge = graph.addEdge<org.graphstream.graph.Edge>(
                edges.indexOf(edge).toString(),
                edge.fromId.toString(),
                edge.toId.toString()
            )
        }
        graph.display()
    }

    private fun printAllPaths() {
        for (path in allPaths)
            println(path.toString())
    }

    private fun generateAllPaths() {
        val visitedVertexes: MutableSet<Int> = TreeSet()
        val pathList: MutableList<Int> = mutableListOf()

        pathList.add(0)

        generatePaths(0, 1, visitedVertexes, pathList)

    }

    private fun generatePaths(fromId: Int, toId: Int, visited: MutableSet<Int>, pathList: MutableList<Int>) {
        visited.add(fromId)
        if (toId == fromId){
//            println(pathList)
            addPath(pathList)
            visited.remove(fromId)
            return
        }
        for (edge in getEdgesFrom(fromId)){
            if (!visited.contains(edge.toId)){
//                pathList.add(edge.toId)
                pathList.add(getVertexById(edge.toId).quantity)
                pathList.add(edge.weight)
                generatePaths(edge.toId, toId, visited, pathList)
//                pathList.remove(edge.toId)
                pathList.removeAt(pathList.size - 1)
                pathList.removeAt(pathList.size - 1)
            }
        }
        visited.remove(fromId)
    }

    private fun addPath(pathList: List<Int>) {
        var totalWeight = 0
        val quantities = mutableListOf<Int>()
        var i = 0
        while (i < 7){
            totalWeight += pathList[i++]
            quantities.add(pathList[i++])
        }
        val path = Path(quantities, totalWeight)
        if (validatePath(path)){
            allPaths.add(path)
        }
    }

    private fun validatePath(path: Path): Boolean {
        if (path.verts.sum() != MACHINES)
            return false
        if (path.verts[0] < 1)
            return false
        if (path.verts[0] + path.verts[1] - 1 < 2)
            return false
        if (path.verts[0] + path.verts[1] + path.verts[2] - 3 < 3)
            return false
        if (path.verts[0] + path.verts[1] + path.verts[2] + path.verts[3] - 6 < 4)
            return false
        return true
    }

    private fun getVertexById(id: Int): Vertex {
        return vertexes.find { vertex -> vertex.id == id}!!
    }

    private fun getEdgesFrom(vertId: Int): List<Edge> {
        return edges.filter { edge -> edge.fromId == vertId }
    }

    private fun printGraph() {
        println("${vertexes.size} ${edges.size}")
        println("0 1")
        for (edge in edges){
            println("${edge.fromId} ${edge.toId} ${edge.weight}")
        }
    }

    private fun getVertexesOfLevel(level: Int): List<Vertex> {
        return vertexes.filter { v -> v.level == level }
    }

    private fun addVertex(level: Int, quantity: Int) {
        vertexes.add(Vertex(lastIndex++, level, quantity))
    }

    private fun calcCost(quantity: Int, price: Int): Int {
        return (price * quantity.toDouble().pow(POWER)).roundToInt()
    }

}