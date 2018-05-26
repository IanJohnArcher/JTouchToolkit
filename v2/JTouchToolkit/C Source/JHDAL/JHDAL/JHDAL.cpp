// This is the main DLL file.

#include "stdafx.h"

#include <jni.h>
#include "JHDAL.h"
#include <stdio.h>
#include <hdl/hdl.h>

typedef struct
{
    jobject obj;
    jobjectArray data;
}HDALcallbackObj;

JavaVM *cached_jvm;
HDALcallbackObj callobj[10000];
int callobjSize = 10000;
int callobjPt = 0;


/*
 * Caches the Virtual Machine ready for callbacks to enviroment
 */
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved)
{
    cached_jvm = jvm;
    return JNI_VERSION_1_4;
}

HDLServoOpExitCode callbackOp(void* pUserData) {
	HDALcallbackObj* object = (HDALcallbackObj *)pUserData;

	if(object != NULL) {
		JNIEnv *ep;// = JNU_GetEnv();
        cached_jvm->AttachCurrentThread((void **)&ep, NULL);

        jclass c = ep->GetObjectClass(object[0].obj);
        jmethodID con = ep->GetMethodID(c, "HDLServoOp", "([Ljava/lang/Object;)I");
		jint param = ep->CallIntMethod(object[0].obj, con, object[0].data);

        cached_jvm->DetachCurrentThread();

        return param;
	}
	return HDL_SERVOOP_EXIT;
}

