package ir.freeland.springboot.annotation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NationalCodeValidator implements ConstraintValidator<IranianNationalCode, String>{
	

	@Override
	public boolean isValid(String nc, ConstraintValidatorContext context) {
		int check = Character.getNumericValue(nc.charAt(9));
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(nc.charAt(i)) * (10 - i);
        }
        int remainder = sum % 11;
        if(nc==null || !nc.matches("^\\d{10}$")) {
            System.out.println("National code is not valid");
            return false;
        }else if ((remainder < 2 && check == remainder) || (remainder >= 2 && check + remainder == 11)){

            return true ;
        }else{
            System.out.println("National code is not valid");
            return false;
        }
	}

}
