package com.freedman.logan.todo

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
@ResponseBody
class GlobalControllerAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleConflict(response: HttpServletResponse, e: Exception): ErrorResponse {
        return ErrorResponse(e::class.java,e.message, e.stackTraceToString())
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler(ArithmeticException::class)
    fun handleTeapot(e: Exception): ErrorResponse {
        return ErrorResponse(e::class.java,"This is the teapot func", e.stackTraceToString())
    }

}

data class ErrorResponse(
    val className: Class<out Exception>,
    val message: String?,
    val traceback: String
)