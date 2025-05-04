package nonogram
    
object Examples:
//  nonogram Filled 1 x 1 : works well
    val r11f = List(List(1))
    val c11f = List(List(1))

//  nonogram Blank 1 x 1 : works well
    val r11b = List(List())
    val c11b = List(List())

//  nonogram "in cross form" : works well
    val r33 = List(List(1), List(3), List(1))
    val c33 = r33

//  nonogram 4x4 "en damier" : works well
    val r44 = List(List(1, 1), List(1, 1), List(1, 1), List(1, 1))
    val c44 = r44

//  test with void : works well
    val r33empty = List(List(), List(3), List(3))  
    val c33empty = List(List(2), List(2), List(2))

//  rectangular nonogram 3 x 5 : works well
    val r35 = List(List(3), List(1), List(1), List(1), List(3))
    val c35 = List(List(1, 1), List(5), List(1, 1))

//  nonogram 5x5 : out of memory from Java (script) ; works for script2
    val r55 = List(List(1, 1, 1), List(1, 1), List(1, 1, 1), List(1, 1), List(1, 1, 1)) 
    val c55 = r55

//  test 7x5 for module 2 : works
    val r75 = List(List(4), List(1), List(5), List(1, 1), List(7))
    val c75 = List(List(1), List(2), List(1, 1), List(1, 1, 1), List(1, 1, 1), List(1, 1, 1), List(5))

//  test 8x7 #42314 "baby crawling" : works welll with script2
    val r87 = List(List(3), List(1, 1, 1), List(3), List(5), List(6), List(2, 4), List(2, 3, 1))
    val c87 = List(List(2, 1), List(1, 1, 3), List(6), List(1, 2, 1), List(1, 4), List(4), List(3), List(2))

//  test 8x9 for module2 : java out of memory
    val r89 = List(List(1), List(2), List(2), List(6), List(5), List(6), List(1, 1, 1, 1), List(1, 1, 1, 1), List(1, 1, 1))
    val c89 = List(List(1, 3), List(3), List(4), List(3, 2), List(3), List(8), List(2, 1), List(1, 2))

//  test with 10 x 10 nonogram : too many time (script1) and blocked (script2)
    val rh1010 = List(List(1), List(2), List(3, 1), List(5, 2), List(2, 3), List(2, 1, 1), List(3, 1), List(1, 1, 1), List(1, 2), List(1, 2))
    val ch1010 = List(List(3), List(3), List(2, 1), List(1, 1, 3), List(1), List(2), List(7), List(2, 1, 2), List(2, 2, 1), List(2))

//  test 10x10 almost empty : works
    val rh1010empty = List(List(1), List(), List(), List(), List(), List(), List(), List(), List(), List())
    val ch1010empty = List(List(1), List(), List(), List(), List(), List(), List(), List(), List(), List())