package org.example

// function that checks if a list is a palindrome
fun palindrome(s: String): Boolean {
    val reversedString = s.reversed()
    return s == reversedString
}

// function that converts a string to a list of all its characters
fun splitIntoCharacters(string: String): List<Char> {
    return string.toList()
}

// FizzBuzz function

fun fizzBuzz(x: Int): String {
    if (x % 3 == 0 && x % 5 == 0) {
        return "FizzBuzz"
    } else if (x % 3 == 0) {
        return "Fizz"
    } else if (x % 5 == 0) {
        return "Buzz"
    } else {
        return x.toString()
    }
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    // testing palindrome function
    println(palindrome("abba"))
    // testing string to list converter
    println(splitIntoCharacters("Hello World!"))
    // testing FizzBuzz
    println(fizzBuzz(15))
}