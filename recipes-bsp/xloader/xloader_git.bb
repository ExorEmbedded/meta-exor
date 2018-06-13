LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;beginline=1;endline=6;md5=157ab8408beab40cd8ce1dc69f702a6c"

SRC_URI = "git://github.com/ExorEmbedded/uboot-us01.git;protocol=git;branch=uboot2014.04_uS01"
SRCREV = "${AUTOREV}"

GCC7_PATCH = " file://0001-Add-linux-compiler-gcc5.h-to-fix-builds-with-gcc5.patch \
	file://0002-Add-linux-compiler-gcc6.h-to-fix-builds-with-gcc6.patch \
	file://0003-gcc5-use_gcc_inline_version_instead_c99.patch \
	file://0004-Add-linux-compiler-gcc7.h-to-fix-builds-with-gcc7.patch \
"

SRC_URI_append_usom01 = "${GCC7_PATCH}"

COMPATIBLE_MACHINE = "usom01"

require ../u-boot.inc

SPL_ONLY = "1"
SPL_BINARY = "MLO"
