class Parser {
    var loop = 0
    var string = false
    var variable = false
    var block = false
    var currentVariableName = ""
    var currentBlock = ""
    var executeBlock = false


    fun parseCode(code: String, master: Boolean = true) {
        if (master) {
            functions.pushInput()
            functions.parseStack()
        }

        code.split("").forEach {
            variables["size"] = stack.size
            try {
                if (it.length == 0) {
                    return@forEach
                }
                if (!string && it == "{") {
                    block = true
                    currentBlock = ""
                    return@forEach
                }
                if (!string && it == "}") {
                    block = false
                    executeBlock = true
                }
                if (block) {
                    currentBlock += it
                    return@forEach
                }
                if (it == "'") {
                    string = !string
                    return@forEach
                }
                if (string) {
                    stack.push(it)
                    return@forEach
                }

                if (it == "%") {
                    if (!currentVariableName.isBlank()) {
                        if (!variables.containsKey(currentVariableName)) {
                            variables[currentVariableName] = stack.peek()
                        } else {
                            stack.push(variables[currentVariableName]!!)
                        }
                        currentVariableName = ""
                    }
                    variable = !variable
                    return@forEach
                }
                if (variable) {
                    currentVariableName += it
                    return@forEach
                }
                if(it == "S"){
                    loop += stack.size
                    return@forEach
                }
                if(it == "$"){
                    loop += stack.pop().toString().toInt()
                    return@forEach
                }
                if (it.length > 0 && it[0].isDigit()) {
                    if (loop > 0) {
                        loop = (loop.toString() + it).toInt()
                    } else {
                        loop = it.toInt()
                    }
                    return@forEach
                }
                if (loop == 0) {
                    loop = 1
                }
                while (loop > 0) {
                    loop--
                    if (executeBlock) {
                        val parser = Parser()
                        parser.parseCode(currentBlock, false)
                        continue
                    }
                    mappings[it.toString()]?.invoke()
                }
                executeBlock = false
                loop = 0
            } catch(t: Throwable) {
                stack.forEach { System.err.print(" " + it) }
                variables.forEach { k, v -> System.err.println(k + ": " + v) }
                System.err.println(code)
                System.err.println(it)
                t.printStackTrace(System.err)
            }
        }

        if (master && !(code.endsWith("P") || code.endsWith("D"))) {
            functions.debug()
        }
    }
}