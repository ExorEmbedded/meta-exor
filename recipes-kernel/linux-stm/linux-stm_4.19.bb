DESCRIPTION = "Linux STM32MP Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRCNAME = "stm32_linux"
SRCBRANCH = "4.19"
SRCREV = "ab436bb0bd18dcd37a0f3af25915980ea04b4d77"

SRC_URI = "git://github.com/ExorEmbedded/linux-stm.git;branch=${SRCBRANCH}"
S = "${WORKDIR}/git"

LINUX_VERSION = "4.19.49"

COMPATIBLE_MACHINE = "(nsom02)"

require ../linux-exor.inc
