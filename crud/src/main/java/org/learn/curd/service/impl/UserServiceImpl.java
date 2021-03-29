package org.learn.curd.service.impl;

import org.learn.curd.dto.UserDTO;
import org.learn.curd.dto.UserResponseDTO;
import org.learn.curd.exception.BusinessException;
import org.learn.curd.exception.StatusCode;
import org.learn.curd.logging.BaseLogger;
import org.learn.curd.logging.CustomLogFactory;
import org.learn.curd.logging.LogLevel;
import org.learn.curd.model.User;
import org.learn.curd.repository.AuthorRepository;
import org.learn.curd.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final BaseLogger _logger = CustomLogFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponseDTO createUser(UserDTO user) {
        _logger.log(LogLevel.INFO,"Create author service method invoked");
        User userModel = modelMapper.map(user, User.class);
        User userId =  authorRepository.save(userModel);
        UserResponseDTO dto = modelMapper.map(userId, UserResponseDTO.class);
        _logger.log(LogLevel.INFO,"Author details saved and generator Id " + userId);
        return dto;
    }

    @Override
    public UserResponseDTO getUser(Long userId) {
        _logger.log(LogLevel.INFO,"Get author service method invoked");
        Optional<User> user = authorRepository.findById(userId);
        if(user.isEmpty()) {
            _logger.log(LogLevel.ERROR,"User does n't exist " + userId);
            throw new BusinessException(StatusCode.ERR_USR_0001);
        }
        return modelMapper.map(user.get(), UserResponseDTO.class);
    }

}
