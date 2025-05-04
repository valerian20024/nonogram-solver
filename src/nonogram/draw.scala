package nonogram
import nonogram.*
import Square.*
import Grid.*
import Solutions.*

/* Pretty prints all the Grids in the Solution
Returns a boolean value 'true' if there is at least a solution
*/
def draw(s: Solutions): Boolean = s match {
//  If Solutions is a List of Grid
    case Solved(grids) => 
        def printGrids(grids: List[Grid], allGrids: List[String]): List[String] = grids match
            case Nil => allGrids
//          Ignores and EmptyGrid (if something wrong happened) since it is not a solution
            case EmptyGrid :: next => printGrids(next, allGrids)
            case FilledGrid(rs, cs) :: tail => {
//              Kind of preventive case (e.g. Solved(List(FilledGrid(Nil, Nil))))
                if (rs.isEmpty || cs.isEmpty) printGrids(Nil, Nil) else {
                    val rowsAsString: List[String] = rs.map(row => row.map(square => squareToString(square)).mkString("[", "|", "]"))
                    val gridAsString: String = rowsAsString.mkString("\n")
                    printGrids(tail, gridAsString :: allGrids)
                }
            }
        val allGrids = printGrids(grids, Nil)
        if (allGrids.nonEmpty) {
            val allGridsAsString: String = allGrids.mkString("\n\n")
            print(allGridsAsString + "\n")
            true
        }
        else false
    
    case _ => false
}