package com.bridgelabz.addressbookjwtapp.service.user;
import com.bridgelabz.addressbookjwtapp.entity.AddressBookDTO;
import com.bridgelabz.addressbookjwtapp.entity.AddressBookData;
import com.bridgelabz.addressbookjwtapp.entity.UserNameOtpData;
import com.bridgelabz.addressbookjwtapp.exception.AddressBookException;
import com.bridgelabz.addressbookjwtapp.repository.IAddressBookRepository;
import com.bridgelabz.addressbookjwtapp.repository.IUserNameOtpRespository;
import com.bridgelabz.addressbookjwtapp.service.EmailSenderService;
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
    EmailSenderService senderService;

    @Autowired
    private IAddressBookRepository iAddressBookRepository;

    @Autowired
    IUserNameOtpRespository iUserNameService;

    @Override
    public AddressBookData addUser(AddressBookDTO addressBookDTO) {
        AddressBookData user = modelMapper.map(addressBookDTO, AddressBookData.class);
        int otps = (int) Math.floor(Math.random() * 1000000);
        String otp = String.valueOf(otps);
        UserNameOtpData userNameOtp = new UserNameOtpData(addressBookDTO.username, otp);
        iUserNameService.save(userNameOtp);
        senderService.sendEmail(user.getEmail(), "OTP for Registration", otp);
        return iAddressBookRepository.save(user);
    }

    @Override
    public List<AddressBookData> getUsers() {
        return null;
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
        int otps = (int) Math.floor(Math.random() * 1000000);
        String otp = String.valueOf(otps);
        UserNameOtpData userNameOtp = new UserNameOtpData(addressBookDTO.username, otp);
        iUserNameService.save(userNameOtp);
        senderService.sendEmail(addressBookData.getEmail(), "OTP for Updating Details.", otp);
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

    @Override
    public Boolean verifyOtp(String username, String otp) {
        UserNameOtpData serverOtp = iUserNameService.findByUsername(username);

        if (otp == null)
            return false;
        if(!(otp.equals(serverOtp.getOtp())))
            return false;
        iAddressBookRepository.changeVerified(username);
        iUserNameService.deleteEntry(username);
        return true;
    }

    @Override
    public Boolean isVerified(String username) {
        return iAddressBookRepository.isVerified(username);
    }

}
