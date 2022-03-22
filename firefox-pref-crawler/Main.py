
import os
from Crawlers import FirefoxCrawler


# Get data and Read files

for i in range(57, 61):
    FirefoxCrawler.download_browser_pref(i)
    os.system("mkdir -p downloaded_browser/build_%s" % i)
    os.system("mv *.zip downloaded_browser/build_%d" % i)

# for i in range(58, 99):

# FirefoxCrawler.download_mobile_pref(64)
# os.system("mkdir -p downloaded_mobile/build_%s" % 64)
# os.system("mv *.zip downloaded_mobile/build_%d" % 64)
# FirefoxCrawler.download_mobile_pref(67)
# os.system("mkdir -p downloaded_mobile/build_%s" % 67)
# os.system("mv *.zip downloaded_mobile/build_%d" % 67)
# FirefoxCrawler.download_mobile_pref(69)
# os.system("mkdir -p downloaded_mobile/build_%s" % 69)
# os.system("mv *.zip downloaded_mobile/build_%d" % 69)