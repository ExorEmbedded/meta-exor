DESCRIPTION = "JMobile Portable"
LICENSE = "Proprietary"

LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ce29dbb849109f28c0a0358e8fedbc64"

PR = "2"
PV = "233"

JM_URI_us01-kit = "http://download.exorembedded.net:8080/Public/usom01/JMobile/jmobile2.01-${PV}-${PR}_portable_us01kit_cds3.tar.gz"
JM_URI_us02-kit = "http://download.exorembedded.net:8080/Public/usom02/JMobile/jmobile2.01-${PV}-${PR}_portable_alterakit_cds3.tar.gz"
JM_URI_us03-kit = "http://download.exorembedded.net:8080/Public/usom03/JMobile/jmobile2.01-${PV}-${PR}_portable_us03kit_cds3.tar.gz"

SRC_URI = "file://jmobile.desktop"

do_fetch () {
	wget ${JM_URI} -O ${WORKDIR}/jmobile_portable.tar.gz
	tar xzf ${WORKDIR}/jmobile_portable.tar.gz -C ${WORKDIR} portable/LICENSE.txt
}

S = "${WORKDIR}/portable"

do_install() {
	install -d ${D}${datadir}/applications
	install -m 0644 ${WORKDIR}/jmobile.desktop ${D}${datadir}/applications/
	install -d ${D}/home/root
	install -m 0644 ${WORKDIR}/jmobile_portable.tar.gz ${D}/home/root/
}

FILES_${PN} = "${datadir}/applications/ /home/root/jmobile_portable.tar.gz"
