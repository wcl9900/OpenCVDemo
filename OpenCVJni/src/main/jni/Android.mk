LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := opencv_java
LOCAL_SRC_FILES := libopencv_java.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
OPENCV_LIB_TYPE:=STATIC

ifeq ("$(wildcard $(OPENCV_MK_PATH))","")
include D:\Work\AndroidSDK\OpenCV-2.4.11-android-sdk\OpenCV-android-sdk\sdk\native\jni\OpenCV.mk
else
include $(OPENCV_MK_PATH)
endif

LOCAL_MODULE := myopencv
LOCAL_SRC_FILES := MyOpenCV.cpp
LOCAL_LDLIBS += -lm -llog -latomic
#LOCAL_SHARED_LIBRARIES := opencv_java
include $(BUILD_SHARED_LIBRARY)
