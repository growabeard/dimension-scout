package com.witt.dimensionscout.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.core.content.FileProvider
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.witt.dimensionscout.R
import com.witt.dimensionscout.domain.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class CharacterSharer(
    private val context: Context,
    private val imageLoader: ImageLoader
) {
    suspend fun shareCharacter(character: Character) {
        val imageUri = fetchAndSaveImage(character)
        val shareMessage = context.getString(R.string.share_character_message, character.name)

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareMessage)
            type = if (imageUri != null) {
                putExtra(Intent.EXTRA_STREAM, imageUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                "image/png"
            } else {
                "text/plain"
            }
        }

        val shareIntent = Intent.createChooser(
            sendIntent,
            context.getString(R.string.share_character_title)
        ).apply {
            // Needed if starting from a non-activity context, 
            // but usually we want to preserve the task stack.
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        
        context.startActivity(shareIntent)
    }

    private suspend fun fetchAndSaveImage(character: Character): Uri? {
        val request = ImageRequest.Builder(context)
            .data(character.image)
            .build()

        val result = imageLoader.execute(request)
        return if (result is SuccessResult) {
            val bitmap = (result.drawable as? BitmapDrawable)?.bitmap
            bitmap?.let { saveBitmapToCache(it, "character_${character.id}.png") }
        } else null
    }

    private suspend fun saveBitmapToCache(bitmap: Bitmap, fileName: String): Uri? = withContext(Dispatchers.IO) {
        try {
            val cachePath = File(context.cacheDir, "images").apply { mkdirs() }
            val file = File(cachePath, fileName)
            FileOutputStream(file).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            }
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
