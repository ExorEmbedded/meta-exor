#/bin/bash
export DISPLAY=:0
gst-launch-1.0 -e v4l2src device=/dev/video0 ! "video/x-raw,format=RGB16,width=720,height=576,framerate=(fraction)30/1" ! videoconvert ! videoscale ! video/x-raw,width=400,height=400 ! videoflip  method=rotate-180 ! videoconvert ! ximagesink sync=false

