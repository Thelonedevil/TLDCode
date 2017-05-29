import java.math.BigDecimal
import java.math.BigInteger

class Stack() : java.util.ArrayDeque<Any>() {

    override fun push(it: Any) {
        if (it is BigInteger || it is String || it is BigDecimal || it is Char|| it is Boolean) {
            super.addFirst(it)
        }
    }

    fun popLast(): Any {
        return super.removeLast()
    }

    override fun iterator(): MutableIterator<Any> {
        return super.descendingIterator()
    }

    override fun descendingIterator(): MutableIterator<Any> {
        return super.iterator()
    }
}