package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number>{

	private int numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constrainAnnotation) {
		this.numeroMultiplo = constrainAnnotation.numero();
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		boolean valido = true;
		
		if(value != null) {
			var valor = BigDecimal.valueOf(value.doubleValue());
			var multiplo =  BigDecimal.valueOf(this.numeroMultiplo);
			var resto = valor.remainder(multiplo);
			
			valido = BigDecimal.ZERO.compareTo(resto) == 0;
			
		}
		
		return valido;
	}

	
	
}
