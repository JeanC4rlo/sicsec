package br.cefetmg.sicsec.Exceptions;

public class CorrecaoException extends RuntimeException {

    public CorrecaoException(String mensagem) {
        super(mensagem);
    }

    public CorrecaoException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}