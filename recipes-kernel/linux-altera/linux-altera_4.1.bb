DESCRIPTION = "Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${KERNEL_RELEASE}:"

SRC_URI = "git://github.com/ExorEmbedded/linux-us02.git;protocol=git;branch=4.1-LTS_us02_etop"
SRC_URI[md5sum] = "7094df7dedb134fa41ee6679a34de190"
SRCREV = "0458005c27ba88e3c3ae32bba844fe6ca5cf0f48"

SRC_URI += "file://0001-Added-compiler-gcc6.h.patch \
            file://0002-Add-linux-compiler-gcc7.h-to-fix-builds-with-gcc7.patch \
"
#file://0003-cumulative-deipce.patc


COMPATIBLE_MACHINE = "(usom02|usom05)"

include ./linux-altera.inc

PR = "r3"

PROVIDES += "linux kernel"

S = "${WORKDIR}/git"
B = "${S}"

DTB_TARGET_usom02 = "socfpga.dtb"

KERNEL_RELEASE = "4.1"

