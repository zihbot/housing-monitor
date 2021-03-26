from dynaconf import settings, FlaskDynaconf
import logging
import dynaconf
from dynaconf.base import Settings
from flask import Flask, jsonify, request, abort
from with_urllib import get_site, save_images, get_image_urls

logger = logging.Logger(__name__)
app = Flask(__name__)

@app.route('/pairs', methods=['GET'])
def get_pairs():
    url = request.args.get('url')
    logger.debug(url)
    if url is None:
        return jsonify([])
    return jsonify(get_site(url))

@app.route('/images/download', defaults={'folder': None}, methods=['GET'])
@app.route('/images/download/<folder>', methods=['GET'])
def get_images_download(folder):
    url = request.args.get('url')
    logger.debug('get_images() url={} folder={}'.format(url, folder))
    if url is None:
        return jsonify({'dirName': ''})
    return jsonify({'dirName': save_images(url, folder)})

@app.route('/images/urls', methods=['GET'])
def get_images_urls():
    url = request.args.get('url')
    logger.debug('get_images() url=' + url)
    if url is None:
        abort(400, 'url value not provided')
    return jsonify({'urls': get_image_urls(url)})

@app.route('/metadata/<folder>', methods=['GET'])
def get_metadata(folder):
    logger.debug('get_metadata() folder=' + folder)
    raise NotImplemented()
    return jsonify([])

@app.route('/similarity/<folder>', methods=['GET'])
def get_similarity(folder):
    url = request.args.get('url')
    logger.debug('get_similarity() folder={} url={}'.format(folder, url))
    if url is None:
        abort(400, 'url value not provided')
    raise NotImplemented()
    return jsonify([])

if __name__ == "__main__":
    app.run()
