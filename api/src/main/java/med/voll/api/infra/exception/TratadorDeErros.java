package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception){
        var erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Map<String, String>> trataErros(ValidacaoException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("erro", exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(response);
    }

    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }


    //ERROS PARA TESTAR DEPOIS:

//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity tratarErro404() {
//        return ResponseEntity.notFound().build();
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
//        var erros = ex.getFieldErrors();
//        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity tratarErro400(HttpMessageNotReadableException ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity tratarErroBadCredentials() {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity tratarErroAuthentication() {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity tratarErroAcessoNegado() {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity tratarErro500(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
//    }
//
//    private record DadosErroValidacao(String campo, String mensagem) {
//        public DadosErroValidacao(FieldError erro) {
//            this(erro.getField(), erro.getDefaultMessage());
//        }
//    }
//}
}
