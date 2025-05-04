package nonogram

// type aliases
type Hint = List[Int]

type Row = List[Square]

// ADT definitions
enum Square:
    case Blank
    case Filled
    case Indeterminate

object Square:
    def squareToString(square: Square): String = square match
        case Blank => "."
        case Filled => "#"
        case Indeterminate => "?"

// 'FilledGrid' contains a list of vertical rows and a horizontal one
enum Grid:
    case EmptyGrid
    case FilledGrid(rs: List[Row], cs: List[Row])

    
// 'Solved' is a list of 'FilledGrid' that correspond to solution(s)
enum Solutions:
    case NoSolution
    case Solved(solutions: List[Grid])