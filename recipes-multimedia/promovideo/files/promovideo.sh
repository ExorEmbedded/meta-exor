#!/bin/bash

trap '' HUP
/etc/init.d/xserver-nodm stop
sleep 1
echo "start Video"
/usr/bin/gst-launch-1.0 playbin uri=file:///usr/share/exorvideo/JSmart_Promotional.OGG video-sink=fbdevsink
sleep 1;
/etc/init.d/xserver-nodm start


