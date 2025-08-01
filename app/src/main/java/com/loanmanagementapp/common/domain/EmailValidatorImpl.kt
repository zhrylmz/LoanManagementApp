package com.loanmanagementapp.common.domain

import androidx.core.util.PatternsCompat

class EmailValidatorImpl(
    override val errorMessage: String = "Email is not valid"
) : TextFieldValidator<String> {

    override fun validate(candidate: String?): Boolean {
        candidate?.let {
            return isValidEmail(target = it)
        }
        return false
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return target.isNotEmpty() &&
                PatternsCompat.EMAIL_ADDRESS.matcher(target).matches()
    }
}
