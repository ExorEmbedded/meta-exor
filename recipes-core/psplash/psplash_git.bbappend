FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://psplash-bar-img.h"

BAR_IMAGE = "file://psplash-bar-img.h"

python do_compile () {
    import shutil
    barimage = d.getVar('BAR_IMAGE', True)
    if barimage:
        fetcher = bb.fetch2.Fetch([barimage], d)
        flocal = fetcher.localpath(barimage)
        shutil.copyfile(flocal, "%s/psplash-bar-img.h" % d.getVar('S', True))
    # Build a separate executable for each splash image
    workdir = d.getVar('WORKDIR', True)
    convertscript = "%s/make-image-header.sh" % d.getVar('S', True)
    destfile = "%s/psplash-poky-img.h" % d.getVar('S', True)
    localfiles = d.getVar('SPLASH_LOCALPATHS', True).split()
    outputfiles = d.getVar('SPLASH_INSTALL', True).split()
    for localfile, outputfile in zip(localfiles, outputfiles):
        if localfile.endswith(".png"):
            outp = oe.utils.getstatusoutput('%s %s POKY' % (convertscript, os.path.join(workdir, localfile)))
            print(outp[1])
            fbase = os.path.splitext(localfile)[0]
            shutil.copyfile("%s-img.h" % fbase, destfile)
        else:
            shutil.copyfile(os.path.join(workdir, localfile), destfile)
        # For some reason just updating the header is not enough, we have to touch the .c
        # file in order to get it to rebuild
        os.utime("%s/psplash.c" % d.getVar('S', True), None)
        bb.build.exec_func("oe_runmake", d)
        shutil.copyfile("psplash", outputfile)
}

