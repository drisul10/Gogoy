package com.gogoy.utils

object EndPoints {
    private const val URL_ROOT = "https://api-gogoy.oemahsolution.com/"
    const val URL_AUTH_LOGIN = URL_ROOT + "auth/login"
    const val URL_AUTH_LOGOUT = URL_ROOT + "auth/logout"
    const val URL_AUTH_REGISTER = URL_ROOT + "auth/register"
    const val URL_RESET_NEW_PASSWORD = URL_ROOT + "forgot"
    const val URL_RESET_SEND_CODE = URL_ROOT + "resetpassword"
    const val URL_RESET_VERIFY_CODE = URL_ROOT + "find"
    const val URL_USER_PROFILE = URL_ROOT + "user/profile"
    const val URL_USER_EDIT_PROFILE = URL_ROOT + "user/edit"
}