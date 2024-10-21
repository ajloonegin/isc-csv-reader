package ir.freeland.springboot.annotation;

import java.math.BigInteger;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IbanCodeValidator implements ConstraintValidator<IbanCode, String> {
	
	@Override
	public boolean isValid(String iban,ConstraintValidatorContext cxt) {

        String numericCountryCode = "1827";
        String checkDigitsValue = iban.substring(2, 4);

        BigInteger standardModNumber = BigInteger.valueOf(97);
        int countryCode = 2;
        int checkDigits = 2;

        String preparedIban = iban.substring(countryCode).substring(checkDigits).concat(numericCountryCode).concat(checkDigitsValue);

        return new BigInteger(preparedIban).mod(standardModNumber).intValue() == 1;
    }

}
