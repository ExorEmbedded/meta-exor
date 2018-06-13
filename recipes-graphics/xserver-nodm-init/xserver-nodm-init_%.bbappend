FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " file://55xScreenSaver-nodpms \
	file://90XWindowManager.sh_resistive_touch"

do_install_append() {
	install -d ${D}${sysconfdir}/X11/Xsession.d
	install -m 755 ${WORKDIR}/55xScreenSaver-nodpms ${D}${sysconfdir}/X11/Xsession.d/55xScreenSaver
}

do_install_append_usom01() {
	install -m 775 ${WORKDIR}/90XWindowManager.sh_resistive_touch ${D}${sysconfdir}/X11/Xsession.d/90XWindowManager.sh
}
