# HanBianCoreCode
本项目为微信小程序韩变的后端核心代码

## Korean类
**目前已拆分到仓库[Korean](https://github.com/yuzheng14/Korean)**
### hangulToJamo方法
将语料中的韩语文字拆分成韩语字母
### jamoToHangul方法
将语料中的韩语字母合成为韩语文字，同时不改变原语料中的其余符号

## Transfer类
用于将韩语文字转换成其发音  
**已重新编写完成**

# 更新说明
## 2021.1.31
* 重构`jamosToPronunciation()`
* 重构`uiTransfer()`
## 2021.2.1
* 重构`addOtherSymbol()`
* 测试`jamosToPronunciation()`
* 重构`HangulTransfer()`
* 测试`HangulTransfer()`
##2021.2.2
* 解决`jamosToPronunciation()`中鼻音化的问题

## 待更新列表
**暂无**