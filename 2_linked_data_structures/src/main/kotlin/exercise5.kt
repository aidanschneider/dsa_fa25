package org.example


fun exercise5() {
    // initialize 2 stacks, an original stack and a copy stack, and an intermediary queue
    val originalStack = LinkedListStack<Int>()
    val intermediateQueue = LinkedListQueue<Int>()
    val copyStack = LinkedListStack<Int>()

    // pop all the values from the original stack to the intermediate queue
    while (!originalStack.isEmpty()) {
        val poppedVal = originalStack.pop()

        if (poppedVal != null) {
            intermediateQueue.enqueue(poppedVal)
        }
    }
    // dequeue all the values from the intermediate queue to the original stack, inverting the original stack
    while (!intermediateQueue.isEmpty()) {
        val dequeuedVal = intermediateQueue.dequeue()

        if (dequeuedVal != null) {
            originalStack.push(dequeuedVal)
        }
    }
    // pop the values from the original stack to both the copy stack and the intermediate queue, generating
    // the correct copy of the original stack
    while (!originalStack.isEmpty()) {
        val poppedVal = originalStack.pop()

        if (poppedVal != null) {
            copyStack.push(poppedVal)
            intermediateQueue.enqueue(poppedVal)
        }
    }

    // dequeue the values from the intermediate queue to the original stack to restore the original stack
    while(!intermediateQueue.isEmpty()) {
        val dequeuedVal = intermediateQueue.dequeue()

        if (dequeuedVal != null) {
            originalStack.push(dequeuedVal)
        }
    }
}