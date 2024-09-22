package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;

@SpringBootTest
class AddressServiceImplTest {

	@Autowired
    private AddressServiceImpl addressService;

    @MockBean
    private AddressRepository addressRepository;

	@Test
    public void testSaveAddress() {
        Address address = new Address(1L, "Mangalore", null);
        Mockito.when(addressRepository.save(address)).thenReturn(address);
        Address savedAddress = addressService.saveAddress(address);

        Mockito.verify(addressRepository).save(address);
        assertEquals(address, savedAddress);
    }

}
