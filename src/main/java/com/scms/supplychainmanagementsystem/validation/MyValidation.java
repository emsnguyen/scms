package com.scms.supplychainmanagementsystem.validation;

import com.scms.supplychainmanagementsystem.exceptions.AppException;
import org.springframework.stereotype.Component;

@Component
public class MyValidation {

        private  final String PHONE_VALID = "^\\d{10}\\d*$";
        private  final String EMAIL_VALID = "^[A-Za-z0-9.+-_%]+@[A-Za-z.-]+\\.[A-Za-z]{2,4}$";


        public  String checkInputPhone(String phone,int index) {
            if (phone.matches(PHONE_VALID)) {
                return phone;
            } else {
                throw new AppException("-- Sai định sạng số điện thoại tại hàng "+index+" --");
            }
        }

        public  String checkInputEmail(String email,int index) {
            if (email.matches(EMAIL_VALID)) {
                return email;
            } else {
                throw new AppException("-- Sai định dạng Email tại hàng "+index+" --");
            }
        }

}

