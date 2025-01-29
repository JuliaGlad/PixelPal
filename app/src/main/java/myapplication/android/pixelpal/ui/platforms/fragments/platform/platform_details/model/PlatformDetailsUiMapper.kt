package myapplication.android.pixelpal.ui.platforms.fragments.platform.platform_details.model

import myapplication.android.pixelpal.domain.model.platform.PlatformDomainDetails

fun PlatformDomainDetails.toUi() =
    PlatformDetailsUi(
        endYear, description
    )