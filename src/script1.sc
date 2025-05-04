import nonogram.*

/*  Arguments "by default" that corresponds to the hints of the nonogram to solve
 *  Note: to set hints corresponding to none case 'Filled' write Nil or List() , not List(0)
 */
val chosenRowHint = Examples.r33  // Modify to choose row hints
val chosenColHint = Examples.c33  // Same for column hints

/*  In the REPL environment, load the script (after having computed the package) and call run()
 *  To directly visualize : call draw(run())
 */
@main 
def run1(): Solutions = {
    val startTime = System.currentTimeMillis()
    val sol = solveNaive(chosenRowHint, chosenColHint)
    val endTime = System.currentTimeMillis()
    println("Total time for script1: " + (endTime - startTime) + " ms")
    sol
}
