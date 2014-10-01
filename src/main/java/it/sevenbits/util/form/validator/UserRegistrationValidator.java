package it.sevenbits.util.form.validator;

import it.sevenbits.dao.UserDao;
import it.sevenbits.util.form.UserRegistrationForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 7/29/13
 * Time: 3:21 PM
 *
 */
@Component
public class UserRegistrationValidator implements Validator {

    private static final int MAX_NAME_SIZE = 20;

    @Resource(name = "userDao")
    private UserDao userDao;


    @Override
    public boolean supports(final Class<?> clazz) {
        return UserRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        UserRegistrationForm userRegistrationForm = (it.sevenbits.util.form.UserRegistrationForm) target;
        String password = userRegistrationForm.getPassword();
        String email = userRegistrationForm.getEmail();
        String firstName = userRegistrationForm.getFirstName();
        String lastName = userRegistrationForm.getLastName();
        if (!EmailValidator.getInstance().isValid(((UserRegistrationForm) target).getEmail())) {
            errors.rejectValue("email", "email.not.correct", "Введите корректный e-mail адрес.");
        }
        if (firstName.length() > 20) {
            errors.rejectValue("firstName", "firstName.too.long", "Допускается не более 20 символов.");
        }
        if (lastName.length() > 20) {
            errors.rejectValue("lastName", "lastName.too.long", "Допускается не более 20 символов.");
        }
        if (this.userDao.isExistUserWithEmail(email)) {
            errors.rejectValue("email", "email.user.exists", "Пользователь с таким e-mail существует.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Пароль не может быть пустым.");
    }
}




