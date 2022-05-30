package com.team.managing.constant;

public class ConstantClass {

    public static String HIBERNATE_DIALECT = "hibernate.dialect";

    public static String VIEW_RESOLVER_PREFIX = "/WEB-INF/pages";
    public static String VIEW_RESOLVER_SUFFIX = ".jsp";

    public static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    public static final String PROPERTY_SOURCE = "classpath:application.properties";

    public final static String PACKAGE_TO_SCAN = "com.team.managing";
    public final static String ROOT_USER_PAGES = "/user/**";
    public final static String ROOT_ADMIN_PAGES = "/admin/**";
    public final static String ROOT_SIGNUP_PAGE = "/signup";
    public final static String ROOT_LOGIN_PAGE = "/login";
    public final static String ROOT_REDIRECT_PAGE = "/redirect";

    public final static String ROLE_USER = "USER";
    public final static String ROLE_ADMIN = "ADMIN";

    public final static String AUTHENTICATION_ROLE_USER = "[ROLE_USER]";
    public final static String AUTHENTICATION_ROLE_ADMIN = "[ROLE_ADMIN]";

    public final static String AUTHENTICATION_ROLE_SUFFIX = "ROLE_";

    public final static String RESOURCE_HANDLERS_ROOT = "/WEB-INF/**";
    public final static String RESOURCE_HANDLERS_CLASSPATH = "classpath:/WEB-INF/";

    public final static String USER_IS_ALREADY_EXIST_MESSAGE = "User is already exist";
    public final static String CAPTCHA_RESPONSE_ERROR = "Captcha was failed";
}