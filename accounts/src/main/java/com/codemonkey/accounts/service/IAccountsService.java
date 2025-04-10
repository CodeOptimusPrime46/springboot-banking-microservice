package com.codemonkey.accounts.service;

import com.codemonkey.accounts.dto.CustomerDto;

public interface IAccountsService {

  /**
   * @param customerDto - customer dto
   */
  void create(CustomerDto customerDto);

  CustomerDto fetch(String mobileNumber);
}
