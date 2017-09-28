DESCRIPTION = "Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${KERNEL_RELEASE}:"

SRC_URI = "git://github.com/ExorEmbedded/linux-us02.git;protocol=git;branch=4.1-LTS_us02_etop"
SRC_URI[md5sum] = "7094df7dedb134fa41ee6679a34de190"
SRCREV = "fd938fe480e311217e61ded24b6b138f64028f18"

SRC_URI += "file://0001-Added-compiler-gcc6.h.patch"

COMPATIBLE_MACHINE = "(usom02)"

include ./linux-altera.inc

PR = "r2"

PROVIDES += "linux kernel"

S = "${WORKDIR}/git"
B = "${S}"

DTB_TARGET = "socfpga.dtb"

KERNEL_RELEASE = "4.1"

#INSANE_SKIP_${PN} += "installed-vs-shipped" 
