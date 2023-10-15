package br.com.votacao.validations;

import br.com.votacao.validations.impl.UsuarioConstraintImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsuarioConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioConstraint {
    String message() default "Usuário invalido";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
