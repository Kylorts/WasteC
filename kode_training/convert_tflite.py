import tensorflow as tf


# Sesuaikan dengan path model keras Anda
MODEL_PATH = 'C:/Users/User/Documents/Kuliah/Semester 4/Model_Keras/EfficientNetV2S_model.keras'

try:
    full_model = tf.keras.models.load_model(MODEL_PATH)
    print("Model lengkap berhasil dimuat.")
    full_model.summary()
except Exception as e:
    print(f"Gagal memuat model: {e}")
    exit()

try:
    inference_model = tf.keras.Sequential([
        tf.keras.Input(shape=(400, 400, 3), name="input_layer_inference"), 
        full_model.get_layer('efficientnetv2-s'), 
        full_model.get_layer('global_average_pooling2d'),
        full_model.get_layer('dense'),
        full_model.get_layer('dropout'),
        full_model.get_layer('dense_1')
    ])
    print("\nModel inferensi baru berhasil dibuat.")
except Exception as e:
    print(f"\nGagal membuat model inferensi. Periksa nama layer. Error: {e}")
    exit()

converter = tf.lite.TFLiteConverter.from_keras_model(inference_model)

converter.optimizations = [tf.lite.Optimize.DEFAULT]

tflite_model = converter.convert()

with open('model_EfficientNetV2S.tflite', 'wb') as f:
  f.write(tflite_model)

  print("\nModel baru telah berhasil dibuat.")