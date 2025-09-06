package sarinxo.service.mdmservice.exception;

/**
 * Основное исключение приложения
 */
public class MdmServiceException extends RuntimeException {

    public MdmServiceException(String message) {
        super(message);
    }

    public MdmServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
