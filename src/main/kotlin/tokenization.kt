
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
