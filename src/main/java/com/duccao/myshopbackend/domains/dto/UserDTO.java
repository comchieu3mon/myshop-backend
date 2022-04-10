package com.duccao.myshopbackend.domains.dto;

import java.util.UUID;

public record UserDTO (UUID id, String email, String firstName, String lastName, boolean active) {

}
