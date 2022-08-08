package com.bridgelabz.addressbookjwtapp.repository;

import com.bridgelabz.addressbookjwtapp.entity.AddressBookData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface IAddressBookRepository extends JpaRepository<AddressBookData, Long> {
    //provide username and it will return the user of given username
    public AddressBookData findByUsername(String username);

    @Query(value="select * from address_book_data where city=:city", nativeQuery=true)
    List<AddressBookData> findContactByCity(String city);
    @Query(value="select * from address_book_data where state=:state", nativeQuery=true)
    List<AddressBookData> findContactByState(String state);

    @Query(value = "select *from address_book_data order by city", nativeQuery = true)
    List<AddressBookData> orderContactsByCity();

    @Query (value = "select *from address_book_data order by state", nativeQuery = true)
    List<AddressBookData> orderContactsByState();

    @Modifying
    @Transactional
    @Query(value = "update address_book_data set verified=true where username = :username ", nativeQuery = true)
    void changeVerified(String username);

    @Query(value = "select verified from address_book_data where username = :username", nativeQuery = true)
    Boolean isVerified(String username);
}
