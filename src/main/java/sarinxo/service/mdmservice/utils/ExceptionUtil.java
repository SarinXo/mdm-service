package sarinxo.service.mdmservice.utils;

import java.util.Objects;

public class ExceptionUtil {

    public static String causeChain(Throwable e) {
        int maxIterations = 20;
        return causeChain(e, maxIterations);
    }

    /**
     * Делает развертку только из исключений и сообщений к ним
     *
     * @param e             исключение
     * @param maxIterations кол-во максимальной вложенности
     * @return Отформатированную строку с stacktrace
     */
    public static String causeChain(Throwable e, int maxIterations) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception stack trace:\n");
        while (Objects.nonNull(e.getCause()) && maxIterations >= 0) {
            sb.append("[\nMessage:").append(e.getMessage()).append("\n");
            sb.append("Exception:").append(e.getCause()).append("\n]\n");
            e = e.getCause();
            maxIterations--;
        }
        return sb.toString();
    }

}
