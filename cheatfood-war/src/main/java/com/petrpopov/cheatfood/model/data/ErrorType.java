package com.petrpopov.cheatfood.model.data;

/**
 * User: petrpopov
 * Date: 18.07.13
 * Time: 13:41
 */
public enum ErrorType {
    access_denied,
    unknown_location,
    already_voted,
    already_rated,
    no_such_user,
    no_password_data,
    wrong_password,
    login_error,
    password_mismatch,
    wrong_token,
    token_invalid,
    merge_users,
    user_already_exists,
    email_is_empty,
    no_user_with_such_email,
    overpriced,
    comment_is_empty,
    too_early_comment,
    unknown_comment,
    vote_for_own_comment,
    other
}
