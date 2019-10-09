
enum class TokenType {
    NUMBER,
    STRING,
    WEIGHT_DELIMITER,
    OPTIONAL_GROUP_START,
    OPTIONAL_GROUP_END,
    GROUP_START,
    GROUP_END,
    ALTERNATIVE_DELIMITER,
    END
}

class TokenizationException(message: String?) : Exception(message)

class Token(val value: String, val tokenType: TokenType) {
    override fun toString(): String {
        return "Token(value='$value', tokenType=$tokenType)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Token

        if (value != other.value) return false
        if (tokenType != other.tokenType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + tokenType.hashCode()
        return result
    }
}

class Tokenizer(
    alternativeDelimiter: String = "/",
    groupStart: String = "[",
    groupEnd: String = "]",
    optionalGroupStart: String = "(",
    optionalGroupEnd: String = ")"
) {
    private val tokenToPattern = mapOf(
        TokenType.NUMBER to "(\\d)+(.(\\d)+)?",
        TokenType.STRING to "\\w+",
        TokenType.WEIGHT_DELIMITER to "\\*",
        TokenType.OPTIONAL_GROUP_START to Regex.escape(optionalGroupStart),
        TokenType.OPTIONAL_GROUP_END to Regex.escape(optionalGroupEnd),
        TokenType.GROUP_START to Regex.escape(groupStart),
        TokenType.GROUP_END to Regex.escape(groupEnd),
        TokenType.ALTERNATIVE_DELIMITER to Regex.escape(alternativeDelimiter)
    )

    @Throws(TokenizationException::class)
    fun tokenize(program: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var searchFromIndex = 0
        while (searchFromIndex < program.length) {
            val nextToken: Token = getNextToken(program, searchFromIndex)
                ?: throw TokenizationException("Couldn't parse a token at ${program.substring(searchFromIndex)}")
            searchFromIndex += nextToken.value.length

            tokens.add(nextToken)
        }

        tokens.add(Token("", TokenType.END))
        return tokens
    }

    private fun getNextToken(program: String, initialIndex: Int): Token? {
        for ((type, pattern) in tokenToPattern) {
            val match = Regex("^$pattern").find(program.substring(initialIndex))
            if (match != null) {
                return Token(match.value, type)
            }
        }
        return null
    }
}