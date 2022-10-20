package com.kode.iconfinder.util

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Singleton

private const val TAG = "SaveImage"
@Singleton
object SaveImage {
    var resolver: ContentResolver? =null
     suspend fun saveImageToGallery(bitmap: Bitmap) {
        val fos: OutputStream
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Icon" + ".jpg")
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator.toString() + "IconFinder"
                )
                val imageUri: Uri? =
                    resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = Objects.requireNonNull(imageUri)?.let { resolver?.openOutputStream(it) }!!
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                Objects.requireNonNull(fos)

            }
            else{

                val imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM
                ).toString() + File.separator + "IconFinder"

                val file = File(imagesDir)

                if (!file.exists()) {
                    file.mkdir()
                }

                val image = File(imagesDir, "FinderSaved" + ".png")
                fos = FileOutputStream(image)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                Objects.requireNonNull(fos)
            }
        } catch (e: Exception) {
            Log.d(TAG, "saveImageToGallery: $e")
        }


    }
}