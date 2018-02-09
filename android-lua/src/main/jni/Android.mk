LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
SRC :=./lua-5.3.4/src
LOCAL_MODULE    := lua
LOCAL_SRC_FILES := lua-5.3.4/src/lapi.c lua-5.3.4/src/lauxlib.c lua-5.3.4/src/lbaselib.c lua-5.3.4/src/lbitlib.c lua-5.3.4/src/lcode.c lua-5.3.4/src/lcorolib.c lua-5.3.4/src/lctype.c lua-5.3.4/src/ldblib.c lua-5.3.4/src/ldebug.c lua-5.3.4/src/ldo.c lua-5.3.4/src/ldump.c lua-5.3.4/src/lfunc.c lua-5.3.4/src/lgc.c lua-5.3.4/src/linit.c lua-5.3.4/src/liolib.c lua-5.3.4/src/llex.c lua-5.3.4/src/lmathlib.c lua-5.3.4/src/lmem.c lua-5.3.4/src/loadlib.c lua-5.3.4/src/lobject.c lua-5.3.4/src/lopcodes.c lua-5.3.4/src/loslib.c lua-5.3.4/src/lparser.c lua-5.3.4/src/lstate.c lua-5.3.4/src/lstring.c lua-5.3.4/src/lstrlib.c lua-5.3.4/src/ltable.c lua-5.3.4/src/ltablib.c lua-5.3.4/src/ltm.c lua-5.3.4/src/lundump.c lua-5.3.4/src/lutf8lib.c lua-5.3.4/src/lvm.c lua-5.3.4/src/lzio.c

LOCAL_CFLAGS += -DLUA_DL_DLOPEN -DLUA_USE_C89 -DLUA_COMPAT_5_1 -DLUA_COMPAT_5_2 -DLUA_USE_LINUX

LOCAL_LDLIBS += -L$(SYSROOT)/usr/lib -llog -ldl
LOCAL_CFLAGS += -pie -fPIE
LOCAL_LDFLAGS += -pie -fPIE

LOCAL_LDLIBS    := -ld -lm

include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/lua-5.3.4/src
LOCAL_MODULE     := luajava
LOCAL_SRC_FILES  := luajava/luajava.c
LOCAL_STATIC_LIBRARIES := liblua

LOCAL_LDLIBS := -llog

LOCAL_CFLAGS += -DLUA_DL_DLOPEN -DLUA_USE_C89 -DLUA_COMPAT_5_1 -DLUA_COMPAT_5_2 -DLUA_USE_LINUX

include $(BUILD_SHARED_LIBRARY)