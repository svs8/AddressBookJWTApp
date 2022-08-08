package com.bridgelabz.addressbookjwtapp.service.user;

import com.bridgelabz.addressbookjwtapp.entity.AddressBookDTO;
import com.bridgelabz.addressbookjwtapp.entity.AddressBookData;

import java.util.List;

public interface IAddressBookService {
    public AddressBookData addUser(AddressBookDTO user);
    public List<AddressBookData> getUsers();

    List<AddressBookData> getAddressBookData();

    AddressBookData getAddressBookDataById(long personId);

    List<AddressBookData> getContactsByCity(String city);

    List<AddressBookData> getContactsByState(String state);

    AddressBookData updateAddressBookData(long personId, AddressBookDTO addressBookDTO);

    void deleteAddressBookData(long personId);

    List<AddressBookData> orderContactsByCity();

    List<AddressBookData> orderContactsByState();

    Boolean verifyOtp(String username, String otp);

    Boolean isVerified(String username);
}
