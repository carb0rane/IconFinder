package com.kode.iconfinder.IconFinder.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.kode.iconfinder.IconFinder.*
import com.kode.iconfinder.util.SaveImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit

private const val TAG = "IconFinderViewModel"

class IconFinderViewModel(application: Application) : AndroidViewModel(application) {

    val context = application.applicationContext
    var after = ""
    lateinit var retrofit: Retrofit
    lateinit var retrofitApi: Api
    lateinit var apiKey: String
    lateinit var accept: String
    var identifier = "arrow"
    var iconset_id = 1761
    var selectedCategory = MutableLiveData<String>()
    var category = listAllCategories(mutableListOf<Category>(), 1)
    var categoryList = MutableLiveData<listAllCategories>()
    var iconset = iconSetsFromCategory(mutableListOf(), 1)
    var liveIconSet = MutableLiveData<iconSetsFromCategory?>()
    var icon = iconsList(mutableListOf(), 1)
    var liveIcon = MutableLiveData<iconsList?>()
    var searchResult = iconsList(mutableListOf(), 0)
    var liveSearchResult = MutableLiveData<iconsList>()

    fun setRetrofitApi() {
        try {
            retrofitApi = retrofit.create(Api::class.java)


        } catch (e: Exception) {
            Log.d(TAG, "getHomeList: $e")
        }
    }

    fun getHomeList() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response: Response<listAllCategories>? = try {
                retrofitApi.getHomeList(apiKey, accept, 25, after)
            } catch (e: Exception) {
                Log.d(TAG, "getHomeList api raised :  : $e")
                return@launch
            }
            if (response != null) {
                if (response.isSuccessful && response.body() != null) {
                    val itemsReceived = response!!.body()?.categories?.lastIndex
                    categoryList.postValue(response.body()!!)
                    after = response.body()!!.categories.get(itemsReceived!!).identifier
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "inside retro response: $e ")
        }

    }

    fun getIconSetList() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response: Response<iconSetsFromCategory>? = try {
                retrofitApi.getIconSetFromCategory(identifier, apiKey, accept, 25, after)
            } catch (e: Exception) {
                Log.d(TAG, "retrofit : $e")
                return@launch
            }
            if (response != null) {
                if (response.isSuccessful && response.body() != null) {
                    val itemsReceived = if (response.body()!!.total_count - 1 > 24) {
                        24
                    } else response.body()!!.total_count - 1

                    liveIconSet.postValue(response!!.body())
                    after = response.body()!!.iconsets.get(itemsReceived).identifier
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "getIconSetList: $e")
        }

    }

    fun getIconFromIconSet() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response: Response<iconsList>? = try {
                retrofitApi.getIconFromIconSet(iconset_id, apiKey, accept)
            } catch (e: Exception) {
                Log.d(TAG, "retrofit : $e")
                return@launch
            }
            if (response != null) {
                if (response.isSuccessful && response.body() != null) {
                    liveIcon.postValue(response!!.body())
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "getIconFromIconSet: $e")
        }

    }

    fun download(url: String) = CoroutineScope(Dispatchers.Default).launch {
        try {
            val headers = LazyHeaders.Builder()
                .addHeader("Authorization", apiKey)
                .addHeader("Accept", accept)
                .build()
            val glideUrl = GlideUrl(
                url,
                headers
            )

            val bitmap = Glide.with(context)
                .asBitmap()
                .load(glideUrl)
                .submit()
                .get()
            SaveImage.resolver = context.contentResolver
            SaveImage.saveImageToGallery(bitmap)
        } catch (e: Exception) {
            Log.d(TAG, "download: $e")
        }


    }

    fun searchIcons(query: String) = CoroutineScope(Dispatchers.Default).launch {
        try {
            val response: Response<iconsList>? = try {
                retrofitApi.searchIcons(apiKey, accept, 25, query)
            } catch (e: Exception) {
                Log.d(TAG, "retrofit : $e")
                return@launch
            }
            if (response != null) {
                if (response.isSuccessful && response.body() != null) {
                    liveSearchResult.postValue(response.body()!!)
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "serchIcons: $e")
        }
    }

    suspend fun getTextQuery(flow: Flow<CharSequence?>) {
        flow
            .filter { it?.length!! >= 1 }
            .debounce(100)
            .flatMapLatest {
                return@flatMapLatest flow{
                    emit(it.toString())
                }
            }
            .collect {
                       searchIcons(it)
            }
    }
}