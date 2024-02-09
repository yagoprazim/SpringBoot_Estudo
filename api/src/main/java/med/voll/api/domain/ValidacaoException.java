package med.voll.api.domain;

import org.springframework.http.HttpStatus;

public class ValidacaoException extends RuntimeException{
    private HttpStatus status;

    public ValidacaoException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
