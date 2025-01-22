# Complex Calculator
This document describes a complex number graphing calculator written in Java. The calculator utilizes the Swing and AWT libraries to render graphical output and handle some user input. Additionally, it leverages ArrayLists and Lists for various data structures. The primary function, `Main.java`, employs a scanner to accept user input for the equation to be visualized. **Equation Input** Equations for graphing must adhere to the common LaTeX format. It is important to note that the tilde (`~`) character is reserved for internal purposes within the code and cannot be included within the input equation. The calculator supports a comprehensive set of mathematical functions, including logarithmic (base specified and natural), trigonometric (sine, cosine, tangent, secant, cosecant, cotangent), hyperbolic (sinh, cosh, tanh, sech, csch, coth), inverse trigonometric (asin, acos, atan, asec, acsc, acot), inverse hyperbolic (asinh, acosh, atanh, asech, acsch, acoth), gamma function, absolute value, exponentiation (base-e and arbitrary power), multiplication, division, addition, and subtraction.
## Understanding the Code
A fundamental understanding of complex numbers (numbers encompassing both real and imaginary components) is essential for comprehending the code. Intermediate mastery of how functions operate on complex numbers is also beneficial. While a working knowledge of LaTeX is helpful, it may not be necessary with enough exploration though comments. Furthermore, a basic understanding of trees as data structures and recursion is likely necessary to realize the code's functionality.
## How does the code function
The code is run from the `Main.java` class.
User input is taken in 
The equation class will take this string input and create a binary tree of the entire equation.
The tree is made up of the Nodes via the Node class. 
These Nodes have either data or and operator and either two or zero sub-nodes (left and right).
The equation class creates this tree by adheres PEMDAS (Parenthesis, Exponentiation, Multiplication/Division, Addition/Subtraction) and adding in a node for each operator and number.
Each number is stored as a complex number from the Complex Number class.
This includes `i` which would be stored as `0 + 1i`. 
The equation parser goes very far in splitting number as to turn `2 + 4i` into three separate nodes (plus two operator nodes `+` `*` ): `2 + 0i` `1 + 0i` and `0 + 1i`.
After the equation creates the tree, the Main script evaluates the equation at 0, 1, and i as to confirm with the user what the function should be doing.
The Main script then initializes the frame that will be used by Swing and AWT.
The next step is the other major part of the program: the scene.
Main creates a scene by passing in values of the camera, points and desired view of the scene.
Scene is able to interprete this and by using the Vector class and Matrix class will create a 2D projection of the 4D graph.
From there SweepXValues is used to evaluate the equation at many x-values and will return the values at a given imaginary value of x to the main script.
(through testing I have discovered the optimal range to be from 100-1000, but this will clearly depend on computer processing power)
The main script then uses DrawPoint to add all the sweep values and draw them.
This is called on every key press. (It would be unnecessary to call this more often)
By using the scroll wheel of the mouse the user is able to move through any of the calculated imaginary x-values.
The second mode used allows for the visualisation of multiple lines at the same time. 
For example if your current slice is set to `Im[x] = 0` the next lines will be of the same equation but will have different Im[x] values.
## How to use the code
The equation parser within the code is designed to handle the output from the scanner directly. When entering the equation, adhere to the LaTeX format. It is important to note that trigonometric functions must use curly braces `{}` to enclose the argument instead of regular parentheses `()`. For instance, use `\sin{2}` instead of `\sin(2)`.
Use `w` `a` `s` `d` to move and `z` and `x` to zoom in and out.
Use the scroll wheel or scroll on the trackpad to move through different imaginary inputs for x.
Use `m` to toggle between modes 1 and 2.
Use `n` to change the number of additional lines in mode 2.
Use `c` to change color pallete for mode 2.
