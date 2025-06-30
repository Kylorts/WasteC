package com.example.wastec.data.datasource.local.ml

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.wastec.domain.model.ClassificationResult
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class WasteClassifierHelper(
    private val context: Context,
    private val classifierListener: ClassifierListener?
) {
    private var interpreter: Interpreter? = null
    private lateinit var labels: List<String>
    private val inputImageWidth = 400
    private val inputImageHeight = 400

    init {
        setupInterpreter()
    }

    private fun setupInterpreter() {
        try {
            val model = loadModelFile(context, "model_EfficientNetV2S.tflite")
            val options = Interpreter.Options().apply {
                numThreads = 4
            }
            interpreter = Interpreter(model, options)
            labels = context.assets.open("labels.txt").bufferedReader().useLines { it.toList() }
        } catch (e: Exception) {
            classifierListener?.onError("Gagal menginisialisasi TFLite Interpreter: ${e.message}")
            Log.e("WasteClassifierHelper", "Error initializing TFLite Interpreter", e)
        }
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun classify(image: Bitmap) {
        if (interpreter == null || labels.isEmpty()) {
            classifierListener?.onError("Classifier belum siap.")
            return
        }

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(image)

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(inputImageHeight, inputImageWidth, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        val processedImage = imageProcessor.process(tensorImage)

        val outputProbabilityBuffer = Array(1) { FloatArray(labels.size) }

        interpreter?.run(processedImage.buffer, outputProbabilityBuffer)

        val topResult = outputProbabilityBuffer[0]
            .mapIndexed { index, confidence ->
                ClassificationResult(labels[index], confidence)
            }
            .maxByOrNull { it.confidence }

        classifierListener?.onResults(topResult)
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(result: ClassificationResult?)
    }
}