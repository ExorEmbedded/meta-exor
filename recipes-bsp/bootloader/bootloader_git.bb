LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;beginline=1;endline=6;md5=157ab8408beab40cd8ce1dc69f702a6c"
LIC_FILES_CHKSUM_usom02 = "file://README;beginline=1;endline=6;md5=05908ffcfd3d7d846e5c7eafa9ab62de"
LIC_FILES_CHKSUM_usom03 = "file://README;beginline=1;endline=6;md5=37495cdcff8cd78890dc65d37099bc38"
LIC_FILES_CHKSUM_usom05 = "file://README;beginline=1;endline=6;md5=05908ffcfd3d7d846e5c7eafa9ab62de"
LIC_FILES_CHKSUM_nsom02 = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR = "r2"

DEPENDS += "bc-native"
DEPENDS += "flex-native bison-native"

#uSom01
SRC_URI = "git://github.com/ExorEmbedded/uboot-us01.git;protocol=git;branch=uboot2014.04_uS01"
SRCREV = "1a08cfc39d2bf45fde23b76ca6101d94645ce2cf"

#uSom 02 & uSom05
SRC_URI_usom02 = "git://github.com/ExorEmbedded/uboot-us02.git;protocol=git;branch=master"
SRCREV_usom02  = "cab40e340784b1dbf7c5a0f72697f0c1ae066d42"

#uSom03
SRC_URI = "git://github.com/ExorEmbedded/uboot-us01.git;protocol=git;branch=2020.04+fslc"
SRCREV = "29bc7cb2dcc8c4cb891b29d2ae33a1a2c8ec6b7d"

SRC_URI_usom05 = "git://github.com/ExorEmbedded/uboot-us02.git;protocol=git;branch=us02_etop"
SRCREV_usom05  = "8f3c576ae66fad63669704bd88e9111babd6c39b"

#nSom01
SRC_URI_nsom01 = "git://github.com/ExorEmbedded/uboot-us01.git;protocol=git;branch=uboot2017.07_nS01"
SRCREV_nsom01  = "02152ce87f5160d7976883fcba1973d2bbe46a2d"

#nSom02
SRC_URI_nsom02 = "git://github.com/ExorEmbedded/uboot-stm.git;protocol=git;branch=stm32_v2018.11"
SRCREV_nsom02  = "4e2260f34ab957f3cb43a16afaed057a70ab7c41"

GCC7_PATCH = " file://0001-Add-linux-compiler-gcc5.h-to-fix-builds-with-gcc5.patch \
               file://0002-Add-linux-compiler-gcc6.h-to-fix-builds-with-gcc6.patch \
               file://0003-gcc5-use_gcc_inline_version_instead_c99.patch \
               file://0004-Add-linux-compiler-gcc7.h-to-fix-builds-with-gcc7.patch \
"

GCC7_PATCH_append_usom02 = "file://0005-gcc7-fix-definition.patch"
GCC7_PATCH_append_usom05 = "file://0005-gcc7-fix-definition.patch"

SRC_URI_append_usom01 = "${GCC7_PATCH}"
SRC_URI_append_usom02 = "${GCC7_PATCH}"
SRC_URI_append_usom05 = "${GCC7_PATCH}"

UBOOT_SUFFIX_usom03 = "imx"
UBOOT_SUFFIX_nsom01 = "imx"

require ../u-boot.inc

EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                 HOSTSTRIP=true'

PACKAGE_ARCH = "${MACHINE_ARCH}"

UBOOT_ONLY = "1"
