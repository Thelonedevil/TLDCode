# TLDCode
TLDCode a stack based language

TLDCode is a stack based language that is still very much a WIP
ATM there are 13,symbols recognised:

| Symbol | Function         | Description                                                                                                                |
|--------|------------------|----------------------------------------------------------------------------------------------------------------------------|
| +      | incrementHead()  | Increments the head of the stack by 1 for numbers, and increments the code point by 1 for characters                       |
| _      | pushInput()      | Pushes the input onto the stack, the input being the 2nd and onward arguments, or the letters A-M if no input is specified |
| p      | parseStack()     | Parses the stack into the correct types                                                                                    |
| D      | debug()          | Prints the full stack to the console                                                                                       |
| ➕      | incrementStack   | Increments the full stack by 1 for numbers, and increments the code points by 1 for characters                             |
| =      | clearStack()     | Clears the stack of all values                                                                                             |
| >      | rotateRight()    | Rotates the stack to the right                                                                                             |
| <      | rotateLeft()     | Rotates the stack to the left                                                                                              |
| -      | decrementHead()  | Decrements the head of the stack by 1 for numbers, and increments the code point by 1 for characters                       |
| ➖      | decrementStack() | Decrements the full stack by 1 for numbers, and increments the code points by 1 for characters                             |
| r      | reverse()        | Reverse the stacks order                                                                                                   |
| s      | sum()            | Sums the stack together, then pushes the results to the stack, integers, floating points,strings                           |
| P      | popPrint()       | Pops the top off the stack, and prints it                                                                                  |
| [\d]    |                   | a Number, repeats the following symbol that many times

An exmaple Hello, World! program looks like this:

```
44->24+>33+>40+>38+>15+>39->26->42+>40+>41+>35+>7+>sP
```
