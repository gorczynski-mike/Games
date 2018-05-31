28.05.2018 08:33
Two different approaches:
- complex sudokuElementsArray (com.gorczynskimike.sudoku.complex) with more complex objects, uses lots of memory when putting game
state on backtrack, doesn't work for now, most probably broken somewhere (saving state to stack?)
- simple sudokuElementsArray (com.gorczynskimike.sudoku.simple), much simpler objects, uses very little memory, works very well and
very fast
For now main class App uses simple sudokuElementsArray and everything seems to be working ok