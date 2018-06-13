SRC_URI += "file://0009-Enable-share-group-workaround-for-Vivante-GPUs.patch \
	file://0012-disable-sync-promo.patch \
	file://0013-disable-welcome-page.patch \
"

do_install_append() {
	sed -i'' 's:^CHROME_EXTRA_ARGS=.*$:CHROME_EXTRA_ARGS="--no-sandbox --disable-infobars --disable-gpu":' ${D}/${libdir}/chromium/chromium-wrapper
}

do_install_append_usom03() {
	sed -i'' 's:^CHROME_EXTRA_ARGS=.*$:CHROME_EXTRA_ARGS="--no-sandbox --disable-infobars --use-gl=egl --enable-zero-copy --disable-accelerated-canvas":' ${D}/${libdir}/chromium/chromium-wrapper
}
