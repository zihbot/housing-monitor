import logging
from urllib.request import Request, urlopen, urlretrieve
from bs4 import BeautifulSoup
from flask.app import Flask
from lxml import etree, html
from itertools import count
import sys
import re
import os
import json
import random
import string
from ast import literal_eval
from dynaconf import settings, FlaskDynaconf

logger = logging.Logger(__name__)
app = Flask(__name__)
FlaskDynaconf(app)

def parse_site(html: str, get_type: str, get_value: str):
    if get_type == "css":
        return etree.HTML(html).cssselect(get_value)[0].xpath('string()').strip()
    if get_type == "xpath":
        return etree.HTML(html).xpath(get_value)[0].xpath('string()').strip()
    if get_type == "text":
        return get_value.strip()
    if get_type == "format_css":
        el = []
        try:
            for i in count(1):
                el.append(parse_site(html, "css", get_value.format(i)))
        except:
            return el
    if get_type == "format_xpath":
        el = []
        try:
            for i in count(1):
                el.append(parse_site(html, "xpath", get_value.format(i)))
        except:
            return el

def map_dict_to_key_value_list(d: dict) -> list:
    return [{'key': k, 'value': v} for k, v in d.items()]

def get_site(url: str) -> list:
    req = Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    html = urlopen(req).read().decode('utf-8')
    site = 'ingatlanCom'
    problems = 0
    result = {}
    logger.debug(html)
    for pair in app.config[site]['pairs']:
        try:
            key = parse_site(html, pair[0], pair[1])
            value = parse_site(html, pair[2], pair[3])
            if isinstance(key, list):
                result.update(dict(zip(key, value)))
            else:
                result.update({key: value})
        except IndexError as ex:
            logger.warning('Element not found')
            problems += 1
        except:
            logger.error('Error: ', sys.exc_info()[0])
            problems += 1
    logger.info('Finished. Problems found: ', problems)
    logger.debug('Result: ', str(result))
    return map_dict_to_key_value_list(result)

def save_images(url: str, folder: str = None) -> str:
    result = get_image_urls(url)
    if folder is None:
        folder = ''.join(random.choice(string.ascii_uppercase + string.digits) for i in range(16))
    os.mkdir(os.path.join(app.config['imageFolder'], folder))
    for img_url in result:
        path = os.path.join(app.config['imageFolder'], folder, img_url[img_url.rfind('/')+1:])
        urlretrieve(img_url, path)
    return folder

def get_image_urls(url: str) -> list[str]:
    req = Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    html = urlopen(req).read().decode('utf-8')
    #
    script = etree.HTML(html).cssselect('.card.listing .listing-left script')[0].xpath('string()')
    imgs = json.loads(literal_eval("'%s'" % re.search('\[.*\]', script)[0]))
    result = [image_obj['large_url'] for image_obj in imgs]
    #
    return result

if __name__ == "__main__":
    #print(get_site("https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/31119133"))
    print(save_images('https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/31119133'))
