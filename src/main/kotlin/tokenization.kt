
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

class Token(val value: String, val tokenType: TokenType)
