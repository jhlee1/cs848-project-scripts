import os


for i in range(57, 61):
    os.system("cd /Users/joohan/PycharmProjects/firefox-pref-crawler/downloaded_browser/build_%s;find ./ -name '*.zip' | xargs unzip;find ./ -type d -name profile | xargs -I{} mv {} ./;find ./ -type d -name 'mozilla*' | xargs rm -rf" % i)
    os.system("mv downloaded_browser/build_%s/profile downloaded_files/build_%s" % (i, i))

# os.system("cd /Users/joohan/PycharmProjects/firefox-pref-crawler/downloaded_browser/build_%s;find ./ -name '*.zip' | xargs unzip;find ./ -type d -name profile | xargs -I{} mv {} ./;find ./ -type d -name 'mozilla*' | xargs rm -rf" % i)
# os.system("mv downloaded_browser/build_%s/profile downloaded_files/build_%s" % (i, i))
#         os.system("cd /Users/joohan/PycharmProjects/firefox-pref-crawler/downloaded_mobile/build_%s;find ./ -name '*.zip' | xargs unzip;find ./ -type d -name app | xargs -I{} mv {} ./;find ./ -type d -name 'mozilla*' | xargs rm -rf" % i)
#     os.system("mv downloaded_mobile/build_%s/app downloaded_files/build_%s" % (i, i))
