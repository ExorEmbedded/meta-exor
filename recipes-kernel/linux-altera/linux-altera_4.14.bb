DESCRIPTION = "Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI = "git://github.com/ExorEmbedded/linux-us02.git;protocol=git;branch=socfpga-4.14.126-ltsi-rt"
SRCREV = "d5c7a154687d521c3bd0336cdf869aa8422c1c9e"

COMPATIBLE_MACHINE = "(usom02)"

require linux-altera.inc

PR = "r3"

PROVIDES += "linux kernel"

S = "${WORKDIR}/git"
B = "${S}"

DTB_TARGET = "socfpga.dtb"

KERNEL_RELEASE = "4.14"
