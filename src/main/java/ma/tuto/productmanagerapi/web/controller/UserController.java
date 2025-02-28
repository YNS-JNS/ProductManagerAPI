package ma.tuto.productmanagerapi.web.controller;

import ma.tuto.productmanagerapi.application.dto.UserRequestDTO;
import ma.tuto.productmanagerapi.application.dto.UserResponseDTO;
import ma.tuto.productmanagerapi.application.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUserCtrl(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userCreated = userService.createUserServ(userRequestDTO);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsersCtrl() {
        List<UserResponseDTO> userList = userService.getUsersServ();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
