package lernia.backosys.laboration02.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFoundException(ResourceNotFoundException exception){
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());

        problemDetail.setType(URI.create("https://localhost:8080/RTFM"));
        problemDetail.setTitle("Resource Not Found!");
        problemDetail.setProperty("Requested Resource: ", exception.getId());

        return problemDetail;
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ProblemDetail handleNotAuthorizedException (NotAuthorizedException exception){
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getLocalizedMessage());

        problemDetail.setType(URI.create("https://localhost:8080/RTFM"));
        problemDetail.setTitle("You do not have proper Authorization for this action!");
        problemDetail.setProperty("Requested Resource: ", exception.getId());

        return problemDetail;
    }

    @ExceptionHandler(ConstraintException.class)
    public ProblemDetail handleConstraintException (ConstraintException exception){
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());

        problemDetail.setType(URI.create("https://localhost:8080/RTFM"));
        problemDetail.setTitle("You have provided bad content!");
        problemDetail.setProperty("Provided Resource: ", exception.getId());

        return problemDetail;
    }

}
