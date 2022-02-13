package com.area.uti;

import com.area.web.rest.vm.ManagedUserVM;
import org.apache.commons.lang3.StringUtils;

public class Utility {

    public static boolean isPasswordLengthInvalid(String password) {
        return (
            StringUtils.isEmpty(password) ||
            password.length() < ManagedUserVM.PASSWORD_MIN_LENGTH ||
            password.length() > ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }
}
