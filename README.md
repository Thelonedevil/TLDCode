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
| »      | incrementStack   | Increments the full stack by 1 for numbers, and increments the code points by 1 for characters                             |
| =      | clearStack()     | Clears the stack of all values                                                                                             |
| >      | rotateRight()    | Rotates the stack to the right                                                                                             |
| <      | rotateLeft()     | Rotates the stack to the left                                                                                              |
| -      | decrementHead()  | Decrements the head of the stack by 1 for numbers, and increments the code point by 1 for characters                       |
| «      | decrementStack() | Decrements the full stack by 1 for numbers, and increments the code points by 1 for characters                             |
| r      | reverse()        | Reverse the stacks order                                                                                                   |
| s      | sum()            | Sums the stack together, then pushes the results to the stack, integers, floating points,strings                           |
| P      | popPrint()       | Pops the top off the stack, and prints it                                                                                  |
| '      |                  | Pushes all characters following onto the stack until next '                                                                |
| [\d]    |                   | a Number, repeats the following symbol that many times                                                                   |

An example Hello, World! program looks like this:

```
44->24+>33+>40+>38+>15+>39->26->42+>40+>41+>35+>7+>sP
7»51->17+>26+>33+>31+>8+>46->33->35+>33+>34+>28+2>sP
```
How it works
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
s
```
this joins the string together

```
P
```
prints the head of the stack

A simpler Hello, World! would be
```
=\"Hello, World!\"sP
```

How it works

```
=
```
clears the stack of the default input

```
'
```
escapes the " character so the command line parses it correctly, then signals that what follows is a string

```
'
```
signals the end of the string

```
s
```
this joins the string together

```
P
```
prints the head of the stack