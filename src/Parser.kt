import java.math.BigDecimal
import java.math.BigInteger

class Parser {
    var loop = 0
    var charArray = false
    var string = false
    var tempstring = ""
    var variable = false
    var rblock = 0
    var tblock = 0
    var fblock = 0
    var currentVariableName = ""
    var currentBlock = ""
    var executeBlock = false
    var conditional = 0
    var condition = false

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
                if (tblock == 0 && fblock == 0 && !charArray && !string && it == "{") {
                    rblock++

                    if (rblock == 1) {
                        if (debug) {
                            System.err.println("Started reading Repeatable Block")
                        }
                        currentBlock = ""
                        return@forEach
                    }
                }
                if (tblock == 0 && fblock == 0 && !charArray && !string && it == "}") {
                    rblock--
                    if (rblock == 0) {
                        if (debug) {
                            System.err.println("Stopped reading Repeatable Block")
                        }
                        executeBlock = true
                    }
                }
                if (rblock == 0 && fblock == 0 && !string && !charArray && it == "(") {
                    val temp = stack.peek()
                    if (temp is Boolean) {
                        conditional = 1
                        tblock++
                        condition = temp
                        if (tblock == 1) {
                            if (debug) {
                                System.err.println("Started reading True Block")
                            }
                            currentBlock = ""
                            return@forEach
                        }

                    } else if (temp is BigInteger) {
                        conditional = 1
                        tblock++
                        condition = temp > BigInteger.ZERO
                        if (tblock == 1) {
                            if (debug) {
                                System.err.println("Started reading True Block: Condition: $condition")
                            }
                            currentBlock = ""
                            return@forEach
                        }
                    }
                    //return@forEach
                }
                if (rblock == 0 && fblock == 0 && !string && !charArray && it == ")") {
                    conditional = 0
                    tblock--
                    if (tblock == 0) {
                        if (debug) {
                            System.err.println("Stopped reading True Block")
                        }
                        executeBlock = condition
                    }
                }
                if (tblock == 0 && rblock == 0 && !string && !charArray && it == "[") {
                    val temp = stack.peek()
                    if (temp is Boolean) {
                        conditional = 2
                        fblock++
                        condition = temp
                        if (fblock == 1) {
                            if (debug) {
                                System.err.println("Started reading False Block: Condition: $condition")
                            }
                            currentBlock = ""
                            return@forEach
                        }

                    } else if (temp is BigInteger) {
                        conditional = 2
                        fblock++
                        condition = temp > BigInteger.ZERO
                        if (fblock == 1) {
                            if (debug) {
                                System.err.println("Started reading False Block")
                            }
                            currentBlock = ""
                            return@forEach
                        }
                    }
                    //return@forEach
                }

                if (tblock == 0 && rblock == 0 && !string && !charArray && it == "]") {
                    conditional = 0
                    fblock--
                    if (fblock == 0) {
                        if (debug) {
                            System.err.println("Stopped reading False Block")
                        }
                        executeBlock = !condition
                    }
                }
                if ((rblock > 0 && conditional == 0) || (tblock > 0 && conditional == 1) || (fblock > 0 && conditional == 2)) {
                    currentBlock += it
                    if (debug) {
                        System.err.println("Added $it to block")
                    }
                    return@forEach
                }



                if (!string && it == "'") {
                    charArray = !charArray
                    if (debug) {
                        if (charArray) {
                            System.err.println("Started CharArray")
                        } else {
                            System.err.println("Stopped CharArray")
                        }
                    }
                    return@forEach
                }
                if (!charArray && it == "#") {
                    string = !string
                    if (tempstring != "") {
                        stack.push(tempstring)
                        if (debug) {
                            System.err.println("Added $tempstring to Stack")
                        }
                    }
                    if (debug) {
                        if (string) {
                            System.err.println("Started String")
                        } else {
                            System.err.println("Stopped String")
                        }
                    }
                    tempstring = ""
                    return@forEach
                }
                if (charArray) {
                    stack.push(it)
                    if (debug) {
                        System.err.println("Added $it to Stack")
                    }
                    return@forEach
                }
                if (string) {
                    tempstring += it
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
                if (it == "S") {
                    loop += stack.size
                    if (debug) {
                        System.err.println("Looping $loop")
                    }
                    return@forEach
                }
                if (it == "$") {
                    val head = stack.peek()
                    if (head is String) {
                        if (head.codePointCount(0, head.length) == 1) {
                            if (head[0].isHighSurrogate()) {
                                loop += (head[0].toInt().toString() + head[1].toInt().toString()).toInt()
                            } else {
                                loop += head[0].toInt()
                            }
                        } else {
                            loop += head[0].toInt()
                        }
                    } else
                        if (head is BigInteger) {
                            loop += head.toInt()
                        } else
                            if (head is BigDecimal) {
                                loop += head.toInt()
                            }
                    if (debug) {
                        System.err.println("Looping $loop")
                    }
                    return@forEach
                }
                if (it.length > 0 && it[0].isDigit()) {
                    if (loop > 0) {
                        loop = (loop.toString() + it).toInt()
                    } else {
                        loop = it.toInt()
                    }
                    if (debug) {
                        System.err.println("Looping $loop")
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
                    if (debug) {
                        System.err.println("Executed: $it: ${mappings[it.toString()]}")
                    }
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