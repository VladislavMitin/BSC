package ru.vladislavmitin.bsc.data

import com.google.gson.annotations.SerializedName
import ru.vladislavmitin.bsc.domain.Event
import ru.vladislavmitin.bsc.domain.Guest
import ru.vladislavmitin.bsc.domain.Location

class EventsDto(
    @SerializedName("events") var events: List<EventDto>
)

class EventDto(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("image") var image: String,
    @SerializedName("location") var location: LocationDto,
    @SerializedName("invited") var invited: GuestDto,
    @SerializedName("guests") var guests: List<GuestDto>?
) {
    fun toModel(): Event {
        return Event(
            id,
            name,
            image,
            location.toModel(),
            invited.toModel(),
            guests?.map { it.toModel() }
        )
    }
}

class GuestDto(
    @SerializedName("phone") var phone: String,
    @SerializedName("name") var name: String
) {
    fun toModel() = Guest(phone, name)
}

class LocationDto(
    @SerializedName("address") var address: String,
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double
) {
    fun toModel() = Location(address, latitude, longitude)
}
