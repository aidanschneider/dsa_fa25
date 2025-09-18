package org.example

/**
 * A doubly-linked list that store elements of generic type T. This class provides methods for the user
 * to push, pop, and peek elements from the front and back of the list.
 *
 * @param T the type of elements that will be held in the collection.
 */
class DoublyLinkedList<T>: LinkedList<T> {
    class ListNode<T>(val data: T, var next: ListNode<T>? = null, var prev: ListNode<T>? = null)
    private var front: ListNode<T>? = null
    private var back: ListNode<T>? = null

    override fun pushFront(data: T) {
        /**
         * This function pushes the value to the front of the doubly-linked list, reorienting the pointers
         * to account for the addition.
         * @param data the data to be added to the list.
         */
        if (isEmpty()) {
            front = ListNode(data, null, null)
            back = front
        } else {
            val newNode = ListNode(data, next = front)
            front?.prev = newNode
            front = newNode
        }
    }
    override fun pushBack(data: T) {
        /**
         * This function pushes the value to the back of the doubly-linked list, reorienting the pointers
         * to account for the addition.
         * @param data the data to be added to the list.
         */
        if (isEmpty()) {
            back = ListNode(data, null, null)
            front = back
        } else {
            val newNode = ListNode(data, prev = back)
            back?.next = newNode
            back = newNode
        }
    }

    override fun popFront(): T? {
        /**
         * Returns the element at the front of the list and removes it.
         *
         * @return frontDataToReturn the data of the removed element, or null if the list is empty.
         */
        if (isEmpty()) {
            return null
        }
        val frontDataToReturn = front?.data

        if (front == back) {
            front = null
            back = null
        } else {
            front = front?.next
            front?.prev = null
        }
        return frontDataToReturn
    }

    override fun popBack(): T? {
        /**
         * Returns the element at the back of the list and removes it.
         *
         * @return backDataToReturn the data of the removed element, or null if the list is empty.
         */
        if (isEmpty()) {
            return null
        }

        val backDataToReturn = back?.data

        if (front == back) {
            front = null
            back = null
        } else {
            back = back?.prev
            back?.next = null
        }
        return backDataToReturn
    }

    override fun peekFront(): T? {
        /**
         * Returns the element at the front of the list.
         *
         * @return front?.data the data of the front-most element.
         */
        return front?.data
    }

    override fun peekBack(): T? {
        /**
         * Returns the element at the back of the list.
         *
         * @return back?.data the data of the front-most element.
         */
        return back?.data
    }

    override fun isEmpty(): Boolean {
        /**
         * Checks whether the list is empty.
         *
         * @return true if the list is empty, and false otherwise.
         */
        return front == null
    }
}

// EXERCISE 1: Implementation of Stack using Linked List class

/**
 * An implementation of a stack utilizing the methods of the DoublyLinkedList class.
 *
 * @param T the type of elements that will be held in the collection.
 */
class LinkedListStack<T> : Stack<T> {
    private val storage = DoublyLinkedList<T>()
    override fun push(data: T) {
        /**
         * Adds an element to the top of the stack.
         *
         * @param data the data that is being added to the top of the stack.
         */
        storage.pushBack(data)
    }

    override fun pop(): T? {
        /**
         * Returns the element at the top of the stack and removes it.
         *
         * @return the data of the removed element, or null if the stack is empty.
         */
        return storage.popBack()
    }

    override fun peek(): T? {
        /**
         * Returns the element at the top of the stack without removing it from the stack.
         *
         * @return the element at the top of the stack, or null if the stack is empty.
         */
        return storage.peekBack()
    }

    override fun isEmpty(): Boolean {
        /**
         * Returns the element at the top of the stack.
         *
         * @return true if the stack is empty, false otherwise.
         */
        return storage.isEmpty()
    }
}

// EXERCISE 2: Implementation of Queue using Linked List class
/**
 * An implementation of a queue utilizing the methods of the DoublyLinkedList class.
 *
 * @param T the type of elements that will be held in the collection.
 */
class LinkedListQueue<T>: Queue<T> {
    private val storage = DoublyLinkedList<T>()

    override fun enqueue(data: T) {
        /**
         * Adds an element to the queue.
         *
         * @return the data added to the queue, or null if queue empty.
         */
        return storage.pushBack(data)
    }

    override fun dequeue(): T? {
        /**
         * Removes the element at the front of the queue and returns it.
         *
         * @return the data removed from the queue, or null if queue empty.
         */
        return storage.popFront()
    }

    override fun peek(): T? {
        /**
         * Returns the element at the front of the queue without removing it.
         *
         * @return the data at the front of the queue, or null if queue empty.
         */
        return storage.peekFront()
    }

    override fun isEmpty(): Boolean {
        /**
         * Checks if the queue is empty.
         *
         * @return true if the queue is empty, and false otherwise.
         */
        return storage.isEmpty()
    }

}

fun main() {

}