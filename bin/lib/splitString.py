from jamo import j2hcj, h2j
import sys
import os


def split(request):
    # print(os.getcwd())
    os.chdir('F:/Programming/acode/HanBianCoreCode/lib')
    # print(os.getcwd())
    result=j2hcj(h2j(request))
    with open('result.txt',mode='w+',encoding='UTF-8') as f:
        f.write(result)
    return result


if __name__ == '__main__':
    # print(3)
    a=""
    for i in range(1,len(sys.argv)):
        a+=str(sys.argv[i])
    # print(a)
    print(split(a))
    # print(split('한국어 배우눈 것'))