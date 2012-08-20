#!/bin/bash

Time=`date '+%F %T'`

mem_cpu_status=`ps aux | grep java | grep -v grep | awk '{print $12,$2}'`

echo "$mem_cpu_status" | while read line
  do
    pid=`echo $line | awk '{print $2}'`
    servername=`echo $line | awk '{print $1}'`
    servername=`echo $servername | awk -F '=' '{print $2}'`
    #servername=`echo $servername |  sed -n '='`
    logfile="/data/test/${servername}.log"

    echo "$Time" >> $logfile
    jmap_status=`jmap -histo:live ${pid} |grep com.imop`
    echo "$jmap_status" | while read jmapline
      do
         instancecount=`echo $jmapline | awk '{print $2}'`
         instancesize=`echo $jmapline | awk '{print $3}'`
         if [ $instancecount -gt 10000 -o $instancesize -gt 1000000 ];then
               echo "$jmapline" >> $logfile
         fi
      done 
    echo "-*-*-*-*-*" >> $logfile
  done

