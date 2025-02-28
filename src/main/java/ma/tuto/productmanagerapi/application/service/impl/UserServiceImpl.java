package ma.tuto.productmanagerapi.application.service.impl;

import ma.tuto.productmanagerapi.application.dto.UserRequestDTO;
import ma.tuto.productmanagerapi.application.dto.UserResponseDTO;
import ma.tuto.productmanagerapi.application.mapper.UserMapper;
import ma.tuto.productmanagerapi.application.service.IUserService;
import ma.tuto.productmanagerapi.domain.model.User;
import ma.tuto.productmanagerapi.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO createUserServ(UserRequestDTO userRequestDTO) {
        User userEntity = userMapper.toEntity(userRequestDTO);
        return userMapper.toResDTO(userRepository.save(userEntity));
    }

    @Override
    public List<UserResponseDTO> getUsersServ() {
        return userRepository.findAll().stream()
                .map(userMapper::toResDTO)
                .collect(Collectors.toList());
    }
}
