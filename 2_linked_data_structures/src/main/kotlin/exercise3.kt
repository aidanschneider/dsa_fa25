package org.example

fun exercise3() {
    /**
     * Reverses elements in a stack.
     *
     */
    // creating an initial stack and pushing values to it
    val x = LinkedListStack<Int>()
    x.push(3)
    x.push(5)

    // initialize an empty stack that will hold the reversed values
    val reversedStack = LinkedListStack<Int>()

    // runs till x is empty
    while (!x.isEmpty()) {
        // pop the value and push it to the reversed stack
        val poppedVal = x.pop()
        if (poppedVal != null) {
            reversedStack.push(poppedVal)
        }
    }
}