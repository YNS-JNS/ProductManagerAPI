package ma.tuto.productmanagerapi.application.service;

import ma.tuto.productmanagerapi.application.dto.UserRequestDTO;
import ma.tuto.productmanagerapi.application.dto.UserResponseDTO;

import java.util.List;

public interface IUserService {

    UserResponseDTO createUserServ(UserRequestDTO userRequestDTO);

    List<UserResponseDTO> getUsersServ();
}
