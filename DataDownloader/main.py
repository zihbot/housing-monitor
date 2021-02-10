from dynaconf import settings, FlaskDynaconf
import logging
from flask import Flask, jsonify, request
from with_urllib import get_site

logger = logging.Logger(__name__)
app = Flask(__name__)

@app.route('/pairs', methods=['GET'])
def get_data():
    url = request.args.get('url')
    logger.debug(url)
    if url is None:
        return jsonify([])
    return jsonify(get_site(url))


if __name__ == "__main__":
    app.run()