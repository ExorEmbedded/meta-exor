#
# Sato/X11 based image
#

require recipes-sato/images/core-image-sato.bb

# It's a X only image
REQUIRED_DISTRO_FEATURES = "x11"
CONFLICT_DISTRO_FEATURES = "wayland"

require core-image-exor.inc

IMAGE_INSTALL += " \
	qtx11extras \
	promovideo \
"

#IMAGE_INSTALL += "chromium-x11"

IMAGE_INSTALL_append_usom03 = "\
	xserver-xf86-config \
	${QT5_DEMO} \
"

IMAGE_NAME = "core-image-exor-x11-${MACHINE}-v${DISTRO_VERSION}"
