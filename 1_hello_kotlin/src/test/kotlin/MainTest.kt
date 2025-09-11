import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.example.main
import org.example.palindrome
import org.example.splitIntoCharacters
import org.example.fizzBuzz

class AllTest {
    @Test
    fun palindromeTest() {
        assertEquals(false, palindrome("abcdefghij"))
        assertEquals(true, palindrome("ababa"))
        assertEquals(true, palindrome("a"))
        assertEquals(true, palindrome(""))
        assertEquals( true,palindrome("a man a plan a canal panama"))
        assertEquals(true, palindrome("able was I ere I saw elba"))
    }
    @Test
    fun splitIntoCharactersTest() {
        assertEquals(listOf<Char>('a'),splitIntoCharacters("a"))
        assertEquals(listOf<Char>(),splitIntoCharacters(""))
        assertEquals(listOf<Char>('$', '%', '^'),splitIntoCharacters("$%^"))
        assertEquals(listOf<Char>(' ', 't', 'e', 's', 't', ' '),splitIntoCharacters(" test "))
    }
    @Test
    fun fizzBuzzTest() {
        assertEquals("FizzBuzz", fizzBuzz(15))
        assertEquals("Fizz", fizzBuzz(9))
        assertEquals("Buzz", fizzBuzz(10))
        assertEquals("7", fizzBuzz(7))

    }
}