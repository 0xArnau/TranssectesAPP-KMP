package com.github.oxarnau.transsectes_app.core.data.datasources


import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.github.oxarnau.transsectes_app.core.domain.datasources.FileManagerDataSource
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * Implementation of the [FileManagerDataSource] that handles saving content to CSV files
 * either in the internal storage or the public "Downloads" folder depending on the Android version.
 *
 * @property context The context of the Android application used to access content resolver and storage.
 */
class FileManagerDataSourceImpl(private val context: Context) :
    FileManagerDataSource {

    /**
     * Saves the given content to a CSV file. If the Android version is 10 or higher, it uses
     * the [MediaStore] to save the file to the "Downloads" directory. For Android versions below
     * 10, it falls back to using [Environment.getExternalStoragePublicDirectory].
     *
     * @param fileName The name of the file to be saved, e.g., "file.csv".
     * @param content The content to be written to the CSV file.
     */
    override fun saveToCSV(fileName: String, content: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 (API 29) and higher, use MediaStore to save files to the "Downloads" directory.
            saveToDownloadsUsingMediaStore(fileName, content)
        } else {
            // For older versions, use the deprecated Environment method to save files to external storage.
            val downloadsDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)

            BufferedWriter(FileWriter(file)).use { writer ->
                writer.write(content)
            }
        }
    }

    /**
     * Saves the given content to a CSV file in the "Downloads" directory using the [MediaStore].
     * This method is used for Android 10 (API 29) and higher to save files to the public "Downloads"
     * folder, ensuring compatibility with Scoped Storage.
     *
     * [MediaStore] is the recommended API for accessing shared storage on Android 10 and higher.
     * It provides a safer way to manage files and data stored by apps, especially regarding external storage.
     *
     * @param fileName The name of the file to be saved (e.g., "file.csv").
     * @param content The content to be written to the file.
     */
    private fun saveToDownloadsUsingMediaStore(
        fileName: String,
        content: String
    ) {
        // Create ContentValues to specify the metadata for the file
        val contentValues = ContentValues().apply {
            put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                fileName
            )  // Set the file name
            put(
                MediaStore.MediaColumns.MIME_TYPE,
                "text/csv"
            )  // Set the MIME type as CSV
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_DOWNLOADS
            )  // Set the relative path to "Downloads"
        }

        // Get the ContentResolver instance
        val resolver = context.contentResolver

        // Insert a new entry into the MediaStore
        val uri: Uri? = resolver.insert(
            MediaStore.Files.getContentUri("external"),
            contentValues
        )

        // Check if the URI is valid
        uri?.let { outputUri ->
            // Open an output stream for the URI and write the content to the file
            val outputStream: OutputStream? =
                resolver.openOutputStream(outputUri)
            outputStream.use { stream ->
                val writer = BufferedWriter(OutputStreamWriter(stream))
                writer.write(content)
                writer.flush()
            }
        }
    }
}
