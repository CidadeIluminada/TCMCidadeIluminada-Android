package br.com.bilac.tcm.cidadeiluminada2.protocolos.validators;

import android.widget.TextView;

/**
 * Created by arthur on 23/05/15.
 */
public class CEPValidator extends TextValidatorBase {
    public static final String message = "CEP Inválido";

    public CEPValidator(TextView textView, ValidationState validationState) {
        super(textView, message, validationState);
    }

    @Override
    public boolean validate(String text) {
        return text.matches("^\\d{5}-?\\d{3}$");
    }
}
