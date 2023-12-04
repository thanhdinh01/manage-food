package demo.internspring1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomHandlerException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorException handleNotFoundException(NotFoundException ex){
        return new ErrorException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorException handleBadRequest(BadRequestException ex){
        return new ErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ExistedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorException handleExistedConflict(ExistedException ex){
        return new ErrorException(HttpStatus.CONFLICT, ex.getMessage());
    }
}
