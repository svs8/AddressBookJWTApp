package com.bridgelabz.addressbookjwtapp.repository;


import com.bridgelabz.addressbookjwtapp.entity.UserNameOtpData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface IUserNameOtpRespository extends JpaRepository<UserNameOtpData, String> {

    public UserNameOtpData findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_name_otp_data WHERE username= :username", nativeQuery = true)
    void deleteEntry(String username);
}