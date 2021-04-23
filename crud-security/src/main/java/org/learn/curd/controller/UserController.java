package org.learn.curd.controller;

import io.swagger.annotations.ApiOperation;
import org.learn.curd.dto.UserDTO;
import org.learn.curd.dto.UserResponseDTO;
import org.learn.curd.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@ApiOperation(value = "API to hold details/information about author")
public class UserController {

    private static final Logger _logger = LoggerFactory.getLogger("AuthorController");

    @Autowired
    private UserService userServiceImpl;

    @PostMapping
    @PreAuthorize("@apiPreAuthCheck.hasPermission('CREATE_USER')")
    @ApiOperation(value = "API to create a User")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        _logger.debug("create author controller called");
        UserResponseDTO authorId = userServiceImpl.createUser(userDTO);
        _logger.debug("create author created with Id " + authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorId);
    }

    @GetMapping("/{emailId}")
    @ApiOperation(value = "API to fetch User")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String emailId) {
        _logger.debug("get author controller called");
        UserResponseDTO dto = userServiceImpl.getUser(emailId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
