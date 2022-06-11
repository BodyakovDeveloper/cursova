package com.team.managing.captcha;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.net.URI;

import static com.team.managing.constant.ConstantClass.CAPTCHA_URL;

@Service
@RequiredArgsConstructor
public class CaptchaService implements ICaptchaService {

    private final RestOperations restTemplate;

    @Value("${google.recaptcha.key.secret}")
    private String recaptchaKeySecret;

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