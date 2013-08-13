package it.sevenbits.security;

import it.sevenbits.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 8/6/13
 * Time: 1:26 PM
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource(name = "userDao")
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(final String email) {
        return this.userDao.findUserByEmail(email);
    }

}
