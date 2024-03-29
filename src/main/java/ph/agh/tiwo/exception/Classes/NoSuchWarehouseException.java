package ph.agh.tiwo.exception.Classes;


import ph.agh.tiwo.exception.Messages.ExceptionMessages;

import java.io.Serial;

public class NoSuchWarehouseException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 2645315523262426567L;

    public NoSuchWarehouseException() {
        super(ExceptionMessages.NO_SUCH_WAREHOUSE.getErrorMessage());
    }
}
