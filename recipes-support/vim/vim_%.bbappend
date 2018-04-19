do_install_append() {
	rm ${D}${datadir}/applications/vim.desktop
	rm ${D}${datadir}/applications/gvim.desktop
}
