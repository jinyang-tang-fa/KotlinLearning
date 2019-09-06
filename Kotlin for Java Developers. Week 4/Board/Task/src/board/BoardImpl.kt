package board

import board.Direction.*

open class SquareBoardImpl(override val width: Int): SquareBoard {
    private val cells: Collection<Cell> = (1..width).flatMap { i ->
        (1..width).map { j -> Cell(i, j) }
    }

    override fun getAllCells(): Collection<Cell> = cells

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return getAllCells().firstOrNull { it == Cell(i, j)}
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j) ?: throw IllegalArgumentException("No Cell found at $i, $j")
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val test = jRange.applyBound(width)
        return jRange.applyBound(width).flatMap { j -> getAllCells().filter { it.i == i && it.j == j } }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.applyBound(width).flatMap { i -> getAllCells().filter { it.i == i && it.j == j } }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction) {
            UP -> getCellOrNull(this.i - 1, this.j)
            DOWN -> getCellOrNull(this.i + 1, this.j)
            LEFT -> getCellOrNull(this.i, this.j - 1)
            RIGHT -> getCellOrNull(this.i, this.j + 1)
        }
    }



}

fun IntProgression.applyBound(width: Int): IntProgression = when {
    last > width -> first..width
    first > width -> width..last
    else -> this

}

class GameBoardImpl<T>(width: Int): SquareBoardImpl(width), GameBoard<T> {
    private val board = getAllCells().map {it to null}.toMap<Cell, T?>().toMutableMap()

    override operator fun get(cell: Cell): T? = board[cell]
    override operator fun set(cell: Cell, value: T?) {
        board[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return board.filter { predicate(it.value) }.map { it.key }
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return board.filter { predicate(it.value) }.keys.firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return board.any { predicate(it.value) }
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return board.all { predicate(it.value) }
    }
}


fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

