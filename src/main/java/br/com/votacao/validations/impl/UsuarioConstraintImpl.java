package br.com.votacao.validations.impl;


import br.com.votacao.validations.UsuarioConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UsuarioConstraintImpl implements ConstraintValidator<UsuarioConstraint, String> {

    @Override
    public void initialize(UsuarioConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String nomeDoUsuario, ConstraintValidatorContext constraintValidatorContext) {
        if(nomeDoUsuario == null || nomeDoUsuario.trim().isEmpty() || nomeDoUsuario.contains(" ")){
            return false;
        }
        return true;
    }
}
