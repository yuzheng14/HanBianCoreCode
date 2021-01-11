from jamo import j2h
from jamo import InvalidJamoError
import sys
import os

def synthesis(a):
    """
    将java传输过来的字母合成成字
    """
    result=[]
    try:
        for i in range(len(a)):
            if(a[i]!=" "):
                result.append(j2h(*a[i]))
            else:
                result.append(" ")
    except InvalidJamoError:
        result="输入格式有误，无法解析"
    
    result=''.join(result)
    os.chdir('/home/wwwroot/ftptest/WEBAPP/web/WEB-INF/lib')
    with open('synthesisResult.txt',mode='w+',encoding='UTF-8') as f:
        f.write(result)
    return result

if __name__=='__main__':
    a=[]
    for i in range(1,len(sys.argv)):
        a.append(sys.argv[i])
    # a=['ㅇㅕㅇ','ㅇㅓ']
    print(synthesis(a))