# %%
import os, sys
from PIL import Image, ImageOps

os.environ["CUDA_VISIBLE_DEVICES"] = ""
import tensorflow as tf

#%%
size = 640, 480
in_folder = '.img/'
out_folder = '.img/resized/'
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
# %%
