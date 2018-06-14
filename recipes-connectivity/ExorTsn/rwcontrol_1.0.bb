DESCRIPTION = "Test and config sw for 3t Tns switch"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

RDEPENDS_${PN} += "bash iproute2 iperf3 linuxptp iperf3 vlan"

SRC_URI = "\
        file://control.sh \
        file://regrw.c \
"

S = "${WORKDIR}"


do_compile () {
    ${CC} regrw.c ${LDFLAGS} -o regrw
}

do_install() {
    install -d ${D}${bindir}/
    install -m 0755 ${S}/regrw ${D}${bindir}/
    install -m 0755 ${S}/control.sh ${D}${bindir}/
}

FILES_${PN} = "${bindir}/regrw ${bindir}/control.sh"
