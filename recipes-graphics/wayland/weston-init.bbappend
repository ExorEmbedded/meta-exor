FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

INI_UNCOMMENT_USE_G2D:us04-kit = ""

do_install:append() {
	echo "export XDG_RUNTIME_DIR=/tmp/weston" >> ${D}/${sysconfdir}/default/weston

	install -d ${D}/${sysconfdir}/profile.d
	ln -s ${sysconfdir}/default/weston ${D}/${sysconfdir}/profile.d/weston.sh
}
