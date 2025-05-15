SUMMARY = "Crypto Multi-buffer Library"
DESCRIPTION = "Intel® Integrated Performance Primitives (Intel® IPP) Cryptography \
is a secure, fast and lightweight library of building blocks for cryptography, \
highly-optimized for various Intel® CPUs."
HOMEPAGE = "https://github.com/intel/cryptography-primitives"

LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://../../../LICENSE;md5=d94a5b4dbbc5c6a0c2ce95ab337df6c4"

IPP_BRANCH = "cryptography-primitives_${@'_'.join(d.getVar('PV').rsplit('.')[-3:])}"

SRC_URI = "git://github.com/intel/cryptography-primitives;protocol=https;branch=${IPP_BRANCH} \
           file://0001-CMakeLists.txt-exclude-host-system-headers.patch;striplevel=4 \
           file://0002-cmake-exclude-Yocto-build-flags.patch;striplevel=4 \
           file://0003-crypto-mb-Make-sure-libs-are-installed-correctly.patch;striplevel=4 \
           "
SRCREV = "5a77835c95768d1099c38568291e714c9d9f86ce"

S = "${WORKDIR}/git/sources/ippcp/crypto_mb"

DEPENDS = "openssl"

inherit cmake pkgconfig
COMPATIBLE_HOST = '(x86_64).*-linux'

EXTRA_OECMAKE += " -DARCH=intel64"
EXTRA_OECMAKE += " -DTOOLCHAIN_OPTIONS='${TOOLCHAIN_OPTIONS}'"

do_install:append () {
        rm -rf ${D}${exec_prefix}/tools/
}

FILES:${PN} += "${libdir}/"

# QA Issue: non -dev/-dbg/nativesdk- package intel-crypto-mb contains
# symlink .so '/usr/lib/libcrypto_mb.so' [dev-so]
INSANE_SKIP:${PN} += "dev-so"
