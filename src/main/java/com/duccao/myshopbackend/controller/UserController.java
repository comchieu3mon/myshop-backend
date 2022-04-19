package com.duccao.myshopbackend.controller;

import com.duccao.myshopbackend.domains.dto.RegisteredUserDTO;
import com.duccao.myshopbackend.domains.dto.UserDTO;
import com.duccao.myshopbackend.exception.InvalidRequestException;
import com.duccao.myshopbackend.repository.ProductRepository;
import com.duccao.myshopbackend.services.UserService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {

  @NonNull UserService userService;
  @NonNull ProductRepository productRepository;

  @PostMapping(value = "/register")
  public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisteredUserDTO registeredUserDTO)
      throws InvalidRequestException {
    UserDTO res = userService.create(registeredUserDTO);
    return ResponseEntity.ok().body(res);
  }

  //  @GetMapping
  //  public ResponseEntity<List<ProductDTO>> findByCategory() {
  //    return ResponseEntity.ok()
  //        .body(
  //            ProductMapper.INSTANCE.lstProductEntityToLstProductDTO(
  //                productRepository.findByCategory_Id(
  //                    UUID.fromString("dc838cee-ed71-4ad3-b840-cdb1e3b0e606"))));
  //  }

  @GetMapping
  @RolesAllowed({"ROLE_ADMIN"})
  //  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<List<UserDTO>> findAll() {
    return ResponseEntity.ok().body(userService.findAll());
  }

  @GetMapping("/{id}")
  @RolesAllowed({"ROLE_ADMIN"})
  //  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<UserDTO> findById(@PathVariable(name = "id") String id) {
    return ResponseEntity.ok().body(userService.findById(id));
  }

  @DeleteMapping("/{id}")
  @RolesAllowed({"ROLE_ADMIN"})
  public void deleteById(@PathVariable(name = "id") String id) {
    userService.deleteById(id);
  }
}
