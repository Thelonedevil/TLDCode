import java.math.BigDecimal
import java.math.BigInteger

object functions {
    fun pushInput() {
        if (input == null) {
            "A B C D E F G H I J K L M".split(" ").forEach { stack.push(it) }
            return
        }
        input?.forEach { stack.push(it) }
    }

    fun debug(){
        stack.forEach { print(" " + it) }
    }

    fun parseStack() {
        val temp = Stack()
        stack.forEach {
            if (it.toString().asSequence().filter { it.isDigit() }.count() == it.toString().length) {
                temp.push(BigInteger(it.toString()))
            } else
                if (it.toString().asSequence().filter { it.isDigit() || it == '.' }.count() == it.toString().length) {
                    temp.push(BigDecimal(it.toString()))
                } else {
                    temp.push(it.toString())
                }
        }
        stack = temp
    }

    fun decrementHead() {
        if (stack.size > 0) {
            val head = stack.pop()
            if (head is String) {
                if (head.codePointCount(0, head.length) == 1) {
                    if (head[0].isHighSurrogate()) {
                        stack.push(head[0].toString() + head[1].dec().toString())
                    } else {
                        stack.push(head[0].dec().toString())
                    }
                } else {
                    stack.push(head)
                }
            } else
                if (head is BigInteger) {
                    stack.push(head.minus(BigInteger.ONE))
                } else
                    if (head is BigDecimal) {
                        stack.push(head.minus(BigDecimal.ONE))
                    } else
                        if (head is Any) {
                            parseStack()
                            decrementHead()
                        }
        }
    }

    fun decrementStack() {
        if (stack.size > 0) {
            var j = stack.size.toLong()
            while (j > 0) {
                j--
                rotateRight()
                decrementHead()
            }
        }
    }

    fun incrementHead() {
        if (stack.size > 0) {
            val head = stack.pop()
            if (head is String) {
                if (head.codePointCount(0, head.length) == 1) {
                    if (head[0].isHighSurrogate()) {
                        stack.push(head[0].toString() + head[1].inc().toString())
                    } else {
                        stack.push(head[0].inc().toString())
                    }
                } else {
                    stack.push(head)
                }
            } else
                if (head is BigInteger) {
                    stack.push(head.plus(BigInteger.ONE))
                } else
                    if (head is BigDecimal) {
                        stack.push(head.plus(BigDecimal.ONE))
                    } else
                        if (head is Any) {
                            parseStack()
                            incrementHead()
                        }
        }
    }

    fun incrementStack() {
        if (stack.size > 0) {
            var j = stack.size.toLong()
            while (j > 0) {
                j--
                rotateRight()
                incrementHead()
            }
        }
    }

    fun rotateLeft() {
        stack.push(stack.popLast())
    }

    fun rotateRight() {
        stack.addLast(stack.pop())
    }

    fun reverse() {
        var temp = Stack()
        stack.descendingIterator().forEach { temp.push(it) }
        stack = temp
    }

    fun sum() {
        var intresult = BigInteger.ZERO
        var decimalresult = BigDecimal.ZERO
        var stringresult = ""
        stack.forEach {
            if (it is BigInteger) {
                intresult = intresult.plus(it)
            } else if (it is BigDecimal) {
                decimalresult = decimalresult.plus(it)
            } else if (it is String) {
                stringresult += it
            }
        }
        stack.push(intresult)
        stack.push(decimalresult)
        stack.push(stringresult)
    }

    fun popPrint() {
        print(stack.pop())
    }
}