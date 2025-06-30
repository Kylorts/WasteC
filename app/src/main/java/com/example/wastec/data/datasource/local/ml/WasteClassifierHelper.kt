package com.example.wastec.data.datasource.local.ml

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.wastec.domain.model.ClassificationResult
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.ops.NormalizeOp
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
            val inputTensor = interpreter!!.getInputTensor(0)
            val outputTensor = interpreter!!.getOutputTensor(0)

            Log.d("TFLite-Check", "--- Input Tensor ---")
            Log.d("TFLite-Check", "Shape: ${inputTensor.shape().joinToString(", ")}")
            Log.d("TFLite-Check", "Data Type: ${inputTensor.dataType()}")

            Log.d("TFLite-Check", "--- Output Tensor ---")
            Log.d("TFLite-Check", "Shape: ${outputTensor.shape().joinToString(", ")}")
            Log.d("TFLite-Check", "Data Type: ${outputTensor.dataType()}")
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

        // ====================================================================
        //           PERUBAHAN UTAMA: MENGGUNAKAN IMAGEPROCESSOR
        // ====================================================================

        // 1. Buat TensorImage dari Bitmap
        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(image)

        // 2. Buat ImageProcessor untuk resize DAN normalisasi
        // Ini meniru persis apa yang dilakukan ML Model Binding
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(inputImageHeight, inputImageWidth, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        // 3. Proses gambar
        val processedImage = imageProcessor.process(tensorImage)

        // 4. Siapkan buffer untuk output
        val outputProbabilityBuffer = Array(1) { FloatArray(labels.size) }

        // 5. Jalankan inferensi dengan buffer dari processedImage
        interpreter?.run(processedImage.buffer, outputProbabilityBuffer)

        // ====================================================================

        // 6. Proses output untuk mendapatkan hasil terbaik (kode ini tetap sama)
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