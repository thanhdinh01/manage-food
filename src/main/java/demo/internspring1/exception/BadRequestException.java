package demo.internspring1.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String mes){
        super(mes);
    }
}
