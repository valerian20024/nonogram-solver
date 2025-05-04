package nonogram
import Character.isDigit

// type aliases for parsers
type Str = List[Char]  // lists of chars are easier to read

type Parser[A] = Str => List[(A, Str)]

// parsing function
def parse[A](parser: Parser[A], input: Str): List[(A, Str)] =
    parser(input)

// parses an item (char)
def item: Parser[Char] =
    (x: Str) => x match {
        case Nil => Nil
        case x :: xs => List((x, xs))
    }

// returns an empty parser
def emptyParser[A]: Parser[A] =
    (x: Str) => Nil

// returns a parser of char if the predicate is satified
def satisfy(predicate: Char => Boolean): Parser[Char] =
    x => {val c = item(x); if predicate(c.head(0)) then c else Nil}

// parser for digit
def digit: Parser[Char] =
    satisfy(isDigit)

// parser for natural numbers
def natural: Parser[Int] = ???

// parser for specific caracters
def char(x: Char): Parser[Char] = 
    satisfy(y => y == x)

// main function
def stringToLists(s: String) = //: List[List[List[Int]]] =
    val allow = List('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '[', ']', ',')
    val chars = s.filter(allow.contains).toList
    // here should be the code for parsing the string chars
