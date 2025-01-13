package myapplication.android.pixelpal.ui.platforms.fragments.store.store_details.model

import myapplication.android.pixelpal.domain.model.stores.StoreDetailsDomain

fun StoreDetailsDomain.toUi() =
    StoreDetailsUi(description)