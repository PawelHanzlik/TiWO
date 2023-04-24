package ph.agh.tiwo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private String name;
    @JsonSerialize(using = ToStringSerializer.class)
    private Double quantity;
    private String type;
    private String url;
}
