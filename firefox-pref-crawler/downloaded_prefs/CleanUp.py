import os


for i in range(57, 97):
    os.system("cd /Users/joohan/PycharmProjects/firefox-pref-crawler/downloaded_browser/build_%s;find ./ -name '*.zip' | xargs unzip;find ./ -type d -name profile | xargs -I{} mv {} ./;find ./ -type d -name 'mozilla*' | xargs rm -rf" % i)
