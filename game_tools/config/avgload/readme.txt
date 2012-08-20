使用load.sh监控服务器性能和gc的平均时间，使用com.imop.webzt.tools.avgload.ServerLoadAnalyzer 分析出xls


linux机器上增加crontab 

vi /etc/crontab

*/1 * * * * root /data/load.sh >> /root/crontest

*/1 表示循环，间隔1分钟执行load.sh
