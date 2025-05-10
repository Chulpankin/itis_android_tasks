package com.itis.bookclub.util

sealed class AppException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {
    class BadRequest(
        message: String? = null,
        cause: Throwable? = null
    ) : AppException(message, cause)

    class Unauthorized(
        message: String? = null,
        cause: Throwable? = null
    ) : AppException(message, cause)

    class NotFound(
        message: String? = null,
        cause: Throwable? = null
    ) : AppException(message, cause)

    class InternalServerError(
        message: String? = null,
        cause: Throwable? = null
    ) : AppException(message, cause)

    class UnexpectedError(
        message: String? = null,
        cause: Throwable? = null
    ) : AppException(message, cause)

    class EmptyResponse(
        message: String? = null,
        cause: Throwable? = null
    ) : AppException(message, cause)
}