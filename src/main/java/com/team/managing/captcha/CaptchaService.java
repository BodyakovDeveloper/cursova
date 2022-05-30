package com.team.managing.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.net.URI;

import static com.team.managing.constant.ConstantClass.CAPTCHA_URL;
import static com.team.managing.constant.ConstantClass.PROPERTY_SOURCE;

@PropertySource(PROPERTY_SOURCE)
@Service
public class CaptchaService implements ICaptchaService {

    private final RestOperations restTemplate;

    @Value("${google.recaptcha.key.secret}")
    private String recaptchaKeySecret;

    @Autowired
    public CaptchaService(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Boolean processResponse(String response) {

        URI verifyUri = URI.create(String.format(CAPTCHA_URL, recaptchaKeySecret, response));
        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        if (googleResponse != null) {
            return googleResponse.isSuccess();
        } else {
            return false;
        }
    }
}