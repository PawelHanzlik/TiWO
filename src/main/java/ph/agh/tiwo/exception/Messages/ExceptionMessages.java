package ph.agh.tiwo.exception.Messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionMessages {

    NO_SUCH_USER("No such user"),
    NO_SUCH_PRODUCT("No such product");

    @Getter
    private final String errorMessage;
}
