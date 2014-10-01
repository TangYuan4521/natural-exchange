package it.sevenbits.services.authentication;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.hibernate.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    @Autowired
    static UserDao userDao;

    public static UserEntity getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity) {
            return ((UserEntity) principal);
        } else return null;
    }

    public static UserDetails getUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity) {
            return (UserDetails) principal;
        } else return null;
    }

    public static Long getUserId() {
        UserEntity currentUser = getUser();
        if (currentUser != null) {
            return currentUser.getId();
        }
        return (long) 0;
    }
}
