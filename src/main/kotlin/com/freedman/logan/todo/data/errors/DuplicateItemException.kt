package com.freedman.logan.todo.data.errors

class DuplicateItemException(
    message: String,
    cause: Throwable? = null
): Exception(message, cause)