from selenium import webdriver
from selenium.webdriver.common.by import By


class Browser:
    def __init__(self):
        option = webdriver.ChromeOptions()
        option.add_argument('—incognito')
        option.add_experimental_option("prefs", {
            "download.default_directory": r"/Users/joohan/PycharmProjects/firefox-pref-crawler",
            "download.prompt_for_download": False,
            "download.directory_upgrade": True,
            "safebrowsing.enabled": True
        })

        self.webDriver = webdriver.Chrome(executable_path='resources/chromedriver', chrome_options=option)
        self.webDriver.maximize_window()
        self.webDriver.implicitly_wait(1)

    def get_page(self, url):
        self.webDriver.get(url)

    def get_element_by_xpath(self, xpath):
        return self.webDriver.find_element(by=By.XPATH, value=xpath)

    def get_elements_by_xpath(self, xpath):
        return self.webDriver.find_elements(by=By.XPATH, value=xpath)

    def click_element(self, element):
        return element.click()

    def click_element_by_xpath(self, xpath):
        return self.webDriver.find_element_by_xpath(xpath).click()

    def scroll_to_element(self, element):
        self.webDriver.execute_script("arguments[0].scrollIntoView();", element)

    def get_current_url(self):
        return self.webDriver.current_url