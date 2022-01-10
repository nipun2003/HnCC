package com.nipunapps.hncc.feature_hncc.domain.model

import com.nipunapps.hncc.R

data class HeadBodyModel(
    val head: String,
    val body: Int? = null,
    val more: Boolean = false,
    val message: String = "",
)


val demoInfos =
    HeadBodyModel(
        head = "About HnCC",
        body = R.string.about_hncc,
        more = true
    )


data class ArchitectInfo(
    val head: String,
    val body: Int
)

data class Architect(
    val head: String,
    val body: Int,
    val infos: List<ArchitectInfo>
)

val demoDesignInfos = listOf(
    ArchitectInfo(
        head = "Websites and Platform",
        body = R.string.website_and_platform
    ),
    ArchitectInfo(
        head = "Mobile Applications",
        body = R.string.mobile_application
    ),
    ArchitectInfo(
        head = "Branding",
        body = R.string.branding
    ),
    ArchitectInfo(
        head = "Design Concept",
        body = R.string.design_concept
    )
)

val demoDevelopInfos = listOf(
    ArchitectInfo(
        head = "Website",
        body = R.string.develop_website
    ),
    ArchitectInfo(
        head = "Native/Hybrid Apps",
        body = R.string.develop_native_hybrid
    ),
    ArchitectInfo(
        head = "A.I./M.L.",
        body = R.string.develop_ai_ml
    ),
    ArchitectInfo(
        head = "Open Source",
        body = R.string.develop_open_source
    )
)

val demoCodeInfos = listOf(
    ArchitectInfo(
        head = "Competitve Coding",
        body = R.string.competitive_coding
    ),
    ArchitectInfo(
        head = "DSA",
        body = R.string.code_dsa
    )
)

val demoArchitects = listOf(
    Architect(
        head = "Design.",
        body = R.string.design,
        infos = demoDesignInfos
    ),
    Architect(
        head = "Develop.",
        body = R.string.develop,
        infos = demoDevelopInfos
    ),
    Architect(
        head = "Code.",
        body = R.string.code,
        infos = demoCodeInfos
    )
)
