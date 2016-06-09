var stack = Stack()
var input: List<String>? = null
val mappings = hashMapOf(
        "+" to { functions.incrementHead() },
        "_" to { functions.pushInput() },
        "p" to { functions.parseStack() },
        "D" to { functions.debug() },
        "?" to { functions.incrementStack() },
        "=" to { stack.clear() },
        ">" to { functions.rotateRight() },
        "<" to { functions.rotateLeft() },
        "-" to { functions.decrementHead() },
        "?" to { functions.decrementStack() },
        "r" to { functions.reverse() },
        "s" to { functions.sum() },
        "P" to { functions.popPrint() }
)

fun main(args: Array<String>) {
    val code = args[0]
    if (args.size > 1) {
        input = args.slice(1..args.size).toList()
    }
    var loop = 0
    var string = false
    functions.pushInput()
    functions.parseStack()
    code.split("").forEach {
        val key = it
        if (key == "'") {
            string = !string
        } else
            if (string) {
                stack.push(it)
            } else {
                if (key.length > 0 && key[0].isDigit()) {
                    if (loop > 0) {
                        loop = (loop.toString() + key).toInt()
                    } else {
                        loop = key.toInt()
                    }
                } else {
                    if (loop == 0) {
                        loop = 1
                    }
                    while (loop > 0) {
                        loop--
                        mappings[key.toString()]?.invoke()
                    }
                }
            }
    }
    if (!(code.endsWith("P") || code.endsWith("D"))) {
        functions.debug()
    }
}

fun testFunctions() {
    setup()
    functions.debug()
    setup()
    functions.incrementHead()
    functions.debug()
    setup()
    functions.incrementStack()
    functions.debug()
    setup()
    functions.rotateRight()
    functions.debug()
    setup()
    functions.rotateLeft()
    functions.debug()
    setup()
    functions.incrementHead()
    functions.rotateRight()
    functions.incrementHead()
    functions.rotateLeft()
    functions.debug()
}

fun setup() {
    stack.clear()
    println()
    functions.pushInput()
    functions.parseStack()
    functions.debug()
    print(" ->")
}