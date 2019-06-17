package com.main.meetalocal;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * A class for validating any kind of user input
 */
public class Validator {

    /**
     * Validate user inputs and check if they are empty
     * @param userInputs the user inputs to validate
     * @return true (valid) or false (invalid)
     */
    public static boolean validateUserInputs(EditText [] userInputs) {
        boolean isValid = true;
        for(EditText userInput : userInputs) {
            if(TextUtils.isEmpty(userInput.getText().toString())) {
                isValid = false;
                userInput.setError("This field is required!");
            }
        }
        return isValid;
    }

    /**
     * Validate the password and the password confirm
     * Check if they are empty or doesn't match
     * @param password password input
     * @param passwordConfirm password confirm input
     * @return true (valid) or false (invalid)
     */
    public static boolean validatePasswords(EditText password, EditText passwordConfirm) {
        boolean isValid = true;
        String passwordText = password.getText().toString();
        String passwordConfirmText = passwordConfirm.getText().toString();

        if(!passwordText.equals(passwordConfirmText)) {
            isValid = false;
            passwordConfirm.setError("Passwords don't match!");
        } else if(TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(passwordConfirmText)) {
            isValid = false;
            password.setError("Please pick a password!");
            passwordConfirm.setError("Please confirm your password!");
        }

        return isValid;
    }
}
