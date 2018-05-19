#!/bin/bash
# 使用说明
# 将此文件拷到任何一个目录下，需要加上你的执行权限 sudo chmod +x readfile.sh
# 此 shell 需要有一个配置文件，命名随意,记录什么目录下，什么后缀文件文件代码统计，这里只忽略空格行，目录一般建议使用绝对路径
# 配置文件格式例子如下: /home/linux/project/java/,java
# 注意配置文件末尾不要有空行，否则会有问题
# 执行格式  ./readfile.sh 配置文件
# 建议配置文件和readfile.sh 放在同一个目录下
# 读者看懂代码也可以自行修改里面的内容
conf=$1
whole=0
filesearchAndCount()
{
    suffix=$1
    filename2=(`ls`)
    for b  in ${filename2[*]}
    do 	
        if [ -d $b ]
        then
           cd $b	
             filesearchAndCount $suffix
           cd .. 
        else
           filesuffix=(`echo $b | cut -d . -f 2`)
           # 后缀是否相同
           if [ $filesuffix = $suffix ]
           then
              echo $b
              count=(`cat $b | grep -v "^$" | wc -l`)
              ((whole=whole+count))
              echo $count
           fi
        fi
    done
}

while read line 
do
  path=(`echo $line | cut -d , -f 1`)
  suffix=(`echo $line | cut -d , -f 2`)
  echo $path
  cd $path
  filesearchAndCount $suffix
  # echo $whole
done < $conf

echo "whole lines of code: "$whole

