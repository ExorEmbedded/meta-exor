FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

GLES_EXTRA_DEPS = "libdrm ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)}"

PACKAGECONFIG[gles2] = "-opengl es2 -eglfs,,virtual/libgles2 virtual/egl ${GLES_EXTRA_DEPS}"

QT_CONFIG_FLAGS_append_usom01 = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '-qpa wayland', '-qpa xcb', d)}"

QT_EGLFS_PATCHES = "\
    file://0001-calculator-Add-exit-button-for-non-window-environmen.patch \
    file://quit.png \
"

SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', "${QT_EGLFS_PATCHES}", d)}\
    file://0001-deform-Fix-how-controls-are-shown.patch \
"

SRC_URI_append_usom03 = "file://us03-fix-EGL-include-order.patch"

PACKAGECONFIG_DISTRO += "accessibility fontconfig freetype libinput gif ico sql-sqlite"

PACKAGECONFIG_GL = "gles2"

python do_patch_append() {
    import shutil

    work_dir = d.getVar("WORKDIR", True)
    s = d.getVar("S", True)

    if not bb.utils.contains('DISTRO_FEATURES','wayland',True,False,d):
        shutil.copy(os.path.join(work_dir,"quit.png"),os.path.join(s,"examples/widgets/animation/animatedtiles/images/"))
}
