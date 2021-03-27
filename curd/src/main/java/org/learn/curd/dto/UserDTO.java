package org.learn.curd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Create a user")
public class UserDTO {

    @ApiModelProperty(
            allowableValues = "Shanmugam",
            required = true
    )
    private String firstName;
    @ApiModelProperty(
            allowableValues = "Muthuraj",
            required = true
    )
    private String lastName;
    @ApiModelProperty(
            allowableValues = "Chennai",
            required = true
    )
    private String address;

    @ApiModelProperty(
            allowableValues = "shanmugam@shan.com",
            required = true
    )
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
