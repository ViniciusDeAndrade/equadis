package pt.com.equadis.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> {
                        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, field.getDefaultMessage());
                        problem.setTitle("The input data does not match our requirements");
                        return problem;
                    }
                )
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleCustomerNotFound(CustomerNotFoundException exception) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problem.setTitle("Unknown Customer");
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(WithdrawException.class)
    public ResponseEntity<ProblemDetail> handleWithdrawException(WithdrawException exception) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problem.setTitle("insufficient funds");
        return ResponseEntity.badRequest().body(problem);
    }


}
