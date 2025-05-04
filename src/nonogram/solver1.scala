package nonogram
import nonogram.*
import Square.*
import Grid.*
import Solutions.*
import scala.math.pow
import scala.annotation.tailrec

def solveNaive(rh: List[Hint], ch: List[Hint]): Solutions = {
    if (rh.isEmpty || ch.isEmpty) NoSolution else {
//      Arguments are width and height of grids
        val everything = buildSolutionsNaive(ch.length, rh.length) 

//      Filter to only keep the values that fit given hints
        val sol = everything.filter(x => isGridValid(x, rh, ch))
        if (sol.isEmpty) NoSolution else Solved(sol)
    }
}
    
//  Builds all solutions that have the given shape (width x height), returns a list of Grid ADTs
def buildSolutionsNaive(width: Int, height: Int): List[Grid] = {
    if (width == 0 || height == 0) Nil else {
//      Compute all the possibilities as a binary number
        val pbSize = pow(2.0, width.toDouble * height.toDouble).toInt
        val everything = Range(0, pbSize).toList
        val lls = everything.map(x => buildListSquareFromInt(x, width * height))

//      Cut the List of Squares (i.e. a Row) into sublists of squares to create a List of List of Square (i.e. a Grid)
        val allTheGrids = lls.map(x => buildGridFromList(x, width))

//      Creates the list of Grid ADT
        allTheGrids.map(x => createGridADT(x))
    }
}

// Creates a Grid ADT based on a list of lists of square.
def createGridADT(lls: List[List[Square]]): Grid = lls match {
    case Nil => EmptyGrid
    case h :: t => FilledGrid(lls, lls.transpose)
}

//  From a list of squares (binary nb) builds a grid (formatting the list of squares into a list of list of squares)
def buildGridFromList(l: List[Square], width: Int): List[List[Square]] = {
    @tailrec
    def iter(ls: List[Square], w: Int, nl: List[List[Square]]): List[List[Square]] = ls match {
        case Nil => nl
        case h :: t => iter(ls.drop(w), w, ls.splitAt(w).head :: nl)
    }
    if (width == 0) Nil else {
        val LLS = iter(l, width, Nil).reverse
        LLS
    }
}

//  Converts an integer value to a Row and adds padding to make the row fits width
def buildListSquareFromInt(n: Int, width: Int): List[Square] = {
    @tailrec
    def iter(n: Int, l: List[Square]): List[Square] = {
        if n == 0 then l else {
            val r = n % 2
            r match {
                case 0 => iter(n / 2, List(Blank) ++ l)
                case 1 => iter(n / 2, List(Filled) ++ l)
            }
        }
    }
    if (width == 0) Nil else {
        val raw = iter(n, List())

//      Padding step (to format the list)
        if raw.length == width then raw
        else List.fill(width - raw.length)(Blank) ++ raw
    }
}

// Creates hints deduced from a complete grid (Filled-In Grid)
def hints(grid: Grid): Option[(List[Hint], List[Hint])] = grid match {
    case FilledGrid(rs, cs) => {
        val rowHints = rs.map(row => computeHints(row))
        val colHints = cs.map(col => computeHints(col))
        Some(rowHints, colHints)
    }
    case EmptyGrid => None
}

//  Computes recursively a List of Int that corresponds to the hints for a List of Square that forms a Row
def computeHints(row: Row): Hint = {
    @tailrec
    def computeHintsRec(row: Row, currGroupLength: Int, hints: Hint): Hint = row match {
        case Nil => if (currGroupLength > 0) hints :+ currGroupLength else hints
        case square :: tail => square match {
            case Filled => computeHintsRec(tail, currGroupLength + 1, hints)
            case Blank => 
                if (currGroupLength > 0) computeHintsRec(tail, 0, hints :+ currGroupLength) else computeHintsRec(tail, 0, hints)
            case _ => computeHintsRec(tail, 0, hints)
        }
    }
    computeHintsRec(row, 0, Nil)
}

// Applies hints comparisons on two List of Hints , can stop early if there is a trivial difference between the two lists
def compareHints(l1: List[Hint], l2: List[Hint]): Boolean = (l1, l2) match {
//  Both lists are empty, comparisons successful
    case (Nil, Nil) => true
//  Compares current hints and proceeds to the next ones    
    case (h1 :: t1, h2 :: t2) => (h1 == h2) && (t1 == t2) // if h1 == h2 then t1 == t2 else false
//  One list is longer than the other, return false
    case _ => false
}
 
// Compares a grid and a List of given Hint to determine if the grid is a solution that matches
def isGridValid(g: Grid, rhs: List[Hint], chs: List[Hint]): Boolean = {
    val genHints = hints(g)
    genHints match {
        case None => false
        case Some(r, h) => compareHints(r, rhs) && compareHints(h, chs)
    }
}