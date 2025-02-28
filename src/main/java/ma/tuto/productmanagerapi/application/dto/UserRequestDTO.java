package ma.tuto.productmanagerapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
}
