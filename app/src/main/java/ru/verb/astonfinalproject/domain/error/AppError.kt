package ru.verb.astonfinalproject.domain.error

sealed class AppError(val code: String): RuntimeException()

class ApiError(code: String): AppError(code)
object NetworkError: AppError("network error")
object DataBaseError: AppError("database error")
object UnknownError: AppError("unknown error")
