package com.rts.tap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SuppressWarnings("serial")
@ControllerAdvice
public class GlobalException extends Throwable {
	
	@ExceptionHandler(BudgetNotFoundException.class)
	public ResponseEntity<String> handleBudgetNotFoundException (BudgetNotFoundException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AddBudgetException.class)
	public ResponseEntity<String> handleAddBudgetException (AddBudgetException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UpdateBudgetException.class)
	public ResponseEntity<String> handleUpdateBudgetException (UpdateBudgetException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_MODIFIED);
	}
	
	@ExceptionHandler(DeleteBudgetException.class)
	public ResponseEntity<String> handleDeleteBudgetException (DeleteBudgetException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
}
