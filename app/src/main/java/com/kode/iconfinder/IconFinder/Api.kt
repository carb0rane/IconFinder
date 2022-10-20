package com.kode.iconfinder.IconFinder


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("/v4/categories")
  suspend  fun getHomeList(
        @Header("Authorization") api_key: String,
        @Header("accept") accept: String,
        @Query("count") count: Int,
        @Query("after") after: String
    ): Response<listAllCategories>

    @GET("/v4/categories/{identifier}/iconsets")
    suspend fun getIconSetFromCategory(
        @Path("identifier") identifier: String,
        @Header("Authorization") api_key: String,
        @Header("accept") accept: String,
        @Query("count") count: Int,
        @Query("after") after: String
    ):Response<iconSetsFromCategory>

    @GET("/v4/iconsets/{iconset_id}/icons")
    suspend fun getIconFromIconSet(
        @Path("iconset_id") identifier: Int,
        @Header("Authorization") api_key: String,
        @Header("accept") accept: String
    ):Response<iconsList>

    @GET("/v4/icons/search")
    suspend fun searchIcons(
        @Header("Authorization") api_key: String,
        @Header("accept") accept: String,
        @Query("count") count: Int,
        @Query("query") query: String
    ):Response<iconsList>
}