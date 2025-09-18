package org.example
import org.example.Stack

class myStack<T>: Stack<T> {

    class StackNode<T>(val data: T, var next: StackNode<T>?)

    var top: StackNode<T>? = null

    override fun push(data: T) {
        val newNode= StackNode<T>(data, next = top)
        top = newNode
        TODO("Not yet implemented")
    }
    override fun pop(): T? {
        val currentTop = top
        if (currentTop == null) {
            return null
        }
        top = currentTop.next
        return currentTop.data
        TODO("Not yet implemented")
    }
    override fun peek(): T? {
        TODO("Not yet implemented")
        return top?.data
    }
    override fun isEmpty(): Boolean {
        return top == null
        TODO("Not yet implemented")
    }
}


fun main() {

}