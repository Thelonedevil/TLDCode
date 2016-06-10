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
        "P" to { functions.popPrint() },
        "j" to { functions.join() },
        "," to { functions.comma() },
        "n" to { println()}
)
val variables = HashMap<String, Any>()

fun main(args: Array<String>) {
    val code = args[0]
    if (args.size > 1) {
        input = args.slice(1..args.size).toList()
    }
    val parser = Parser()
    parser.parseCode(code)

}