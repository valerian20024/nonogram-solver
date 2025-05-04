import nonogram.*

/*  Arguments "by default" to run with solver2
 *  Note: to set hints corresponding to none case 'Filled' write Nil or List() , not List(0)
 */
val chosen2RowHint = Examples.r33  // Modify to choose row hints
val chosen2ColHint = Examples.c33  // Same for column hints


//  Computes package and then :load the script to call run2() ; you can use draw(run2()) too
@main def run2(): Solutions = {
    val startTime = System.currentTimeMillis()
    val sol = solve(chosen2RowHint, chosen2ColHint)
    val endTime = System.currentTimeMillis()
    println("Total time for script2: " + (endTime - startTime) + " ms")
    sol
}