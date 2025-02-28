package ma.tuto.productmanagerapi.application.mapper;

import ma.tuto.productmanagerapi.application.dto.UserRequestDTO;
import ma.tuto.productmanagerapi.application.dto.UserResponseDTO;
import ma.tuto.productmanagerapi.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Integrates with Spring's dependency injection
public interface UserMapper {

    // Maps Entity to DTO
    UserRequestDTO toDTO(User user);

    // Maps DTO to Entity
    User toEntity(UserRequestDTO userRequestDTO);

    // Maps Entity to ResponseDTO
    UserResponseDTO toResDTO(User user);
}
