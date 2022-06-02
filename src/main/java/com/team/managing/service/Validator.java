package com.team.managing.service;

import com.team.managing.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class Validator {

    private static final String REGEX_FOR_NUMBERS = ".*\\d+.*";
    private static final String REGEX_FOR_CHECK_EMAIL = "^(.+)@(.+)$";
    private static final String REGEX_FOR_CHECK_BCRYPT = "^\\$2a.*";

    private static final List<String> NAME_OF_USER_FIELDS = List.of("login", "firstName", "lastName", "password", "id", "role", "email");
    /**
     * contains the names of invalid fields of the last validation
     */
    private StringBuilder wrongFieldName = new StringBuilder();

    /**
     * The method takes each field and checks for validity
     * After every call, all information in field wrongFieldName canceled
     *
     * @param user
     * @return are the fields valid, or not
     */
    public boolean isUserValid(UserEntity user) {
        if (user == null) {
            log.warn("user cant be null");
            throw new NullPointerException("User can not be null");
        }
        boolean isValid = true;
        nullifyWrongNameOfField();

        if (user.getLogin() == null
                || user.getLogin().length() > 32
                || user.getLogin().length() < 6) {

            wrongFieldName.append(NAME_OF_USER_FIELDS.get(0))
                    .append(',');
            isValid = false;

            log.warn("wrong input login");
        }

        if (!user.getFirstName().isEmpty() && user.getFirstName().matches(REGEX_FOR_NUMBERS)) {
            wrongFieldName.append(NAME_OF_USER_FIELDS.get(1)).append(',');
            isValid = false;

            log.warn("wrong input first name");
        }

        if (!user.getLastName().isEmpty() && user.getLastName().matches(REGEX_FOR_NUMBERS)) {
            wrongFieldName.append(NAME_OF_USER_FIELDS.get(2)).append(',');
            isValid = false;

            log.warn("wrong input last name");
        }

        if (!user.getPassword().matches(REGEX_FOR_CHECK_BCRYPT)
                && (user.getPassword().length() > 32 || user.getPassword().length() < 6)) {
            wrongFieldName.append(NAME_OF_USER_FIELDS.get(3)).append(',');
            isValid = false;

            log.warn("wrong input password");
        }

        if (!user.getEmail().isEmpty() && !user.getEmail().matches(REGEX_FOR_CHECK_EMAIL)) {
            wrongFieldName.append(NAME_OF_USER_FIELDS.get(6)).append(',');
            isValid = false;

            log.warn("wrong input email");
        }

        if (wrongFieldName.length() > 0) {
            wrongFieldName.replace(wrongFieldName.length() - 1, wrongFieldName.length(), "");
        }


        return isValid;
    }

    /**
     * @return a list of invalid fields in a string with separator ','
     */
    public String getWrongFieldName() {
        return wrongFieldName.toString();
    }

    private void nullifyWrongNameOfField() {
        wrongFieldName = new StringBuilder();
    }
}