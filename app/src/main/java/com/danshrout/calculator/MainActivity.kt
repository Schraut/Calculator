package com.danshrout.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        NumberDisplay.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        NumberDisplay.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            NumberDisplay.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if(lastNumeric) {
            var numberDisplayValue = NumberDisplay.text.toString()

            var prefix = ""
            try {
                if(numberDisplayValue.startsWith("-")) {
                    prefix = "-"
                    numberDisplayValue = numberDisplayValue.substring(1)
                }

                if (numberDisplayValue.contains("-")) {
                    val splitValue = numberDisplayValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    NumberDisplay.text = removeZeroAfterDecimal((one.toDouble() - two.toDouble()).toString())
                }
                else if (numberDisplayValue.contains("*")) {
                    val splitValue = numberDisplayValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    NumberDisplay.text = removeZeroAfterDecimal((one.toDouble() * two.toDouble()).toString())
                }
                else if (numberDisplayValue.contains("+")) {
                    val splitValue = numberDisplayValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    NumberDisplay.text = removeZeroAfterDecimal((one.toDouble() + two.toDouble()).toString())
                }
                else if (numberDisplayValue.contains("/")) {
                    val splitValue = numberDisplayValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    NumberDisplay.text = removeZeroAfterDecimal((one.toDouble() / two.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDecimal(result: String) : String {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    fun onOperator(view: View) {
        if(lastNumeric && !isOperatorAdded(NumberDisplay.text.toString())) {
            NumberDisplay.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
                    value.contains("*") ||
                    value.contains("+") ||
                    value.contains("-")
        }
    }
}