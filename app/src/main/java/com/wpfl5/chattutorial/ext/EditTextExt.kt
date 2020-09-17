package com.wpfl5.chattutorial.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString().trim())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
    })
}

/*
    et_email.validate("valid email address required") { s -> s.isValidEmail() }
 */

fun EditText.validate(message: String, validator: (String) -> Boolean) : Boolean{
    afterTextChanged {
        error = if(validator(it)) null else message
    }
    this.error = if(validator(this.getString())) null else message
    return validator(this.getString())
}

fun EditText.getString() : String = this.text.toString()

var TextInputLayout.textString: String?
    get() = editText?.text?.toString()
    set(value) { editText?.setText(value) }

fun TextInputLayout.errorText(textResource: Int) {
    this.error = resources.getString(textResource)
}

fun TextInputLayout.errorInit() {
    error = null
}

fun Any.initError(vararg input: TextInputLayout){
    input.forEach { it.errorInit() }
}

fun TextInputLayout.validate(message: String, validator: (String?) -> Boolean) : Boolean{
    val text = this.textString
    editText?.afterTextChanged {
        error = if(validator(it)) null else message
    }
    this.error = if(validator(text)) null else message
    return validator(text)
}