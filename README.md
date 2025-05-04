# Nonogram solver

## How to use

Install Scala using the [tutorial](https://scala-lang.org/download/). 
In the root directory :

```
mkdir src/classes                           # creating a folder for build
scalac src/nonogram/*.scala -d src/classes  # compiling classes
scala -cp src/classes                           # launch the REPL indicating where the classes are
```
Inside the REPL :

```
scala> :load src/script1.sc
scala> draw(run1())
```
