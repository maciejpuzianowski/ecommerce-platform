package pl.mp.ecommerce_platfrom.common_models.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String fullName;

    private String email;

    private String password;
}
