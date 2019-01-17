package br.org.pti.fpti_base.application.validation;

import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

@Component
public class StandaloneBeanValidation {
	/**
	 * valida uma entidade com anotações de validação e joga as exceções pro front (faz isso sem precisar salvar a entidade)
	 * @param bean
	 */
	public <T> void validateAndThrow(T bean) {
		Configuration<?> config = Validation.byDefaultProvider().configure();
		ValidatorFactory factory = config.buildValidatorFactory();
		Validator validator = factory.getValidator();
		factory.close();
		
		//valida qualquer classe com annotations e pega a lista de erros de validação
		Set<ConstraintViolation<T>> violations = validator.validate(bean);
		//violations.forEach(v -> System.out.println(v.getPropertyPath() + "- " + v.getMessage()));
		
		
		//joga as exceções porém com as mensagens rescritas de RestResponseEntityExceptionHandler e não as padrões
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		
		return;
	}	
	
	/**
	 * valida uma entidade com anotações de validação e joga as exceções pro front (faz isso sem precisar salvar a entidade)
	 * @param bean
	 * @return a propria entidade validada de volta
	 */
	public <T> T validateAndThrowReturning(T bean) {
		Configuration<?> config = Validation.byDefaultProvider().configure();
		ValidatorFactory factory = config.buildValidatorFactory();
		Validator validator = factory.getValidator();
		factory.close();
		
		Set<ConstraintViolation<T>> violations = validator.validate(bean);
		
		/*violations.forEach(v -> {
			System.out.println(v.getPropertyPath() + "- " + v.getMessage());
		});*/
		
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		
		return bean;
	}
	// Post post = modelMapper.map(tipologiaDto, Tipologia.class); //exemplo de conversão de dto para uma entidade (tem de ter os mesmos props) 
	
}
