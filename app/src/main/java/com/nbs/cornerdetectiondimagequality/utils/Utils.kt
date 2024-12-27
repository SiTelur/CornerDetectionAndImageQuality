package com.nbs.cornerdetectiondimagequality.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"

private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

private const val TAG = "UriToBitmapConverter"

// Primary conversion method with multiple fallback strategies
fun convertUriToBitmap(
    context: Context,
    uri: Uri,
    maxWidth: Int = 1024,
    maxHeight: Int = 1024
): Bitmap? {
    return try {
        // Try multiple conversion strategies
        when {
            // Strategy 1: Content Resolver
            isContentUri(uri) -> convertFromContentResolver(context, uri, maxWidth, maxHeight)

            // Strategy 2: File URI
            isFileUri(uri) -> convertFromFileUri(context, uri, maxWidth, maxHeight)

            // Strategy 3: Resource URI
            isResourceUri(uri) -> convertFromResourceUri(context, uri, maxWidth, maxHeight)

            else -> null
        }
    } catch (e: Exception) {
        Log.e(TAG, "Bitmap conversion error", e)
        null
    }
}

// Content URI conversion
private fun convertFromContentResolver(
    context: Context,
    uri: Uri,
    maxWidth: Int,
    maxHeight: Int
): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use { stream ->
            // Decode with sampling to reduce memory usage
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeStream(stream, null, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false

            // Reopen stream for actual decoding
            context.contentResolver.openInputStream(uri)?.use { finalStream ->
                BitmapFactory.decodeStream(finalStream, null, options)
            }
        }
    } catch (e: Exception) {
        Log.e(TAG, "Content resolver conversion failed", e)
        null
    }
}

// File URI conversion
private fun convertFromFileUri(
    context: Context,
    uri: Uri,
    maxWidth: Int,
    maxHeight: Int
): Bitmap? {
    val filePath = uri.path ?: return null
    return try {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeFile(filePath, options)

        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false

        BitmapFactory.decodeFile(filePath, options)
    } catch (e: Exception) {
        Log.e(TAG, "File URI conversion failed", e)
        null
    }
}

// Resource URI conversion
private fun convertFromResourceUri(
    context: Context,
    uri: Uri,
    maxWidth: Int,
    maxHeight: Int
): Bitmap? {
    return try {
        val resourceId = uri.toString().split("/").last().toInt()
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(context.resources, resourceId, options)

        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false

        BitmapFactory.decodeResource(context.resources, resourceId, options)
    } catch (e: Exception) {
        Log.e(TAG, "Resource URI conversion failed", e)
        null
    }
}

// Sample size calculation to reduce memory usage
private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while ((halfHeight / inSampleSize) >= reqHeight &&
            (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

// URI type checkers
private fun isContentUri(uri: Uri) = uri.scheme == "content"
private fun isFileUri(uri: Uri) = uri.scheme == "file"
private fun isResourceUri(uri: Uri) = uri.scheme == "android.resource"

// Orientation handling
fun getBitmapWithCorrectRotation(
    context: Context,
    uri: Uri
): Bitmap? {
    val bitmap = convertUriToBitmap(context, uri) ?: return null

    return try {
        val exif = ExifInterface(context.contentResolver.openInputStream(uri)!!)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        rotateBitmapIfNeeded(bitmap, orientation)
    } catch (e: Exception) {
        Log.e(TAG, "Orientation correction failed", e)
        bitmap
    }
}

// Bitmap rotation utility
private fun rotateBitmapIfNeeded(bitmap: Bitmap, orientation: Int): Bitmap {
    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        else -> return bitmap
    }

    return Bitmap.createBitmap(
        bitmap, 0, 0,
        bitmap.width, bitmap.height,
        matrix, true
    )
}


// Extension functions for convenience
fun Uri.toBitmap(context: Context, maxWidth: Int = 1024, maxHeight: Int = 1024): Bitmap? {
    return convertUriToBitmap(context, this, maxWidth, maxHeight)
}

fun Uri.toBitmapWithOrientation(context: Context): Bitmap? {
    return getBitmapWithCorrectRotation(context, this)
}


