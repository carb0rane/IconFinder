package com.kode.iconfinder.IconFinder

data class listAllCategories(
    val categories: MutableList<Category>,
    val total_count: Int
)

data class Category(
    val identifier: String,
    val name: String
)

data class iconSetsFromCategory(
    val iconsets: MutableList<Iconset>,
    val total_count: Int
)

data class Iconset(
    val are_all_icons_glyph: Boolean,
    val author: Author,
    val categories: List<Category>,
    val icons_count: Int,
    val iconset_id: Int,
    val identifier: String,
    val is_premium: Boolean,
    val is_purchased: Boolean,
    val name: String,
    val prices: List<Price>,
    val published_at: String,
    val styles: List<Style>,
    val type: String
)

data class Author(
    val company: String,
    val iconsets_count: Int,
    val is_designer: Boolean,
    val name: String,
    val user_id: Int,
    val username: String
)

data class Price(
    val currency: String,
    val license: License,
    val price: Double
)

data class Style(
    val identifier: String,
    val name: String
)

data class License(
    val license_id: Int,
    val name: String,
    val scope: String,
    val url: String
)

data class iconsList(
    var icons: MutableList<Icon>,
    var total_count: Int
)

data class Icon(
    val categories: List<Category>,
    val containers: List<Container>,
    val icon_id: Int,
    val is_icon_glyph: Boolean,
    val is_premium: Boolean,
    val published_at: String,
    val raster_sizes: List<RasterSize>,
    val styles: List<Style>,
    val tags: List<String>,
    val type: String,
    val vector_sizes: List<VectorSize>
)


data class Container(
    val download_url: String,
    val format: String
)

data class RasterSize(
    val formats: List<Format>,
    val size: Int,
    val size_height: Int,
    val size_width: Int
)

data class VectorSize(
    val formats: List<FormatX>,
    val size: Int,
    val size_height: Int,
    val size_width: Int,
    val target_sizes: List<List<Int>>
)

data class Format(
    val download_url: String,
    val format: String,
    val preview_url: String
)

data class FormatX(
    val download_url: String,
    val format: String
)