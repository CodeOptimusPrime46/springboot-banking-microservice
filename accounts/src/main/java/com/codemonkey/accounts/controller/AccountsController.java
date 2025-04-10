package com.codemonkey.accounts.controller;

import com.codemonkey.accounts.constants.AccountsConstants;
import com.codemonkey.accounts.dto.CustomerDto;
import com.codemonkey.accounts.dto.ResponseDto;
import com.codemonkey.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

  /**
   * Since we have single constructor we dont need to autowire specifically,
   */
  private IAccountsService service;

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
    service.create(customerDto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
  }

  @GetMapping("/fetch")
  public ResponseEntity<CustomerDto> fetAccountDetails(@RequestParam String mobileNumber) {
    return ResponseEntity.ok(service.fetch(mobileNumber));
  }


}
