# %%
import os, sys
from PIL import Image, ImageOps

os.environ["CUDA_VISIBLE_DEVICES"] = ""
import tensorflow as tf

#%%
size = 640, 480
in_folder = '.img/'
out_folder = '.img/resized/'
#%%
# Preprocessing
# %%
for filename in os.listdir(in_folder):
    try:
        im = Image.open(in_folder + filename)
        ratio = min(im.size[0] / size[0], im.size[1] / size[1])
        left = (im.size[0] - int(size[0] * ratio)) // 2
        top  = (im.size[1] - int(size[1] * ratio)) // 2
        im = im.crop((left, top, im.size[0], im.size[1]))
        im = im.resize(size)
        im.save(out_folder + filename)
    except:
        print('Cannot resize image: ' + filename)

#%%
# Loading
# %%
batch_size=10
seed = 42

train_ds = tf.keras.preprocessing.image_dataset_from_directory(
    out_folder,
    validation_split=0.2,
    subset="training",
    seed=seed,
    image_size=size,
    batch_size=batch_size
)
test_ds = tf.keras.preprocessing.image_dataset_from_directory(
    out_folder,
    validation_split=0.2,
    subset="validation",
    seed=seed,
    image_size=size,
    batch_size=batch_size
)
# %%
# Model
#%%
for image_batch, labels_batch in train_ds:
  print(image_batch.shape)
  print(labels_batch.shape)
  break
# %%
from tensorflow.keras import layers
num_classes = len(train_ds.class_names)

model = tf.keras.Sequential([
    layers.experimental.preprocessing.Rescaling(1./255,  name='normalizer'),
    layers.Conv2D(10, 3, activation='relu', name='conv1'),
    layers.MaxPooling2D(name='pool1'),
    layers.Conv2D(10, 3, activation='relu', name='conv2'),
    layers.MaxPooling2D(name='pool2'),
    layers.Flatten(name='flat'),
    layers.Dense(128, activation='relu', name='dense'),
    layers.Dense(num_classes)
])
model.compile(
  optimizer='adam',
  loss=tf.losses.SparseCategoricalCrossentropy(from_logits=True),
  metrics=['accuracy'])
# %%
# Train
# %%
model.fit(
  train_ds,
  validation_data=test_ds,
  epochs=5
)
# %%
# Save
# %%
model.save('model.h5')
# %%
model2 = tf.keras.models.load_model('model.h5')
# %%
import numpy as np
for image, label in test_ds:
    for i in range(len(label)):
        #print(image[i].shape)
        true = label[i]
        if test_ds.class_names[true] != 'layout':
            continue
        print(test_ds.class_names[true])
        predicted = model2.predict(tf.expand_dims(image[i],0))
        print(predicted)
        print(test_ds.class_names[np.argmax(predicted)])
# %%
