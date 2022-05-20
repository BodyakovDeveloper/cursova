package com.team.managing.captcha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static com.team.managing.constant.ConstantClass.ERROR_CAPTCHA_INPUT_RESPONSE;
import static com.team.managing.constant.ConstantClass.ERROR_CAPTCHA_INVALID_SECRET;
import static com.team.managing.constant.ConstantClass.ERROR_CAPTCHA_MISSING_RESPONSE;
import static com.team.managing.constant.ConstantClass.ERROR_CAPTCHA_MISSING_SECRET;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "success",
        "challenge_ts",
        "hostname",
        "error-codes"
})
public class GoogleResponse {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("error-codes")
    private ErrorCode[] errorCodes;

    @JsonIgnore
    public boolean hasClientError() {
        ErrorCode[] errors = getErrorCodes();
        if(errors == null) {
            return false;
        }
        for(ErrorCode error : errors) {
            switch(error) {
                case INVALID_RESPONSE:
                case MISSING_RESPONSE:
                    return true;
                default:
            }
        }
        return false;
    }

    enum ErrorCode {
        MISSING_SECRET, INVALID_SECRET,
        MISSING_RESPONSE, INVALID_RESPONSE;

        private static Map<String, ErrorCode> errorsMap = new HashMap<>(4);

        static {
            errorsMap.put(ERROR_CAPTCHA_MISSING_SECRET, MISSING_SECRET);
            errorsMap.put(ERROR_CAPTCHA_INVALID_SECRET, INVALID_SECRET);
            errorsMap.put(ERROR_CAPTCHA_MISSING_RESPONSE, MISSING_RESPONSE);
            errorsMap.put(ERROR_CAPTCHA_INPUT_RESPONSE, INVALID_RESPONSE);
        }

        @JsonCreator
        public static ErrorCode forValue(String value) {
            return errorsMap.get(value.toLowerCase());
        }
    }
}
