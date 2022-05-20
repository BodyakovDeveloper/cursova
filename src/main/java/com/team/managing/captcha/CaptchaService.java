package com.team.managing.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestOperations;

import java.net.URI;

import static com.team.managing.constant.ConstantClass.ANNOTATION_SPRING_PROPERTY_SOURCE;
import static com.team.managing.constant.ConstantClass.CAPTCHA_URL;
import static com.team.managing.constant.ConstantClass.RECAPTCHA_KEY_SECRET;

@PropertySource(ANNOTATION_SPRING_PROPERTY_SOURCE)
public class CaptchaService implements ICaptchaService {

    @Autowired
    private RestOperations restTemplate;

    @Autowired
    private Environment environment;

    @Override
    public Boolean processResponse(String response) {

        URI verifyUri = URI.create(String.format(CAPTCHA_URL, environment.getProperty(RECAPTCHA_KEY_SECRET), response));
        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        if (googleResponse != null) {
            return googleResponse.isSuccess();
        } else {
            return false;
        }
    }
}