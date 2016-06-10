import java.util.*

var stack = Stack()
var input: List<String>? = null
val mappings = hashMapOf(
        "+" to { functions.incrementHead() },
        "_" to { functions.pushInput() },
        "p" to { functions.parseStack() },
        "D" to { functions.debug() },
        "»" to { functions.incrementStack() },
        "=" to { stack.clear() },
        ">" to { functions.rotateRight() },
        "<" to { functions.rotateLeft() },
        "-" to { functions.decrementHead() },
        "«" to { functions.decrementStack() },
        "r" to { functions.reverse() },
        "s" to { functions.sum() },
        "P" to { functions.popPrint() }
)
val variables = HashMap<String, Any>()

fun main(args: Array<String>) {
    val code = args[0]
    if (args.size > 1) {
        input = args.slice(1..args.size).toList()
    }
    var loop = 0
    var string = false
    var variable = false
    var currentVariableName = ""
    functions.pushInput()
    functions.parseStack()
    code.split("").forEach {
        val key = it
        if (key == "'") {
            string = !string
            return@forEach
        }
        if (string) {
            stack.push(it)
            return@forEach
        }
        if(key == "%"){
            if(!currentVariableName.isBlank() ) {
                if(!variables.containsKey(currentVariableName)) {
                    variables[currentVariableName] = stack.peek()
                }else {
                    stack.push(variables[currentVariableName]!!)
                }
                currentVariableName = ""
                variable = !variable
                return@forEach

            }
            variable = !variable
            return@forEach
        }
        if(variable){
            currentVariableName+=it
            return@forEach
        }

        if (key.length > 0 && key[0].isDigit()) {
            if (loop > 0) {
                loop = (loop.toString() + key).toInt()
                return@forEach
            } else {
                loop = key.toInt()
                return@forEach
            }
        }
        if (loop == 0) {
            loop = 1
        }
        while (loop > 0) {
            loop--
            mappings[key.toString()]?.invoke()
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