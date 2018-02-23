DESCRIPTION = "JMobile Portable"
LICENSE = "Proprietary"

LIC_FILES_CHKSUM = "file://${WORKDIR}/RS9113.NBZ.NL.GNU.LNX.1.3.RC9/Releasenotes.txt;md5=1e7324aca892675807a7fab1b1ef199c"

SRC_URI = "file://RS9113.NBZ.NL.GNU.LNX.1.3.RC9.tgz"

S = "${WORKDIR}/RS9113.NBZ.NL.GNU.LNX.1.3.RC9/"


do_install() {
	install -d  ${D}${nonarch_base_libdir}/firmware/
	install 	${S}/Firmware/*	${D}${nonarch_base_libdir}/firmware/
}

FILES_${PN} += "${nonarch_base_libdir}/firmware/*"
