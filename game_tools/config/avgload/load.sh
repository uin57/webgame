#!/bin/bash

loadavg=`cat /proc/loadavg | cut -d " " -f1`
Time=`date '+%F %T'`
logfile="/data/avgload.log"

echo "$Time-----$loadavg-----" >> $logfile

mem_cpu_status=`ps aux | grep java | grep -v grep | awk '{print $12,$2,$3,$4}'`

echo "$mem_cpu_status" | while read line
  do
    pid=`echo $line | awk '{print $2}'`
    gctime=`/usr/local/webserver/jdk/jdk1.6.0_16/bin/jstat -gcutil $pid | tail -1`
    
    wholestatus=`echo $line $gctime`
    echo "$wholestatus" >> $logfile
  done

echo "-*-*-*-*-*" >> $logfile
