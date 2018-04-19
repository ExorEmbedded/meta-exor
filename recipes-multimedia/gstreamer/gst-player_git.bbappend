# Remove icon from desktop. We already have a qt5 player demo
do_install_append() {
	rm ${D}${datadir}/applications/gst-player.desktop
}
