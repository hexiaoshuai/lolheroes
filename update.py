#!/usr/bin/env python2
# -*- coding: utf-8 -*-

__author__ = "HeXiaoShuai"
 
# email : 309948071@qq.com

import os
import sys
import json
import urllib2
import traceback
import re
import shutil
import requests
from collections import OrderedDict

class Common(object):
    @staticmethod
    def getJson(url):
        html = None
        try:
            request = urllib2.Request(url)
            response = urllib2.urlopen(request)
            contentBytes = response.read()
            html = contentBytes.decode('UTF-8')
        except:
            print "get html from %s error!" %url
            traceback.print_exc()
        pattern = re.compile(r"\"data\"\:(.*?)\,\"version\"\:",re.S)
        result = re.findall(pattern, html)[0]
        dictJson = json.loads(result, object_pairs_hook=OrderedDict)
        strJson = json.dumps(dictJson)
        return strJson

    @staticmethod
    def writeFile(fileName, str):
        output = open(fileName, 'w')
        output.write(str)
        output.close()

    @staticmethod
    def cur_file_dir():
        path = sys.path[0]
        if os.path.isdir(path):
            return path
        elif os.path.isfile(path):
            return os.path.dirname(path)

    @staticmethod
    def mkDir(path):
        if os.path.isdir(path):
            shutil.rmtree(path)
        os.makedirs(path)

    @staticmethod
    def dowloadPic(imageUrl,filePath):
        r = requests.get(imageUrl)
        with open(filePath, "wb") as code:
            code.write(r.content)

class UpdateHeroes(object):
    def __init__(self, savePath):
        self.savePath = savePath

    def run(self):
        Common.mkDir(self.savePath + "json")
        Common.mkDir(self.savePath + "champion")
        Common.mkDir(self.savePath + "skin")
        Common.mkDir(self.savePath + "passive")
        Common.mkDir(self.savePath + "spell")
        heroes = Common.getJson("http://lol.qq.com/biz/hero/champion.js")
        Common.writeFile(self.savePath + "json/" + "heroes.json", heroes);
        pattern = re.compile(r"\"full\"\: \"(.*?)\"\, \"sprite",re.S)
        championList = re.findall(pattern, heroes)
        for champion in championList:
            Common.dowloadPic("http://ossweb-img.qq.com/images/lol/img/champion/" + champion, self.savePath + "champion/" + champion)
        pattern = re.compile(r"\"id\"\: \"(.*?)\"\, \"key",re.S)
        heroList = re.findall(pattern, heroes)
        for hero in heroList:
            heroJson = Common.getJson("http://lol.qq.com/biz/hero/" + hero + ".js")
            Common.writeFile(self.savePath + "json/" + "hero_" + hero + ".json", heroJson)
            pattern = re.compile(r"\"skins\"\: \[(.*?)\]\, \"info",re.S)
            skinJson = re.findall(pattern, heroJson)
            pattern = re.compile(r"\"id\"\: \"(.*?)\"\, \"num",re.S)
            skinList = re.findall(pattern, skinJson[0])
            Common.dowloadPic("http://ossweb-img.qq.com/images/lol/web201310/skin/big" + skinList[0] + ".jpg", self.savePath + "skin/big" + skinList[0] + ".jpg")
            for skin in skinList:
                #Common.dowloadPic("http://ossweb-img.qq.com/images/lol/web201310/skin/big" + skin + ".jpg", self.savePath + "skin/big" + skin + ".jpg")
                Common.dowloadPic("http://ossweb-img.qq.com/images/lol/web201310/skin/small" + skin + ".jpg", self.savePath + "skin/small" + skin + ".jpg")
            pattern = re.compile(r"\"passive\"\: (.*?)\, \"lore",re.S)
            passiveJson = re.findall(pattern, heroJson)
            pattern = re.compile(r"\"full\"\: \"(.*?)\"\, \"sprite",re.S)
            passive = re.findall(pattern, passiveJson[0])
            Common.dowloadPic("http://ossweb-img.qq.com/images/lol/img/passive/" + passive[0], self.savePath + "passive/" + passive[0])
            pattern = re.compile(r"\"spells\"\: \[(.*?)\]\, \"passive",re.S)
            spellJson = re.findall(pattern, heroJson)
            pattern = re.compile(r"\"full\"\: \"(.*?)\"\, \"sprite",re.S)
            spellList = re.findall(pattern, spellJson[0])
            for spell in spellList:
                Common.dowloadPic("http://ossweb-img.qq.com/images/lol/img/spell/" + spell, self.savePath + "spell/" + spell)

if __name__ == "__main__":
    updateHeroes = UpdateHeroes(Common.cur_file_dir() + "/app/src/main/assets/")
    updateHeroes.run()
