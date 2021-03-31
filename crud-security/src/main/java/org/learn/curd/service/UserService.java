package org.learn.curd.service;

import org.learn.curd.dto.UserDTO;
import org.learn.curd.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserDTO author);

    UserResponseDTO getUser(Long authorId);

}
