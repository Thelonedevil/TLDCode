package uk.tldcode.tldcode
import java.math.BigDecimal
import java.math.BigInteger

object functions {
    fun pushInput() {
        if (input == null) {
            "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ").forEach { stack.push(it) }
            return
        }
        input?.forEach { stack.push(it) }
    }

    fun debug() {
        print(stack.joinToString("_"))
        //stack.forEach { print("_" + it) }
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

    fun sumBackup() {
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

    fun popHead() {
        stack.pop()
    }

    fun popTail() {
        stack.popLast()
    }

    fun popPrint() {
        print(stack.pop())
    }

    fun peekPrint() {
        print(stack.peek())
    }

    fun join() {
        var result = ""
        stack.forEach { result += it.toString() }
        stack.push(result)
    }

    fun comma() {
        val temp = Stack()
        stack.forEach { temp.push(it);temp.push(",") }
        temp.pop()
        stack = temp
    }

    fun copy() {
        stack.push(stack.peek())
    }

    fun sum() {
        val temp = stack.pop()
        val a: Number = if (temp.toString().contains(".")) BigDecimal(temp.toString()) else BigInteger(temp.toString())
        val b: Number = if (stack.peek().toString().contains(".")) BigDecimal(stack.peek().toString()) else BigInteger(stack.peek().toString())
        var result: Number = BigInteger.ZERO
        if (a is BigInteger && b is BigInteger) {
            result = a + b
        } else if (a is BigInteger && b is BigDecimal) {
            result = b + BigDecimal(a.toString())
        } else if (a is BigDecimal && b is BigInteger) {
            result = a + BigDecimal(b.toString())
        } else if (a is BigDecimal && b is BigDecimal) {
            result = a + b
        }
        stack.push(temp)
        stack.push(result)
    }

    fun product() {
        val temp = stack.pop()
        val a: Number = if (temp.toString().contains(".")) BigDecimal(temp.toString()) else BigInteger(temp.toString())
        val b: Number = if (stack.peek().toString().contains(".")) BigDecimal(stack.peek().toString()) else BigInteger(stack.peek().toString())
        var result: Number = BigInteger.ZERO
        if (a is BigInteger && b is BigInteger) {
            result = a * b
        } else if (a is BigInteger && b is BigDecimal) {
            result = b * BigDecimal(a.toString())
        } else if (a is BigDecimal && b is BigInteger) {
            result = a * BigDecimal(b.toString())
        } else if (a is BigDecimal && b is BigDecimal) {
            result = a * b
        }
        stack.push(temp)
        stack.push(result)
    }

    fun power() {
        val temp = stack.pop()
        val a: Number = if (temp.toString().contains(".")) BigDecimal(temp.toString()) else BigInteger(temp.toString())
        val b: Number = if (stack.peek().toString().contains(".")) BigDecimal(stack.peek().toString()) else BigInteger(stack.peek().toString())
        var result: Number = BigInteger.ZERO
        if (a is BigInteger && b is BigInteger) {
            result = a.pow(b.intValueExact())
        } else if (a is BigInteger && b is BigDecimal) {
            result = a.pow(b.intValueExact())
        } else if (a is BigDecimal && b is BigInteger) {
            result = a.pow(b.intValueExact())
        } else if (a is BigDecimal && b is BigDecimal) {
            result = a.pow(b.intValueExact())
        }
        stack.push(temp)
        stack.push(result)
    }

    fun difference() {
        val temp = stack.pop()
        val a: Number = if (temp.toString().contains(".")) BigDecimal(temp.toString()) else BigInteger(temp.toString())
        val b: Number = if (stack.peek().toString().contains(".")) BigDecimal(stack.peek().toString()) else BigInteger(stack.peek().toString())
        var result: Number = BigInteger.ZERO
        if (a is BigInteger && b is BigInteger) {
            result = a - b
        } else if (a is BigInteger && b is BigDecimal) {
            result = b - BigDecimal(a.toString())
        } else if (a is BigDecimal && b is BigInteger) {
            result = a - BigDecimal(b.toString())
        } else if (a is BigDecimal && b is BigDecimal) {
            result = a - b
        }
        stack.push(temp)
        stack.push(result)
    }

    fun division() {
        val temp = stack.pop()
        val a: Number = if (temp.toString().contains(".")) BigDecimal(temp.toString()) else BigInteger(temp.toString())
        val b: Number = if (stack.peek().toString().contains(".")) BigDecimal(stack.peek().toString()) else BigInteger(stack.peek().toString())
        var result: Number = BigInteger.ZERO
        if (a is BigInteger && b is BigInteger) {
            result = a / b
        } else if (a is BigInteger && b is BigDecimal) {
            result = BigDecimal(a.toString()) / b
        } else if (a is BigDecimal && b is BigInteger) {
            result = a / BigDecimal(b.toString())
        } else if (a is BigDecimal && b is BigDecimal) {
            result = a / b
        }
        stack.push(temp)
        stack.push(result)
    }

    fun split() {
        stack.pop().toString().toCharArray().forEach { stack.push(it.toString()) }
    }

    fun prime() {
        val temp = stack.peek()
        if (temp is BigInteger) {
            if (!temp.isProbablePrime(5)) {
                stack.push(false)
                return
            }
            val two = BigInteger("2")
            if (two != temp && BigInteger.ZERO == temp.mod(two)) {
                stack.push(false)
                return
            }
            var i = BigInteger("3")
            while (i.multiply(i).compareTo(temp) < 1) {
                if (BigInteger.ZERO == temp.mod(i)) {
                    stack.push(false)
                    return
                }
                i = i.add(two)
            }

            stack.push(true)

        }
    }
    fun greater(){
        val a = stack.pop()
        val b = stack.peek()
        var result= false
        if(a is BigInteger && b is BigInteger){
            result=a>b
        }
        stack.push(a)
        stack.push(result)
    }
    fun less(){
        val a = stack.pop()
        val b = stack.peek()
        var result= false
        if(a is BigInteger && b is BigInteger){
            result=a<b
        }
        stack.push(a)
        stack.push(result)
    }
    fun equals(){
        val a = stack.pop()
        val b = stack.peek()
        var result= false
        if(a is BigInteger && b is BigInteger){
            result=a==b
        }
        stack.push(a)
        stack.push(result)
    }
    fun eval(){
        val code = stack.peek().toString()
        val parser = Parser(null)
        parser.parseCode(code,false)
    }

    fun  mod() {
        val temp = stack.pop()
        val a: Number = if (temp.toString().contains(".")) BigDecimal(temp.toString()) else BigInteger(temp.toString())
        val b: Number = if (stack.peek().toString().contains(".")) BigDecimal(stack.peek().toString()) else BigInteger(stack.peek().toString())
        var result: Number = BigInteger.ZERO
        if (a is BigInteger && b is BigInteger) {
            result = a % b
        } else if (a is BigInteger && b is BigDecimal) {
            result = BigDecimal(a.toString()) % b
        } else if (a is BigDecimal && b is BigInteger) {
            result = a % BigDecimal(b.toString())
        } else if (a is BigDecimal && b is BigDecimal) {
            result = a % b
        }
        stack.push(temp)
        stack.push(result)
    }

    fun root(){
        val temp = stack.pop()
        val a: Number = if (temp.toString().contains(".")) BigDecimal(temp.toString()) else BigInteger(temp.toString())
        val b: Number = if (stack.peek().toString().contains(".")) BigDecimal(stack.peek().toString()) else BigInteger(stack.peek().toString())
        var result: Number = BigDecimal.ZERO
        if (a is BigInteger && b is BigInteger) {
            result = BigDecimal(a.toString()).root(b.intValueExact())
        } else if (a is BigInteger && b is BigDecimal) {
            result = BigDecimal(a.toString()).root(b.intValueExact())
        } else if (a is BigDecimal && b is BigInteger) {
            result = BigDecimal(a.toString()).root(b.intValueExact())
        } else if (a is BigDecimal && b is BigDecimal) {
            result = a.root(b.intValueExact())
        }
        stack.push(temp)
        stack.push(result)
    }
    fun input(){
        var line:String=""
        while(line.isEmpty()){
                line= readLine()!!
        }
        stack.push(line)
    }
    fun swap(){
        val a = stack.pop()
        val b = stack.pop()
        stack.push(a)
        stack.push(b)
    }

    private val SCALE = 10
    private val ROUNDING_MODE = BigDecimal.ROUND_HALF_DOWN

    fun BigDecimal.root(n:Int):BigDecimal {
        return nthRoot(n, this, BigDecimal.valueOf(.1).movePointLeft(SCALE))
    }
    private fun nthRoot(n: Int, a: BigDecimal, p: BigDecimal): BigDecimal {
        if (a < BigDecimal.ZERO) {
            throw IllegalArgumentException("nth root can only be calculated for positive numbers")
        }
        if (a == BigDecimal.ZERO) {
            return BigDecimal.ZERO
        }
        var xPrev = a
        var x = a.divide(BigDecimal(n), SCALE, ROUNDING_MODE)  // starting "guessed" value...
        while (x.subtract(xPrev).abs() > p) {
            xPrev = x
            x = BigDecimal.valueOf(n - 1.0)
                    .multiply(x)
                    .add(a.divide(x.pow(n - 1), SCALE, ROUNDING_MODE))
                    .divide(BigDecimal(n), SCALE, ROUNDING_MODE)
        }
        return x
    }
}