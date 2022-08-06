package com.bridgelabz.addressbookjwtapp.controller;

import com.bridgelabz.addressbookjwtapp.entity.JwtResponse;
import com.bridgelabz.addressbookjwtapp.entity.AddressBookDTO;
import com.bridgelabz.addressbookjwtapp.helper.JwtUtil;
import com.bridgelabz.addressbookjwtapp.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    //login api is accessible for all the users
    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody AddressBookDTO addressBookDTO) throws Exception {
        System.out.println(addressBookDTO);
        try {
            String username = addressBookDTO.getUsername();
            String password = addressBookDTO.getPassword();

            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, password);
            //checks if the user exits
            this.authenticationManager.authenticate(user);


        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            System.out.println("User invalid");
            throw new Exception("Bad Credentials");
        }catch (BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }

        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(addressBookDTO.getUsername());
        String generatedToken = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT" + generatedToken);

        return ResponseEntity.ok(new JwtResponse(generatedToken));
    }
}
