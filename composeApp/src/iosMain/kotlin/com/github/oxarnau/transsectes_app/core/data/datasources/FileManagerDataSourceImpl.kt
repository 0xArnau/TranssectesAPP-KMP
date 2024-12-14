package com.github.oxarnau.transsectes_app.core.data.datasources

import com.github.oxarnau.transsectes_app.core.domain.datasources.FileManagerDataSource
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.writeToFile

/**
 * Implementation of [FileManagerDataSource] for iOS.
 * This class provides functionality to save content to a CSV file in the app's Documents directory,
 * making it accessible to the user via the Files app if UIFileSharingEnabled is set in Info.plist.
 */
class FileManagerDataSourceImpl : FileManagerDataSource {

    /**
     * Saves the given content to a CSV file in the app's Documents directory.
     *
     * If the `UIFileSharingEnabled` key is set to true in the Info.plist,
     * the file will be visible to the user in the Files app on iOS.
     *
     * @param fileName The name of the file to be saved, e.g., "file.csv".
     * @param content The content to be written to the CSV file.
     */
    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    override fun saveToCSV(fileName: String, content: String) {
        // Get the path to the Documents directory
        val paths = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory, NSUserDomainMask, true
        )
        val documentsDirectory = paths.firstOrNull() as? String

        if (documentsDirectory != null) {
            // Create the full file path
            val filePath = "$documentsDirectory/$fileName"

            // Convert the content to NSString (iOS string format)
            val fileContent = NSString.create(string = content)

            // Write the content to the file
            val success = fileContent.writeToFile(
                filePath,
                atomically = true,
                encoding = NSUTF8StringEncoding,
                error = null
            )

            // Check if the file was successfully saved
            if (!success) {
                println("Error: Failed to save file to path $filePath")
            } else {
                println("File saved successfully at $filePath")
            }
        } else {
            println("Error: Unable to find the Documents directory")
        }
    }
}
