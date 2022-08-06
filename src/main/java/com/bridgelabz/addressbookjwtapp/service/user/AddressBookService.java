package com.bridgelabz.addressbookjwtapp.service.user;
import com.bridgelabz.addressbookjwtapp.entity.AddressBookDTO;
import com.bridgelabz.addressbookjwtapp.entity.AddressBookData;
import com.bridgelabz.addressbookjwtapp.exception.AddressBookException;
import com.bridgelabz.addressbookjwtapp.repository.IAddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class AddressBookService implements IAddressBookService {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private IAddressBookRepository iAddressBookRepository;

    @Override
    public AddressBookData addUser(AddressBookDTO addressBookDTO) {
        AddressBookData user = modelMapper.map(addressBookDTO, AddressBookData.class);
        return iAddressBookRepository.save(user);
    }

    @Override
    public List<AddressBookData> getUsers() {
        return null;
    }

    @Override
    public AddressBookData createAddressBookData(AddressBookDTO addressBookDTO) {
        AddressBookData addressBookData = modelMapper.map(addressBookDTO, AddressBookData.class);
        log.debug("Emp Data: " +addressBookData.toString());
        iAddressBookRepository.save(addressBookData);
        return addressBookData;
    }

    @Override
    public List<AddressBookData> getAddressBookData() {
        return iAddressBookRepository.findAll();
    }

    @Override
    public AddressBookData getAddressBookDataById(long personId) {
        return iAddressBookRepository.findById(personId)
                .orElseThrow(() -> new AddressBookException("Person not found In the List"));
    }

    @Override
    public List<AddressBookData> getContactsByCity(String city) {
        return iAddressBookRepository.findContactByCity((city));
    }

    @Override
    public List<AddressBookData> getContactsByState(String state) {
        return iAddressBookRepository.findContactByState((state));
    }
    @Override
    public AddressBookData updateAddressBookData(long personId, AddressBookDTO addressBookDTO) {
        AddressBookData addressBookData = this.getAddressBookDataById(personId);
        modelMapper.map(addressBookDTO, addressBookData);
        iAddressBookRepository.save(addressBookData);
        return addressBookData;
    }

    @Override
    public void deleteAddressBookData(long personId) {
        AddressBookData addressBookData = this.getAddressBookDataById(personId);
        iAddressBookRepository.delete(addressBookData);
    }

    @Override
    public List<AddressBookData> orderContactsByCity() {
        return iAddressBookRepository.orderContactsByCity();

    }

    @Override
    public List<AddressBookData> orderContactsByState() {
        return iAddressBookRepository.orderContactsByState();
    }


}
