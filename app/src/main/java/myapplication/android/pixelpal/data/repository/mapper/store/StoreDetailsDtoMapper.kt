package myapplication.android.pixelpal.data.repository.mapper.store

import myapplication.android.pixelpal.data.models.stores.StoreDetails
import myapplication.android.pixelpal.data.repository.dto.store.StoreDetailsDto

fun StoreDetails.toDto() =
    StoreDetailsDto(description)