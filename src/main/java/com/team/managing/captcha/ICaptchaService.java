package com.team.managing.captcha;

public interface ICaptchaService {
    Boolean processResponse(final String response);
}