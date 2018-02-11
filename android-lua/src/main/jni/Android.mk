LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LUA_SRC :=$(LOCAL_PATH)/lua-5.3.4/src
LOCAL_MODULE    := lua
LOCAL_SRC_FILES := $(LUA_SRC)/lapi.c $(LUA_SRC)/lauxlib.c $(LUA_SRC)/lbaselib.c $(LUA_SRC)/lbitlib.c $(LUA_SRC)/lcode.c $(LUA_SRC)/lcorolib.c $(LUA_SRC)/lctype.c $(LUA_SRC)/ldblib.c $(LUA_SRC)/ldebug.c $(LUA_SRC)/ldo.c $(LUA_SRC)/ldump.c $(LUA_SRC)/lfunc.c $(LUA_SRC)/lgc.c $(LUA_SRC)/linit.c $(LUA_SRC)/liolib.c $(LUA_SRC)/llex.c $(LUA_SRC)/lmathlib.c $(LUA_SRC)/lmem.c $(LUA_SRC)/loadlib.c $(LUA_SRC)/lobject.c $(LUA_SRC)/lopcodes.c $(LUA_SRC)/loslib.c $(LUA_SRC)/lparser.c $(LUA_SRC)/lstate.c $(LUA_SRC)/lstring.c $(LUA_SRC)/lstrlib.c $(LUA_SRC)/ltable.c $(LUA_SRC)/ltablib.c $(LUA_SRC)/ltm.c $(LUA_SRC)/lundump.c $(LUA_SRC)/lutf8lib.c $(LUA_SRC)/lvm.c $(LUA_SRC)/lzio.c
#LOCAL_SRC_FILES := $(wildcard $(LUA_SRC)/*.c)
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