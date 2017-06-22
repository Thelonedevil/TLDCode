# TLDCode

TLDCode is a stack based language that is still very much a WIP

ATM there are 31,symbols recognised:

| Symbol | Function         | Description                                                                                                                   |
|--------|------------------|-------------------------------------------------------------------------------------------------------------------------------|
| +      | incrementHead()  | Increments the head of the stack by 1 for numbers, and increments the code point by 1 for characters                          |
| _      | pushInput()      | Pushes the input onto the stack, the input being the 2nd and onward arguments, or the letters A-M if no input is specified    |
| p      | parseStack()     | Parses the stack into the correct types                                                                                       |
| D      | debug()          | Prints the full stack to the console                                                                                          |
| »      | incrementStack   | Increments the full stack by 1 for numbers, and increments the code points by 1 for characters                                |
| =      | clearStack()     | Clears the stack of all values                                                                                                |
| >      | rotateRight()    | Rotates the stack to the right                                                                                                |
| <      | rotateLeft()     | Rotates the stack to the left                                                                                                 |
| -      | decrementHead()  | Decrements the head of the stack by 1 for numbers, and decrements the code point by 1 for characters                          |
| «      | decrementStack() | Decrements the full stack by 1 for numbers, and decrements the code points by 1 for characters                                |
| r      | reverse()        | Reverse the stacks order                                                                                                      |
| s      | sum()            | Sums the top two items in the stack                              |
| P      | popPrint()       | Pops the top off the stack, and prints it                                                                                     |
| '      |                  | Pushes the following characters (character by character) onto the stack until next '                                          |
| #      |                  | Pushes the following characters onto the stack as a single sequence until next #                                             |
| [\d]   |                  | a Number, repeats the following symbol, or code block, that many times                                                                       |
| %      |                  | Starts reading a variable name until it finds a closing %, if it doesn't exist, it copies the top of the stack into that variable, otherwise it pushes it onto the top of the stack|
| j      | join()           | Joins all elements in the stack as a string |
| ,      | comma()          | Interleaves a comma between each element in the stack |
| n      | println()        | Prints a new line |
| {      |                  | Starts a repeatable code block |
| }      |                  | Ends a repeatable code block |
| S      |                  | Repeats the following symbol, or repeatable code block, the amount of times of the size of the stack |
| $      |                  | Repeats the following symbol, or repeatable code block, the amount of times of the value the top of the stack |
| e      | peekPrint()      | Peeks at the top of the stack, and prints it |
| c      | copy()           | Copies the top of the stack onto the top of the stack |
| *      | product()        | Multiply the top two elements on the stack together   |
| ^      | power()          | Returns the top of the stack to the power of the second from the top   |
| d      | difference()     | Get the difference between the top two elements on the stack |
| v      | popHead()        | Pops the top of the stack off |
| V      | popTail()        | Pops the bottom of the stack off  |
| &#124; | split()          | Splits the top of the stack |
| /      | division()       | Divides the top of the stack by the second from the top |
| \      | prime()          | Tests whether or not the top of the stack is prime, returns result to top of stack |
| G      | greater()        | Tests where or not the top of the stack is greater than the second from the top, returns result to top of stack |
| L      | less()           | Tests where or not the top of the stack is less than the second from the top, returns result to top of stack |
| E      | equals()         | Tests where or not the top of the stack is equal to the second from the top, returns result to top of stack |
| &      | eval()           | Executes the top of the stack as code |
| (      |                  | Starts a true code block, |
| )      |                  | Ends a true code block |
| [      |                  | Starts a false code block |
| ]      |                  | Ends a false code block |
| R      | root()           | Gets the nth root |
| i      | input()          | Reads input from STDIN |

| Code Block |Syntax | Description |
|------------|-------|-------------|
| Repeatable | {}    | A code block that can be repeated |
| True       | ()    | A code block that is only executed if the top of the stack is true |
| False      | []    | A code block that is only executed if the top of the stack is false |


There are also a few flags that can be used

| Flag  | Long Form | ShortForm | Description   |
|-------|-----------|-----------|---------------|
| Code  | --code    | -c        | Specifies the code for the program    |
| File  | --file    | -f        | Specifies a file that contains the code for the program   |
| REPL  |           | -r        | Launches a REPL environment   |
| Input | --input   | -i        | specifies that all following arguments are input    |

## Examples
1. An example Hello, World! program looks like this:
  
  ```
  -c 44->24+>33+>40+>38+>15+>39->26->42+>40+>41+>35+>7+>jP
  ```
  How it works
  ```
  -c
  ```
   specifies that the next set of characters are the code
  ```
  44-
  ```
  will decrement the head of the stack 44 times, because no input was specified the stack contains 
  ```
  A B C D E F G H I J K L M
  ```
  since M is at the head, it become !. This is because M is 77 and ! is 33
  
  this leaves the stack as
  ```
  A B C D E F G H I J K L !
  ```
  ```
  >
  ```
  will rotate the stack to the right once, leaving the stack as
  ```
  ! A B C D E F G H I J K L
  ```
  and so on till the end
  ```
  j
  ```
  this joins the string together
  
  ```
  P
  ```
  prints the head of the stack
  

2. A simpler Hello, World! would be

  ```
  -c ='Hello, World!'jP
  ```
  
  How it works
  
  ```
  \=
  ```
  
  clears the stack of the default input
  
  ```
  '
  ```
  
  signals that what follows is a string
  
  ```
  '
  ```
  signals the end of the string
  
  ```
  j
  ```
  this joins the string together
  
  ```
  P
  ```
  prints the head of the stack
  
3. An example of repeatable code blocks

  ```
  -c ='Hello, World!'10{jPn}=
  ```
  this is almost the same as example 2, but it repeats the joining and printing, as well as now printing a new line at the end, 10 times.
  
  Note the
  ```
  \=
  ```
  at the end, this is to clear the stack, this is done because, if the last function isnt printing or debug, it runs the debug function, which prints the whole stack. The printing in the code block doesnt count as printing at the end, so we clear the stack, since an empty stack has nothing to print.
4. An example fibonacci sequence

  ```
  -c '011'<2-$s,jP -i 20
  ```
  the -i specifies a comma sepreated list of inputs
  it then pushes 0,1,1 to the stack, and rotates left, bringing the 20 from the input to the to, it then decrementing it twice because we already have f1 and f2 on the stack
  ```
  $
  ```
  pops the top of the stack and uses its value to repeat the following code block
  ```
  s
  ```
  sums the top 2 elements in the stack and pushes the result
  ```
  ,jP
  ```
  interleaves a comma between each elements in the stack, joins them together as a string and prints
