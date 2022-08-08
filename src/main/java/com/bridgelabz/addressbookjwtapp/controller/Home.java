package com.bridgelabz.addressbookjwtapp.controller;


import com.bridgelabz.addressbookjwtapp.dto.ResponseDTO;
import com.bridgelabz.addressbookjwtapp.dto.UserNameOtpDTO;
import com.bridgelabz.addressbookjwtapp.entity.AddressBookDTO;
import com.bridgelabz.addressbookjwtapp.entity.AddressBookData;
import com.bridgelabz.addressbookjwtapp.service.user.IAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class Home {

    final static String SUCCESS = "Entered Otp is valid, and Registration was successful.";
    final static String FAIL = "Entered OTP was not valid! , Registration failed!, please try again";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAddressBookService userService;



    //this api is accessible to authenticated user only
    @RequestMapping("/welcome")
    public String welcome(){
        String text = "This is a private page!!! ";
        text+="Only authorized user can access this page!!!";
        return text;
    }


    //registration api permitted to be accessed by all the users
    @PostMapping("/register")
    public ResponseEntity<AddressBookData> addUser(@Valid @RequestBody AddressBookDTO addressBookDTO){
        addressBookDTO.setPassword(passwordEncoder.encode(addressBookDTO.getPassword()));
        AddressBookData user = userService.addUser(addressBookDTO);
        return ResponseEntity.ok(user);
    }

    //All the below APIs Should be accessible to authenticated user only

    @GetMapping(value = {"/get"})
    public ResponseEntity<ResponseDTO> getAddressBookData() {
        List<AddressBookData> addressBookList = null;
        addressBookList = userService.getAddressBookData();
        ResponseDTO responseDTO = new ResponseDTO("Get call is Successful",addressBookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/get/{personId}"})
    public ResponseEntity<ResponseDTO> getAddressBookDataById(@PathVariable long personId) {
        AddressBookData addressBookData= userService.getAddressBookDataById(personId);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Successful",addressBookData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @PutMapping(value = {"/edit/{personId}"})
    public ResponseEntity<ResponseDTO> editAddressBookData(@PathVariable long personId,
                                                           @Valid @RequestBody AddressBookDTO addressBookDTO) {
        addressBookDTO.setPassword(passwordEncoder.encode(addressBookDTO.getPassword()));
        AddressBookData addressBookData=userService.updateAddressBookData(personId,addressBookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Data updated Successfully!!!",addressBookData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/remove/{personId}"})
    public ResponseEntity<ResponseDTO> removeAddressBookData(@PathVariable int personId) {
        userService.deleteAddressBookData(personId);
        ResponseDTO responseDTO = new ResponseDTO("Data Deleted Successfully!!!",
                "ID number: "+personId + " DELETED!!!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ResponseDTO> getContactsByCity(@PathVariable("city") String city){
        List<AddressBookData> addressBookList = null;
        addressBookList = userService.getContactsByCity(city);
        ResponseDTO responseDTO = new ResponseDTO("Get Call For city Successful",addressBookList);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<ResponseDTO> getContactsByState(@PathVariable("state") String state){
        List<AddressBookData> addressBookList = null;
        addressBookList = userService.getContactsByState(state);
        ResponseDTO responseDTO = new ResponseDTO("Get Call For state Successful",addressBookList);
        return new ResponseEntity<ResponseDTO>(responseDTO,HttpStatus.OK);
    }

    @GetMapping(value = {"/city"})
    public ResponseEntity<ResponseDTO> orderContactsByCity() {
        List<AddressBookData> addressBookList = userService.orderContactsByCity();
        ResponseDTO responseDTO = new ResponseDTO("Contact details sorted by City!!!", addressBookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/state"})
    public ResponseEntity<ResponseDTO> orderContactsByState() {
        List<AddressBookData> addressBookList = userService.orderContactsByState();
        ResponseDTO responseDTO = new ResponseDTO("Contact details sorted by State!!!", addressBookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping({"/verifyotp"})
    public String verifyOtp(@Valid @RequestBody UserNameOtpDTO userNameOtpDTO) {
        String username = userNameOtpDTO.getUsername();
        String otp = userNameOtpDTO.getOtp();
        Boolean isVerifyOtp = userService.verifyOtp(username, otp);
        if (!isVerifyOtp)
            return FAIL;
        return SUCCESS;
    }


}
