import assertk.assertThat
import assertk.assertions.hasMessage
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TokenizerTests {

    private lateinit var tokenizer: Tokenizer

    @BeforeEach
    fun setUp() {
        this.tokenizer = Tokenizer()
    }

    @Test
    internal fun `tokenize throws TokenizationException`() {
        assertThat { this.tokenizer.tokenize("a(b/c)d+abcd[a/e/i]") }
            .isFailure().hasMessage("Couldn't parse a token at +abcd[a/e/i]")
    }

    @Test
    internal fun `tokenize returns a list of tokens`() {
        assertThat(this.tokenizer.tokenize("[a/e/u/i](abc*8/def)xyz"))
            .isEqualTo(
                listOf(
                    Token("[", TokenType.GROUP_START),
                    Token("a", TokenType.STRING),
                    Token("/", TokenType.ALTERNATIVE_DELIMITER),
                    Token("e", TokenType.STRING),
                    Token("/", TokenType.ALTERNATIVE_DELIMITER),
                    Token("u", TokenType.STRING),
                    Token("/", TokenType.ALTERNATIVE_DELIMITER),
                    Token("i", TokenType.STRING),
                    Token("]", TokenType.GROUP_END),
                    Token("(", TokenType.OPTIONAL_GROUP_START),
                    Token("abc", TokenType.STRING),
                    Token("*", TokenType.WEIGHT_DELIMITER),
                    Token("8", TokenType.NUMBER),
                    Token("/", TokenType.ALTERNATIVE_DELIMITER),
                    Token("def", TokenType.STRING),
                    Token(")", TokenType.OPTIONAL_GROUP_END),
                    Token("xyz", TokenType.STRING),
                    Token("", TokenType.END)
                )
            )
    }
}