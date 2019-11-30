package lab6

data class Path(val verts: List<Int>, val totalWeight: Int) : Comparable<Path> {
    override fun compareTo(other: Path): Int {
        return totalWeight.compareTo(other.totalWeight)
    }

    override fun toString(): String {
        return "Verts: $verts, Weight = $totalWeight"
    }
}