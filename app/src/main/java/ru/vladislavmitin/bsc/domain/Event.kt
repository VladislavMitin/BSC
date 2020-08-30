package ru.vladislavmitin.bsc.domain

data class Event(val id: Int, val name: String, val image: String, val location: Location, val invited: Guest, val guests: List<Guest>?)
