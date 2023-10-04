package com.moneyminions.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.content.FileProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

private const val TAG = "UploadUtils_D210"
object UploadUtils {
    /**
     * filePath로 multipart 객체를 만듭니다.
     * filePath == 앨범에서 가져온 사진 경로
     * key = 통신할때 사용할 값
     */
    fun createMultipartFromUri(context: Context, key: String, filePath: String): MultipartBody.Part? {
        try {
//            val uri = FileProvider.getUriForFile(
////                TedPermissionProvider.context,
//                context,
//                "com.moneyminions.fileprovider",
//                File(filePath),
//            )
    
            val uri = Uri.parse(filePath)
            Log.d(TAG, "createMultipartFromUri: 1 $uri")

            val file: File? = getFileFromUri(context, uri)
            if (file == null) {
                // 파일을 가져오지 못한 경우 처리할 로직을 작성하세요.
                return null
            }
            Log.d(TAG, "createMultipartFromUri: 2 $file")

            val requestFile: RequestBody = createRequestBodyFromFile(file)
            
            Log.d(TAG, "createMultipartFromUri: 3 $requestFile")
            
            return MultipartBody.Part.createFormData(key, file.name, requestFile)
        } catch (e: Exception) {
            Log.d(TAG, "createMultipartFromUri Exception: ${e.message}")
            return null
        }
    }

    /**
     * uri로 사진 파일을 가져옵니다
     * createMultipartFromUri로 결과값을 반환합니다
     */
    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val filePath = uriToFilePath(context, uri)
        return if (filePath != null) File(filePath) else null
    }

    /**
     * 만들어진 uri를 파일로 변환합니다
     */
    @SuppressLint("Range")
    private fun uriToFilePath(context: Context, uri: Uri): String? {
        Log.d(TAG, "uriToFilePath: $uri")
        lateinit var filePath: String
        context.contentResolver.query(uri, null, null, null, null).use { cursor ->
            cursor?.let {
                if (it.moveToFirst()) {
                    val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    val file = File(context.cacheDir, displayName)
                    try {
                        val inputStream = context.contentResolver.openInputStream(uri)
                        val outputStream = FileOutputStream(file)
                        inputStream?.copyTo(outputStream)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    filePath = file.absolutePath
                }
            }
        }
        return filePath
    }

    /**
     * 저장된 사진 파일의 body를 가져옵니다
     */
    private fun createRequestBodyFromFile(file: File): RequestBody {
        val MEDIA_TYPE_IMAGE = "multipart/form-data".toMediaTypeOrNull()
        val inputStream: InputStream = FileInputStream(file)
        val byteArray = inputStream.readBytes()
        return RequestBody.create(MEDIA_TYPE_IMAGE, byteArray)
    }
}