DESCRIPTION = "Linux STM32MP Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRCNAME = "stm32_linux"
SRCBRANCH = "4.19"
SRCREV = "729e0ee442c6acd0d9e76a8bd4068d4d1e13a123"

SRC_URI = "git://github.com/ExorEmbedded/linux-stm.git;branch=${SRCBRANCH}"
S = "${WORKDIR}/git"

PROVIDES += "linux kernel"

KERNEL_RELEASE = "4.19"
LINUX_VERSION = "4.19"

COMPATIBLE_MACHINE = "(nsom02)"

require linux-stm.inc

