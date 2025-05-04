package nonogram
import nonogram.*
import Square.*
import Grid.*
import Solutions.*
import scala.collection.immutable.ArraySeq
import scala.annotation.tailrec

//  Main function to solve the problem with partial solutions
def solve(rhs: List[Hint], chs: List[Hint]): Solutions = {
    if (rhs.isEmpty || chs.isEmpty) NoSolution else {
        val sol = solveCartesianProduct(rhs, chs)
        if (sol.isEmpty) NoSolution else Solved(sol)
    }
}

//  Returns the valid grids from cartesian product of all the possibilities from the different rows
def solveCartesianProduct(rhs: List[Hint], chs: List[Hint]): List[Grid] = {
    val width = chs.length  // width of the grids wanted
    val rows = rhs.map(x => getAllRowPossibilities(x, width))
    val products = cartesianProduct(rows)
    val grids = products.map(x => createGridADT(x))
    grids.filter(x => isGridValid(x, rhs, chs))
}

/* Computes all the possible Row not trivially wrong knowing a Hint and the length
 * of the row desired.
 */
def getAllRowPossibilities(hint: Hint, length: Int): List[Row] = {
    val nbFilled = hint.length
    val nbLonelySq = length - hint.sum - (if hint.length > 0 then hint.length - 1 else 0) // manages empty lists too
    val nbGroups = nbFilled + nbLonelySq

/*  List of list of int: ((0, 1, 2), (0, 1, 3), ...) 
    Represents the position of mini rows in the sequence of all the groups 
    Sorted.
*/
    val combs = List.range(0, nbGroups).combinations(hint.length).toList
    
//  Creates the mini rows
    val rows = hintToMiniRows(hint, List())  
    combs.map(x => buildRowFromComponents(nbGroups, x, rows))
}

/* Builds a row of Squares based on the different components (groups) of squares that exist
 * for this Row.
 */
def buildRowFromComponents(nbGroups: Int, combs: List[Int], miniRows: List[Row]): Row = {
    val hints = List.range(0, nbGroups)

    def iter(hints: List[Int], combs: List[Int], mrs: List[Row]): List[Square] = hints match {
//      Add "hints left" number of Blank
        case h :: Nil if combs == List.empty => List.fill(hints.length)(Blank)
        case h :: t if combs == List.empty => List.fill(hints.length)(Blank) // same as above
        case h :: Nil if h == combs.head => mrs.head  // last miniRow to add
        case h :: Nil if h != combs.head => List(Blank)  // last lonely square
//      In the middle of the computation, adds a miniRow
        case h :: t if h == combs.head => mrs.head ++ iter(hints.tail, combs.tail, mrs.tail) 
//      In the middle of the computation, adds a lonely square
        case h :: t if h != combs.head => List(Blank) ++ iter(hints.tail, combs, mrs) 
        case _ => Nil
    }
    iter(hints, combs, miniRows)
}

/*  Creates a list of Rows based on an Hint. This list represents the different elements of a possibility
 *  Does not include lonely squares.
 */
def hintToMiniRows(hint: Hint, lr: List[Row]): List[Row] = hint match {
    case Nil => Nil
//  Last row, rows are put in the same order as in hints
    case len :: Nil => (List.fill(len)(Filled) :: lr).reverse
//  Normal row with mandatory Blank square
    case len :: t => hintToMiniRows(t, (List.fill(len)(Filled) ++ List(Blank)) :: lr) 
}

// Computes the cartesian product of different lists
def cartesianProduct[A](in: List[List[A]]): List[List[A]] = {
    @tailrec
    def iter(acc: List[List[A]], r: List[List[A]]): List[List[A]] = {
        r match {
            case Nil => acc
            case l :: rLists => {
                val next = l.flatMap(i => acc.map(a => i+: a))
                iter(next, rLists)
            }
        }
    }
    iter(List(Nil), in.reverse)
}