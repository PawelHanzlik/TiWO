package ph.agh.tiwo.exception.Messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionMessages {

    NO_SUCH_USER("No such user"),
    NO_SUCH_PRODUCT("No such product"),
    NO_SUCH_PRODUCT_LIST("No such product list"),

    USER_ALREADY_EXISTS("User already exists"),

    PRODUCT_ALREADY_EXISTS("Product already exists"),
    NEGATIVE_MONEY("Incorrect money amount"),
    PRODUCT_LIST_ALREADY_EXISTS("Product list already exists");
    @Getter
    private final String errorMessage;
}
