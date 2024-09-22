package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;

@Service
public class AddressServiceImpl{
	@Autowired
	private AddressRepository addressRepository;
	
	@Transactional
	public Address saveAddress(Address address) {
		return addressRepository.save(address);
		//throw new RuntimeException("Getting error!!");
	}

}
