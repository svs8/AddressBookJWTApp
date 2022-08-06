package com.bridgelabz.addressbookjwtapp.service;

import com.bridgelabz.addressbookjwtapp.entity.CustomUserDetails;
import com.bridgelabz.addressbookjwtapp.entity.AddressBookData;
import com.bridgelabz.addressbookjwtapp.repository.IAddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IAddressBookRepository iAddressBookRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AddressBookData user = iAddressBookRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not Found!!");
        }
        else{
            return new CustomUserDetails(user);
        }

    }
}
