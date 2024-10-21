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
@Constraint(validatedBy = IbanCodeValidator.class)

public @interface IbanCode {
	
	public String message() default "Invalid Iban code: must be standard";
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
    
  
	

}
