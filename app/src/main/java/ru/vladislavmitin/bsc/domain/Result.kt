package ru.vladislavmitin.bsc.domain

class Result<T>(val status: Status, val data: T? = null, val error: String? = null)

enum class Status {
    SUCCESS,
    ERROR
}
