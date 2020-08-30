package ru.vladislavmitin.bsc.data

interface IContactService {
    suspend fun getPhoto(phone: String): String?
}