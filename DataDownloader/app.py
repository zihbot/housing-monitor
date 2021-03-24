from dynaconf import settings, FlaskDynaconf
import logging
import dynaconf
from dynaconf.base import Settings
from flask import Flask, jsonify, request
from with_urllib import get_site, save_images

logger = logging.Logger(__name__)
app = Flask(__name__)

@app.route('/pairs', methods=['GET'])
def get_pairs():
    url = request.args.get('url')
    logger.debug(url)
    if url is None:
        return jsonify([])
    return jsonify(get_site(url))

@app.route('/images', methods=['GET'])
def get_images():
    url = request.args.get('url')
    logger.debug('get_images() url=' + url)
    if url is None:
        return jsonify({'dirName': ''})
    return jsonify({'dirName': save_images(url)})

if __name__ == "__main__":
    app.run()
