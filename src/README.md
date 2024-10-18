# Complex Calculator
## This is a calculator that attempts to graph equations with complex inputs and outputs.
## How is it written
This is written in Java and uses the Swing and AWT libraries for graphics and some user input. 
In addition, many functions make use of ArrayLists and Lists. 
The main function also uses scanner to take input from the user of the equation that they wish to graph.
When inputting and equation to graph you should use the LATEX common format for equations.
You may not include a `~` in your equation because it is a reserved character that I use to link nodes on the binary tree.
The currently supported functions are as follows: `log_{b}{a}` `log{a}` `\frac{a}{b}` `^` `^{a}` `*` `/` `+` `-` `sin{a}` `cos{a}` `tan{a}` `sec{a}` `csc{a}` `cot{a}` `sinh{a}` `cosh{a}` `tanh{a}` `sech{a}` `csch{a}` `coth{a}` `gam{a}`.
## What you should know before reading the code
You should be familiar with the concept of complex numbers (a number that includes both a real and imaginary part).
You should also have some familiarity with the idea of how functions may act on numbers like these.
A basic LATEX knowledge could be useful, but I find those sections of the code to be more intuitive.
In addition, a basic knowledge of binary trees and recursion is likely necessary to fully understand.
## How does the code function
The code is run from the `Main.java` class.
You can choose to add in any equation into the instantiated equation class, or you can configure it to allow for user input.
The equation class will take this string input and create a binary tree of the entire equation.
The tree is made up of the Nodes via the Node class. 
These Nodes have either data or and operator and either two or zero sub-nodes (left and right).
The equation class creates this tree by following PEMDAS (Parenthesis, Exponentiation, Multiplication/Division, Addition/Subtraction) and adding in a node for each operator and number.
Each number is stored as a complex number from the Complex Number class.
This includes `i` which would be stored as `0 + 1i`. 
The equation parser goes very far in splitting number as to turn `2 + 4i` into three separate nodes (plus two operator nodes `+` `*` ): `2 + 0i` `1 + 0i` and `0 + 1i`.
After the equation creates the tree, the Main script evaluates the equation at 0, 1, and i as to confirm with the user what the function should be doing.
The Main script then initializes the frame that will be used by Swing and AWT.
The next step is the other major part of the program: the scene.
Main creates a scene by passing in values of the camera, points and desired view of the scene.
Scene is able to interprete this and by using the Vector class and Matrix class will create a 2D projection of the 4D graph.
(This is not descriptive, but it would be redundant to fully explain how the code works here)
From there SweepXValues is used to evaluate the equation at many x-values and will return the values at a given imaginary value of x to the main script.
(I find detail values of 100-1000 work best)
The main script then uses DrawPoint to add all the sweep values and draw them.
This is called on every key press. (It would be unnecessary to call this more often)
By using the scroll wheel of the mouse the user is able to move through any of the calculated imaginary x-values.
Hopefully this makes sense and if anything does not make sense I recommend reading the code.
## How to use the code
I recommend using this code by inputting an equation in a string in the code, but if you prefer to take user input from you should import java scanner and scan for nextLine. (The parser in equation should be able to handle the direct output from that)
When typing in the equation you should keep in mind Latex format.
In addition, for trig functions you must use curly braces instead of regular parenthesis for the argument. (Ex. Sin{2} NOT Sin(2))
