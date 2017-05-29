import java.io.File
import java.util.*

var stack = Stack()
var input: List<String>? = null
var debug = false
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
        "n" to { println() },
        "e" to { functions.peekPrint() },
        "c" to { functions.copy() },
        "*" to { functions.product() },
        "^" to { functions.power() },
        "d" to { functions.difference() },
        "v" to { functions.popHead() },
        "V" to { functions.popTail() },
        "|" to { functions.split() },
        "/" to { functions.division() },
        "\\" to { functions.prime() },
        "G" to { functions.greater() },
        "L" to { functions.less() },
        "E" to { functions.equals() },
        "&" to {functions.eval()}
)
val variables = HashMap<String, Any>()

fun main(args: Array<String>) {
    var code = ""
    if (args.contains("-i")) {
        input = args.slice(args.indexOf("-i") + 1..args.size - 1)
    } else if (args.contains("--input")) {
        input = args.slice(args.indexOf("--input") + 1..args.size - 1)
    }
    if (args.contains("-d") || args.contains("--debug")) {
        debug = true
    }
    if (args.contains("-r")) {
        println("Welcome to TLDStack REPL")
        functions.pushInput()
        functions.parseStack()
        while (true) {
            print(">\t")
            val line = readLine() ?: continue
            Parser().parseCode(line + "nD", false)
            println()

        }
    }

    //val flags = args.filter { (it.startsWith("--") && it.length > 3) || (it.startsWith("-") && it.length == 2) }
    if (args.filter { it == "-c" || it == "--code" || it == "--file" || it == "-f" }.size > 1) {
        System.err.println("Can only use one of '-c --code -f --file'")
    }
    if (args.contains("-c")) {
        code = args[args.indexOf("-c") + 1]
    } else if (args.contains("--code")) {
        code = args[args.indexOf("--code") + 1]
    } else if (args.contains("-f")) {
        val filepath = args[args.indexOf("-f") + 1]
        code = File(filepath).readText()
    } else if (args.contains("--file")) {
        val filepath = args[args.indexOf("--file") + 1]
        code = File(filepath).readText()
    }
    val parser = Parser()
    parser.parseCode(code)

}