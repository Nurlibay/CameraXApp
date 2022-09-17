package uz.unidev.cameraxapp

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

/**
 *  Created by Nurlibay Koshkinbaev on 17/09/2022 16:18
 */

class ImageAnalyzer(private val listener: ImageListener): ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind() /** Rewind the buffer to zero */
        val data = ByteArray(remaining())
        get(data) /** Copy the buffer into a byte array */
        return data /** Return the byte array */
    }

    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map {
            it.toInt() and 0xFF
        }
        val luma = pixels.average()

        listener(luma)
        image.close()
    }
}