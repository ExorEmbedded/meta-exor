LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;beginline=1;endline=6;md5=157ab8408beab40cd8ce1dc69f702a6c"
LIC_FILES_CHKSUM_usom02 = "file://README;beginline=1;endline=6;md5=05908ffcfd3d7d846e5c7eafa9ab62de"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

#uSom01 & uSom03
SRC_URI = "git://github.com/ExorEmbedded/uboot-us01.git;protocol=git;branch=uboot2014.04_uS01"
SRCREV = "e7340191ba8c56f8c8733cc217cacc86030ac81a"

SRC_URI_usom02 = "git://github.com/ExorEmbedded/uboot-us02.git;protocol=git;branch=master"
SRCREV_usom02  = "cab40e340784b1dbf7c5a0f72697f0c1ae066d42"

SRC_URI_nsom01 = "git://github.com/ExorEmbedded/uboot-us01.git;protocol=git;branch=uboot2017.07_nS01"
SRCREV_nsom01  = "2f9c50fd376baef0863db502894bc0d86e7e0e55"

GCC6_PATCH = " file://0001-Add-linux-compiler-gcc5.h-to-fix-builds-with-gcc5.patch \
		   file://0002-Add-linux-compiler-gcc6.h-to-fix-builds-with-gcc6.patch \
		   file://0003-gcc5-use_gcc_inline_version_instead_c99.patch \
"

SRC_URI_append_usom01 = "${GCC6_PATCH}"
SRC_URI_append_usom02 = "${GCC6_PATCH}"
SRC_URI_append_usom03 = "${GCC6_PATCH}"

UBOOT_SUFFIX_usom03 = "imx"
UBOOT_SUFFIX_nsom01 = "imx"

require ../u-boot.inc

EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                 HOSTSTRIP=true'

PACKAGE_ARCH = "${MACHINE_ARCH}"

UBOOT_ONLY = "1"
