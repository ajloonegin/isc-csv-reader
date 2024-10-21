package ir.freeland.springboot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;



@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NationalCodeValidator.class)
public @interface IranianNationalCode {
	String message() default "Invalid National Code: Enter Standard National Code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
