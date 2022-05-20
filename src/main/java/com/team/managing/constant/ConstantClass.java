package com.team.managing.constant;

public class ConstantClass {

    public static String DATABASE_CONNECTION_DRIVER = "hibernate.driver.class.name";
    public static String DATABASE_CONNECTION_URL = "hibernate.connection.url";
    public static String DATABASE_CONNECTION_USERNAME = "hibernate.connection.username";
    public static String DATABASE_CONNECTION_PASSWORD = "hibernate.connection.password";

    public static String HIBERNATE_DIALECT = "hibernate.dialect";
    public static String HIBERNATE_DIALECT_H2 = "org.hibernate.dialect.H2Dialect";

    public static String VIEW_RESOLVER_PREFIX = "/WEB-INF/pages";
    public static String VIEW_RESOLVER_SUFFIX = ".jsp";

    public static String RECAPTCHA_KEY_SITE = "google.recaptcha.key.site";
    public static String RECAPTCHA_KEY_SECRET = "google.recaptcha.key.secret";

    public static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    public static final String ANNOTATION_SPRING_PROPERTY_SOURCE = "classpath:application.properties";

    public final static String PACKAGE_TO_SCAN = "com.team.managing";
    public final static String PROPERTIES_FILE = "application.properties";
    public final static String ROOT_USER_PAGES = "/user/**";
    public final static String ROOT_ADMIN_PAGES = "/admin/**";
    public final static String ROOT_LOGIN_PAGE = "/login";

    public final static String ROLE_USER = "USER";
    public final static String ROLE_ADMIN = "ADMIN";

    public final static String AUTHENTICATION_ROLE_USER = "[ROLE_USER]";
    public final static String AUTHENTICATION_ROLE_ADMIN = "[ROLE_ADMIN]";

    public final static String AUTHENTICATION_ROLE_SUFFIX = "ROLE_";

    public final static String RESOURCE_HANDLERS_ROOT = "/WEB-INF/**";
    public final static String RESOURCE_HANDLERS_CLASSPATH = "classpath:/WEB-INF/";

    public final static String USER_IS_ALREADY_EXIST_MESSAGE = "User is already exist";
    public final static String CAPTCHA_RESPONSE_ERROR = "Captcha was failed";

    public static final String PROPERTY_SPRING_SET_DATE_FORMAT = "dd.MM.yyyy";
    public static final String PROPERTY_SPRING_SET_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final String PROPERTY_SPRING_REGISTER_DATE_FORMAT = "yyyy.MM.dd";
    //SPRING CONFIG

    public static final String ERROR_CAPTCHA_MISSING_SECRET = "missing-input-secret";
    public static final String ERROR_CAPTCHA_INVALID_SECRET = "invalid-input-secret";
    public static final String ERROR_CAPTCHA_MISSING_RESPONSE = "missing-input-response";
    public static final String ERROR_CAPTCHA_INPUT_RESPONSE = "invalid-input-response";
}