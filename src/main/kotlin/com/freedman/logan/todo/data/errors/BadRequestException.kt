package com.freedman.logan.todo.data.errors

class BadRequestException(
    message: String,
    cause: Throwable?
): Throwable(message, cause)