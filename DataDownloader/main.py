from selenium.webdriver import Chrome
from selenium.webdriver.chrome.options import Options
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.chrome.webdriver import WebDriver
from webdriver_manager.chrome import ChromeDriverManager
from dynaconf import settings
from itertools import count
import time
import sys
import logging
from flask import Flask, jsonify, request


def parse_site(browser: WebDriver, get_type: str, get_value: str):
    if get_type == "s":
        return browser.find_element_by_css_selector(get_value).text
    if get_type == "i":
        return browser.find_element_by_id(get_value).text
    if get_type == "Fs":
        el = []
        try:
            for i in count(1):
                el.append(parse_site(browser, "s", get_value.format(i)))
        except:
            return el

def map_dict_to_key_value_list(d: dict):
    return [{'key': k, 'value': v} for k, v in d]


app = Flask(__name__)

@app.route('/pairs', methods=['GET'])
def get_data():
    if 'url' not in request.args.keys:
        return jsonify([])
    else:
        url = request.args.get('url')
    options = Options()
    options.headless = True
    browser = Chrome(ChromeDriverManager().install(), options=options)

    #url = 'https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/31119133'
    site = 'ingatlanCom'
    logging.info('Getting ', url)
    browser.get(url)
    time.sleep(3)

    problems = 0
    result = {}
    for pair in settings[site]['pairs']:
        try:
            key = parse_site(browser, pair[0], pair[1])
            value = parse_site(browser, pair[2], pair[3])
            if isinstance(key, list):
                result.update(dict(zip(key, value)))
            else:
                result.update({key: value})
        except NoSuchElementException as ex:
            logging.warning("Element not found: ", ex.msg)
            problems += 1
        except:
            logging.error('Error: ', sys.exc_info()[0])
            problems += 1
    logging.info('Finished. Problems found: ', problems)
    logging.debug('Result: ', result)
    return jsonify(map(map_dict_to_key_value_list, result))

if __name__ == "__main__":
    app.run()