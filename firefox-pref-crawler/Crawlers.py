
from Browser import Browser
import time


class FirefoxCrawler:
    def download_pref(build_version):
        build_name = "FIREFOX_RELEASE_%s_BASE" % build_version
        print("Downloading: %s" % build_name)

        browser = Browser()
        browser.get_page("https://hg.mozilla.org/mozilla-unified/tags")

        element1 = browser.get_element_by_xpath("//b[contains(text(), '%s')]" % build_name)
        element2 = element1.find_element_by_xpath("../../../td[@class='link']/a[contains(text(), 'files')]")
        browser.click_element(element2)

        browser.click_element_by_xpath("//a[contains(text(), 'modules')]")
        browser.click_element_by_xpath("//a[contains(text(), 'libpref')]")
        browser.click_element_by_xpath("//a[contains(text(), 'zip')]")


        time.sleep(2)

    def download_browser_pref(build_version):
        build_name = "FIREFOX_RELEASE_%s_BASE" % build_version
        print("Downloading: %s" % build_name)

        browser = Browser()
        browser.get_page("https://hg.mozilla.org/mozilla-unified/tags")

        element1 = browser.get_element_by_xpath("//b[contains(text(), '%s')]" % build_name)
        element2 = element1.find_element_by_xpath("../../../td[@class='link']/a[contains(text(), 'files')]")
        browser.click_element(element2)

        browser.click_element_by_xpath("//a[contains(text(), 'browser')]")
        browser.click_element_by_xpath("//a[contains(text(), 'app')]")
        browser.click_element_by_xpath("//a[contains(text(), 'profile')]")
        browser.click_element_by_xpath("//a[contains(text(), 'zip')]")

        time.sleep(2)

    def download_mobile_pref(build_version):
        build_name = "FIREFOX_RELEASE_%s_BASE" % build_version
        print("Downloading: %s" % build_name)

        browser = Browser()
        browser.get_page("https://hg.mozilla.org/mozilla-unified/tags")

        element1 = browser.get_element_by_xpath("//b[contains(text(), '%s')]" % build_name)
        element2 = element1.find_element_by_xpath("../../../td[@class='link']/a[contains(text(), 'files')]")
        browser.click_element(element2)

        browser.click_element_by_xpath("//a[contains(text(), 'mobile')]")
        browser.click_element_by_xpath("//a[contains(text(), 'android')]")
        browser.click_element_by_xpath("//a[contains(text(), 'app')]")
        browser.click_element_by_xpath("//a[contains(text(), 'zip')]")

        time.sleep(15)
