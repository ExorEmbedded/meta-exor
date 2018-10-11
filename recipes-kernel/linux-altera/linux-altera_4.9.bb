DESCRIPTION = "Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${KERNEL_RELEASE}:"

SRC_URI = "git://github.com/ExorEmbedded/linux-us02.git;protocol=git;branch=4.9.76-RT-LTS_us05"
SRCREV = "95c4201394d598515041869a587e5617206f58ac"

COMPATIBLE_MACHINE = "(usom05)"



include ./linux-altera.inc

PR = "r3"

PROVIDES += "linux kernel"

S = "${WORKDIR}/git"
B = "${S}"


KERNEL_RELEASE = "4.9"
