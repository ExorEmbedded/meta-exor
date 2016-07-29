#
# Sato/X11 based image
#

require recipes-sato/images/core-image-sato.bb

# It's a X only image
REQUIRED_DISTRO_FEATURES = "x11"
CONFLICT_DISTRO_FEATURES = "wayland"

require core-image-exor.inc

IMAGE_INSTALL += " qtx11extras"
IMAGE_INSTALL_append_usom03 = "\
	xserver-xf86-config \
	xf86-video-fbdev \
	${QT5_GRAPHICS} \
"

export IMAGE_BASENAME = "core-image-exor-x11"
