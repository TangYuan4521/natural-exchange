package it.sevenbits.web.util.form.validator.user;

import it.sevenbits.services.parsers.StringParser;
import it.sevenbits.web.util.FileValidatorConstants;
import it.sevenbits.web.util.form.user.EditingUserInfoForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class EditingUserInfoFormValidator implements Validator {
    private static final int MAX_LENGTH = 20;

    @Override
    public boolean supports(Class<?> aClass) {
        return EditingUserInfoForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditingUserInfoForm editingUserInfoForm = (EditingUserInfoForm) target;
        MultipartFile photoFile = editingUserInfoForm.getImage();

        if (!photoFile.getOriginalFilename().equals("")) {
            String contentType = StringParser.getType(photoFile.getOriginalFilename());
            if (!FileValidatorConstants.photoFileTypes.contains(contentType)) {
                errors.rejectValue("image", "image", "Неверный формат файла.");
            } else if (photoFile.getSize() > FileValidatorConstants.MAX_FILE_SIZE) {
                errors.rejectValue("image", "image", "Размер файла не должен превышать 3 мегабайт.");
            }
        }

        int firstNameLength = editingUserInfoForm.getFirstName().length();
        int lastNameLength = editingUserInfoForm.getLastName().length();
        if (firstNameLength > MAX_LENGTH) {
            errors.rejectValue("FirstName", "FirstName", "Допускается не более 20 символов.");
        } else if (firstNameLength == 0) {
            errors.rejectValue("FirstName", "FirstName", "Пустое имя недопустимо.");
        }
        if (lastNameLength > MAX_LENGTH) {
            errors.rejectValue("LastName", "LastName", "Допускается не более 20 символов.");
        } else if (lastNameLength == 0) {
            errors.rejectValue("LastName", "LastName", "Пустая фамилия недопустима.");
        }
    }
}
