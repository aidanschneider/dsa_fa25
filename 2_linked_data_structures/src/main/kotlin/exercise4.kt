package org.example

fun exercise4(s: String): Boolean {
    /**
     * Validates a string containing just parentheses to ensure that the open brackets are closed in the correct
     * order by a matching closing bracket.
     *
     * @param s the string of parentheses to validate.
     * @return Returns true if the parentheses string is valid, and false otherwise.
     */
    // create a dictionary of parentheses pairs
    val matches = mapOf(')' to '(', ']' to '[', '}' to '{')

    // create a stack to hold opening parentheses
    val parenStack = LinkedListStack<Char>()

    // iterates through the parentheses string
    for (letter in s) {
        // if the current letter is a value in the dictionary (open parentheses), push it to the stack
       if (letter in matches.values) {
           parenStack.push(letter)
           // if the letter is a closed parentheses, but the stack is empty, then the string is automatically
           // invalid, since a closing parentheses cannot precede an opening parentheses
       } else if (parenStack.isEmpty()) {
               return false
           // pop the parentheses from the stack, if it doesn't pair with the current parenthesis, then the string
           // is invalid
           } else {
               val poppedVal = parenStack.pop()
               if (poppedVal != matches[letter]) {
                   return false
           }
       }
    }
    // if the parentheses stack is still populated at the end of this process, then the string is invalid
    if (!parenStack.isEmpty()) {
        return false
    }
    // return true if all conditions pass
    return true
}