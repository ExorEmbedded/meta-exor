#
# Weston/Wayland based image
#

require recipes-graphics/images/core-image-weston.bb
require core-image-exor.inc

REQUIRED_DISTRO_FEATURES = "wayland"

IMAGE_NAME = "core-image-exor-wayland-${MACHINE}-v${DISTRO_VERSION}"
