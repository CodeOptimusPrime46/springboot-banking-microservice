package com.codemonkey.accounts.service.impl;

import com.codemonkey.accounts.constants.AccountsConstants;
import com.codemonkey.accounts.dto.AccountsDto;
import com.codemonkey.accounts.dto.CustomerDto;
import com.codemonkey.accounts.entity.Accounts;
import com.codemonkey.accounts.entity.Customer;
import com.codemonkey.accounts.exception.CustomerAlreadyExistsException;
import com.codemonkey.accounts.exception.ResourceNotFoundException;
import com.codemonkey.accounts.mapper.AccountsMapper;
import com.codemonkey.accounts.mapper.CustomerMapper;
import com.codemonkey.accounts.repository.AccountsRepository;
import com.codemonkey.accounts.repository.CustomerRepository;
import com.codemonkey.accounts.service.IAccountsService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

  @Autowired
  private AccountsRepository accountsRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public void create(CustomerDto customerDto) {
    Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
    Optional<Customer> customerOptional = customerRepository.findByMobileNumber(
        customer.getMobileNumber());
    customer.setCreatedBy("Anonymous");
    customer.setCreatedAt(LocalDateTime.now());
    if (customerOptional.isPresent()) {
      throw new CustomerAlreadyExistsException(
          "Customer already registered with given mobile number " + customer.getMobileNumber());
    }
    Customer savedCustomer = customerRepository.save(customer);
    accountsRepository.save(createNewAccount(savedCustomer));
  }

  /**
   * Dummy account creator
   */
  private Accounts createNewAccount(Customer customer) {
    Accounts newAccount = new Accounts();
    newAccount.setCustomerId(customer.getCustomerId());
    long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

    newAccount.setAccountNumber(randomAccNumber);
    newAccount.setAccountType(AccountsConstants.SAVINGS);
    newAccount.setBranchAddress(AccountsConstants.ADDRESS);
    newAccount.setCreatedBy("Anonymus");
    newAccount.setCreatedAt(LocalDateTime.now());
    return newAccount;
  }

  @Override
  public CustomerDto fetch(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNUmber", mobileNumber));
    Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
        .orElseThrow(() -> new ResourceNotFoundException("Accounts", "customerId",
            customer.getCustomerId().toString()));
    CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    customerDto.setAccounts(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
    return customerDto;
  }

}
