package com.example.myapplication222

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {
    private lateinit var resultView: TextView
    private var expression: String = ""
    private var lastResult: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        resultView = findViewById(R.id.textViewResult)

        val numButtons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        )
        for (id in numButtons) {
            findViewById<Button>(id).setOnClickListener { append((it as Button).text.toString()) }
        }
        findViewById<Button>(R.id.buttonDot).setOnClickListener { append(".") }
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { append("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { append("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { append("*") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { append("/") }
        findViewById<Button>(R.id.buttonClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculate() }
    }

    private fun append(str: String) {
        expression += str
        resultView.text = expression
    }

    private fun clear() {
        expression = ""
        lastResult = "0"
        resultView.text = "0"
    }

    private fun calculate() {
        try {
            val result = eval(expression)
            lastResult = result.toString()
            resultView.text = lastResult
            expression = ""
        } catch (e: Exception) {
            resultView.text = "Ошибка"
            expression = ""
        }
    }

    // Простой парсер выражения (только +, -, *, /, числа и точка)
    private fun eval(expr: String): Double {
        return object {
            var pos = -1
            var ch = 0
            fun nextChar() { ch = if (++pos < expr.length) expr[pos].code else -1 }
            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) { nextChar(); return true }
                return false
            }
            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < expr.length) throw RuntimeException("Unexpected: " + expr[pos])
                return x
            }
            fun parseExpression(): Double {
                var x = parseTerm()
                while(true) {
                    when {
                        eat('+'.code) -> x += parseTerm()
                        eat('-'.code) -> x -= parseTerm()
                        else -> return x
                    }
                }
            }
            fun parseTerm(): Double {
                var x = parseFactor()
                while(true) {
                    when {
                        eat('*'.code) -> x *= parseFactor()
                        eat('/'.code) -> x /= parseFactor()
                        else -> return x
                    }
                }
            }
            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor() // унарный плюс
                if (eat('-'.code)) return -parseFactor() // унарный минус
                var x: Double
                val startPos = pos
                if (ch in '0'.code..'9'.code || ch == '.'.code) {
                    while (ch in '0'.code..'9'.code || ch == '.'.code) nextChar()
                    x = expr.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                return x
            }
        }.parse()
    }
}
