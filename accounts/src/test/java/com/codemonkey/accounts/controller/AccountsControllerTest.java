package com.codemonkey.accounts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AccountsController.class)
class AccountsControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Test
  public void hello() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/sayHello"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Hello World!"));
  }

  @Test
  void hi() {
  }
}