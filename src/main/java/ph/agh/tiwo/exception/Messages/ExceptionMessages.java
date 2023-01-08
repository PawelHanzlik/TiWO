package ph.agh.tiwo.exception.Messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionMessages {

    NO_SUCH_USER("No such user"),
    NO_SUCH_PRODUCT("No such product"),
    NO_SUCH_PRODUCT_LIST("No such product list"),

    USER_ALREADY_EXISTS("User already exists");
    @Getter
    private final String errorMessage;
}