void sendException(char *message, int error, JNIEnv *eparam)
{
    eparam->ExceptionClear();
    jclass excCls = eparam->FindClass("com/bcu/userlab/haptics/HDAL/HDLException");
    jmethodID con = eparam->GetMethodID(excCls, "<init>", "(Ljava/lang/String;I)V");

    jstring mess = eparam->NewStringUTF(message);
    jint errorCode = error;

    jobject hdExc = eparam->NewObject(excCls, con, mess, errorCode);
    eparam->Throw((jthrowable)hdExc);
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlCountDevices
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlCountDevices
(JNIEnv *eparam, jclass cparam) {
	return hdlCountDevices();;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlCreateServoOp
 * Signature: (Lcom/bcu/userlab/haptics/HDAL/HDLCallback;[Ljava/lang/Object;Z)I
 */
JNIEXPORT jint JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlCreateServoOp
(JNIEnv *eparam, jclass cparam, jobject callback, jobjectArray pUserData, jboolean bBlocking) {
	int hServoOp;

	HDALcallbackObj callObject;
        callObject.obj = eparam->NewGlobalRef(callback);
        callObject.data = (jobjectArray)eparam->NewGlobalRef(pUserData);

	if(callobjPt < callobjSize)
    {
        callobj[callobjPt] = callObject;

		hServoOp = hdlCreateServoOp(callbackOp, &callobj[callobjPt], bBlocking); 
		callobjPt++;
	}

	return hServoOp;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlDestroyServoOp
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlDestroyServoOp
(JNIEnv *eparam, jclass cparam, jint hServoOp) {
	hdlDestroyServoOp(hServoOp);
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlDeviceModel
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlDeviceModel
(JNIEnv *eparam, jclass cparam) {
	return eparam->NewStringUTF(hdlDeviceModel());
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlDeviceWorkspace
 * Signature: ()[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlDeviceWorkspace
(JNIEnv *eparam, jclass cparam){
	double workspace[6];
	hdlDeviceWorkspace(workspace);

	jdoubleArray result = eparam->NewDoubleArray(6);
    eparam->SetDoubleArrayRegion(result, 0, 6, workspace);

	HDLError error = hdlGetError();
	if(error != HDL_NO_ERROR) {
		sendException("Exception in  hdlDeviceWorkspace", error, eparam);
	}

	return result;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlGetState
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlGetState
(JNIEnv *eparam, jclass cparam) {
	int state = hdlGetState();

	HDLError error = hdlGetError();
	if(error != HDL_NO_ERROR) {
		sendException("Exception in  hdlGetState", error, eparam);
	}

	return state;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlGetVersion
 * Signature: (I)Lcom/bcu/userlab/haptics/HDAL/HDLVersionInfo;
 */
JNIEXPORT jobject JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlGetVersion
(JNIEnv *eparam, jclass cparam, jint requestType) {
	HDL_VERSION_INFO_TYPE versionInfo;
	HDL_VERSION_REQUEST rT = (HDL_VERSION_REQUEST)requestType;
	hdlGetVersion(rT, &versionInfo);

	int v1 = HDL_BUILD_VERSION(versionInfo);
	int v2 = HDL_MAJOR_VERSION(versionInfo);
	int v3 = HDL_MINOR_VERSION(versionInfo);

	jclass verCls = eparam->FindClass("com/bcu/userlab/haptics/HDAL/HDLVersionInfo");
    jmethodID con = eparam->GetMethodID(verCls, "<init>", "(III)V");
	jobject hdlVers = eparam->NewObject(verCls, con, v1, v2, v3);

	return hdlVers;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlInitDevice
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlInitDevice
(JNIEnv *eparam, jclass cparam, jint deviceID) {
	int dHandler = hdlInitDevice(deviceID);
	
	HDLError error = hdlGetError();
	if(error != HDL_NO_ERROR) {
		sendException("Exception in  hdlInitDevice", error, eparam);
	}

	return dHandler;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlInitIndexedDevice
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlInitIndexedDevice
(JNIEnv *eparam, jclass cparam, jint index) {
	int dHandler = hdlInitIndexedDevice(index);
	
	HDLError error = hdlGetError();
	if(error != HDL_NO_ERROR) {
		sendException("Exception in  hdlInitIndexedDevice", error, eparam);
	}

	return dHandler;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlInitNamedDevice
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlInitNamedDevice
(JNIEnv *eparam, jclass cparam, jstring deviceName) {
	int dHandler = hdlInitNamedDevice(eparam->GetStringUTFChars(deviceName, 0 ));
	
	HDLError error = hdlGetError();
	if(error != HDL_NO_ERROR) {
		sendException("Exception in  hdlInitNamedDevice", error, eparam);
	}

	return dHandler;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlMakeCurrent
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlMakeCurrent
(JNIEnv *eparam, jclass cparam, jint hHandle) {
	hdlMakeCurrent(hHandle);
	
	HDLError error = hdlGetError();
	if(error != HDL_NO_ERROR) {
		sendException("Exception in  hdlMakeCurrent", error, eparam);
	}
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlSetToolForce
 * Signature: ([D)V
 */
JNIEXPORT void JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlSetToolForce
(JNIEnv *eparam, jclass cparam, jdoubleArray force) {
	jdouble *darray = eparam->GetDoubleArrayElements(force, 0);
	hdlSetToolForce(darray);
	
	HDLError error = hdlGetError();
	if(error != HDL_NO_ERROR) {
		sendException("Exception in  hdlSetToolForce", error, eparam);
	}
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlStart
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlStart
(JNIEnv *eparam, jclass cparam) {
	hdlStart();
	
	HDLError error = hdlGetError();
	if(error != HDL_NO_ERROR) {
		sendException("Exception in  hdlStart", error, eparam);
	}
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlStop
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlStop
(JNIEnv *eparam, jclass cparam) {
	hdlStop();
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlToolButton
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlToolButton
(JNIEnv *eparam, jclass cparam) {
	bool b;
	hdlToolButton(&b);
	return (jboolean)b;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlToolButtons
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlToolButtons
(JNIEnv *eparam, jclass cparam) {
	int but;
	hdlToolButtons(&but);
	return (jint)but;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlToolPosition
 * Signature: ()[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlToolPosition
(JNIEnv *eparam, jclass cparam) {
	double pos[3];
	hdlToolPosition(pos);

	jdoubleArray result = eparam->NewDoubleArray(3);
    eparam->SetDoubleArrayRegion(result, 0, 3, pos);

	return result;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDAL
 * Method:    hdlUninitDevice
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_bcu_userlab_haptics_HDAL_HDAL_hdlUninitDevice
(JNIEnv *eparam, jclass cparam, jint hHandle) {
	hdlUninitDevice(hHandle);
	
	HDLError error = hdlGetError();
	if(error != HDL_NO_ERROR) {
		sendException("Exception in  hdlUninitDevice", error, eparam);
	}
}