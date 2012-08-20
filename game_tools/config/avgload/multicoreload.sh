#!/bin/bash

function print_load(){
	v_load=`cat /proc/stat | grep cpu | awk '{print $1,$2s,$3,$4,$5}'`
	echo "$v_load" >> $1
}
v_logfile="/data/multicoreload.log"
v_time=`date '+%F %T'`
echo "$v_time" >> $v_logfile
echo "-----" >> $v_logfile
print_load $v_logfile
echo "-----" >> $v_logfile
echo "*-*-*-*-*-*" >> $v_logfile
