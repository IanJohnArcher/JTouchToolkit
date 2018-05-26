#include <HL\hl.h>
#include <HD\hd.h>
//#include <HL\hlDefines.h>
#include <HDU/hduVector.h>
#include <jni.h>
//#include "com_uce_userlab_haptics_HL_HLAPI.h"
#include <stdio.h>


#ifndef _Included_com_uce_userlab_haptics_HL_HLAPI
#define _Included_com_uce_userlab_haptics_HL_HLAPI
#ifdef __cplusplus
extern "C" {
#endif

/* Declared types for management */
HHLRC arrayHHLRC[10000];
int arraySize = 10000;
int arrayItem = 0;

HLgeom *tmpFeatureStore = NULL;

typedef struct
{
    jobject obj;
    jobjectArray data;
} CallObject;

typedef struct
{
    jobject obj;
    jobjectArray data;
    jint shape;
    jint thread;
} EventCallbackObject;

CallObject callobj[10000];
int callobjSize = 10000;
int callobjPt = 0;

EventCallbackObject eventcallobj[10000];
int eventcallobjSize = 10000;
int eventcallobjPt = 0;

JavaVM *cached_jvm;

/*
 * Caches the Virtual Machine ready for callbacks to enviroment
 */
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved)
{
    cached_jvm = jvm;
    return JNI_VERSION_1_4;
}
/*
 * Create Java Cache Object based on C *cache
 */
jobject getCacheObject(HLcache *cache, JNIEnv *eparam)
{
    (*eparam)->ExceptionClear(eparam);
    jclass excCls = (*eparam)->FindClass(eparam, "com/uce/userlab/haptics/HL/HLCache");
    jmethodID con = (*eparam)->GetMethodID(eparam, excCls, "<init>", "(ZZZ[D[D[D[D[D[D[D[D[D)V");

    HDdouble p5[3];
    hlCacheGetDoublev(cache, HL_PROXY_POSITION, p5);
    HDdouble p6[4];
    hlCacheGetDoublev(cache, HL_PROXY_ROTATION, p6);
    HDdouble p7[16];
    hlCacheGetDoublev(cache, HL_PROXY_TRANSFORM, p7);


    jboolean param1 = JNI_FALSE;
    jboolean param2 = JNI_FALSE;
    jboolean param3 = JNI_FALSE;

    jdoubleArray param4 = (*eparam)->NewDoubleArray(eparam, 3);
    jdoubleArray param5 = (*eparam)->NewDoubleArray(eparam, 3);
    jdoubleArray param6 = (*eparam)->NewDoubleArray(eparam, 4);
    jdoubleArray param7 = (*eparam)->NewDoubleArray(eparam, 16);
    jdoubleArray param8 = (*eparam)->NewDoubleArray(eparam, 3);
    jdoubleArray param9 = (*eparam)->NewDoubleArray(eparam, 4);
    jdoubleArray param10 = (*eparam)->NewDoubleArray(eparam, 16);
    jdoubleArray param11 = (*eparam)->NewDoubleArray(eparam, 3);
    jdoubleArray param12 = (*eparam)->NewDoubleArray(eparam, 3);

    (*eparam)->SetDoubleArrayRegion(eparam, param5, 0, 3, p5);
    (*eparam)->SetDoubleArrayRegion(eparam, param6, 0, 4, p6);
    (*eparam)->SetDoubleArrayRegion(eparam, param7, 0, 16, p7);

    jobject c = (*eparam)->NewObject(eparam, excCls, con, param1, param2, param3,
                                        param4, param5, param6, param7,
                                        param8, param9, param10, param11, param12);
    return c;
}
/*
 * Callback function for intersectCallbacks from HL to Java
 */
HLboolean HLCALLBACK intersectCallback(const HLdouble startPt[3], const HLdouble endPt[3],
                                    HLdouble intersectionPt[3], HLdouble intersectionNormal[3],
                                    HLenum *face, void *userdata)
{
    CallObject *ob = (CallObject *)userdata;
    jobject obje = ob[0].obj;
    jobjectArray dat = ob[0].data;

    if(obje != NULL)
    {
        JNIEnv *eparam;
        (*cached_jvm)->AttachCurrentThread(cached_jvm, (void **)&eparam, NULL);

        jdoubleArray para1 = (*eparam)->NewDoubleArray(eparam, 3);
        jdoubleArray para2 = (*eparam)->NewDoubleArray(eparam, 3);
        jdoubleArray para3 = (*eparam)->NewDoubleArray(eparam, 3);
        jdoubleArray para4 = (*eparam)->NewDoubleArray(eparam, 3);

        jint f = (jint)getJParameter(face);

        (*eparam)->SetDoubleArrayRegion(eparam, para1, 0, 3, (jdouble *)startPt);
        (*eparam)->SetDoubleArrayRegion(eparam, para2, 0, 3, (jdouble *)endPt);
        (*eparam)->SetDoubleArrayRegion(eparam, para3, 0, 3, (jdouble *)intersectionPt);
        (*eparam)->SetDoubleArrayRegion(eparam, para4, 0, 3, (jdouble *)intersectionNormal);

        jclass c = (*eparam)->GetObjectClass(eparam, obje);
        jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_CALLBACK_INTERSECT_LS", "([D[D[D[DI[Ljava/lang/Object;)Z");
        jboolean param = (*eparam)->CallBooleanMethod(eparam, obje, con, para1, para2, para3, para4, f, dat);

        (*cached_jvm)->DetachCurrentThread(cached_jvm);

        return param;
    }

    return JNI_FALSE;
}
/*
 * Callback function for closestFeatureCallbacks from HL to Java
 */
HLboolean HLCALLBACK closestFeatureCallback(const HLdouble queryPt[3], const HLdouble targetPt[3],
                                    HLgeom *geom, HLdouble closestPt[3], void *userdata)
{
    CallObject *ob = (CallObject *)userdata;
    jobject obje = ob[0].obj;
    jobjectArray dat = ob[0].data;

    if(obje != NULL)
    {
		JNIEnv *eparam;
        (*cached_jvm)->AttachCurrentThread(cached_jvm, (void **)&eparam, NULL);

        jdoubleArray para1 = (*eparam)->NewDoubleArray(eparam, 3);
        jdoubleArray para2 = (*eparam)->NewDoubleArray(eparam, 3);
        jdoubleArray para4 = (*eparam)->NewDoubleArray(eparam, 3);

        (*eparam)->SetDoubleArrayRegion(eparam, para1, 0, 3, (jdouble *)queryPt);
        (*eparam)->SetDoubleArrayRegion(eparam, para2, 0, 3, (jdouble *)targetPt);
        (*eparam)->SetDoubleArrayRegion(eparam, para4, 0, 3, (jdouble *)closestPt);

        jclass c = (*eparam)->GetObjectClass(eparam, obje);
        jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_CALLBACK_CLOSEST_FEATURE", "([D[D[D[Ljava/lang/Object;)Z");
        jboolean param = (*eparam)->CallBooleanMethod(eparam, obje, con, para1, para2, para4, dat);

        (*cached_jvm)->DetachCurrentThread(cached_jvm);

        return param;
    }

    return JNI_FALSE;
}
/*
 * Callback function for computeForceCallbacks from HL to Java
 */
void HLCALLBACK computeForceCallback(HLdouble force[3], HLcache *cache, void *userdata)
{
    CallObject *ob = (CallObject *)userdata;
    jobject obje = ob[0].obj;
    jobjectArray dat = ob[0].data;

    if(obje != NULL)
    {
        JNIEnv *eparam;
        (*cached_jvm)->AttachCurrentThread(cached_jvm, (void **)&eparam, NULL);

        jdoubleArray para1 = (*eparam)->NewDoubleArray(eparam, 3);

        (*eparam)->SetDoubleArrayRegion(eparam, para1, 0, 3, (jdouble *)force);

        jobject ca = getCacheObject(cache, eparam);

        jclass c = (*eparam)->GetObjectClass(eparam, obje);
        jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_CALLBACK_COMPUTE_FORCE",
                                        "([DLcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
        (*eparam)->CallVoidMethod(eparam, obje, con, para1, ca, dat);

        (*cached_jvm)->DetachCurrentThread(cached_jvm);
    }
}
/*
 * Callback function for startForceCallbacks from HL to Java
 */
void HLCALLBACK startForceCallback(HLcache *cache, void *userdata)
{
    CallObject *ob = (CallObject *)userdata;
    jobject obje = ob[0].obj;
    jobjectArray dat = ob[0].data;

    if(obje != NULL)
    {
        JNIEnv *eparam;
        (*cached_jvm)->AttachCurrentThread(cached_jvm, (void **)&eparam, NULL);

        jdoubleArray para1 = (*eparam)->NewDoubleArray(eparam, 3);

        jobject ca = getCacheObject(cache, eparam);

        jclass c = (*eparam)->GetObjectClass(eparam, obje);
        jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_CALLBACK_COMPUTE_FORCE",
                                        "(Lcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
        (*eparam)->CallVoidMethod(eparam, obje, con, ca, dat);

        (*cached_jvm)->DetachCurrentThread(cached_jvm);
    }
}
/*
 * Callback function for stopForceCallbacks from HL to Java
 */
void HLCALLBACK stopForceCallback(HLcache *cache, void *userdata)
{
    CallObject *ob = (CallObject *)userdata;
    jobject obje = ob[0].obj;
    jobjectArray dat = ob[0].data;

    if(obje != NULL)
    {
        JNIEnv *eparam;
        (*cached_jvm)->AttachCurrentThread(cached_jvm, (void **)&eparam, NULL);

        jdoubleArray para1 = (*eparam)->NewDoubleArray(eparam, 3);

        jobject ca = getCacheObject(cache, eparam);

        jclass c = (*eparam)->GetObjectClass(eparam, obje);
        jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_CALLBACK_COMPUTE_FORCE",
                                        "(Lcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
        (*eparam)->CallVoidMethod(eparam, obje, con, ca, dat);

        (*cached_jvm)->DetachCurrentThread(cached_jvm);
    }
}
/*
 * Callback function for HL Callback events from HL to Java
 */
void HLCALLBACK eventCallback(HLenum event, HLuint object, HLenum thread,
                                HLcache *cache, void *userdata)
{
    EventCallbackObject *ob = (EventCallbackObject *)userdata;
    jobject obje = ob[0].obj;
    jobjectArray dat = ob[0].data;

    HLboolean p1;
    hlCacheGetBooleanv(cache, HL_BUTTON1_STATE, &p1);

    if(obje != NULL)
    {
        JNIEnv *eparam;
        (*cached_jvm)->AttachCurrentThread(cached_jvm, (void **)&eparam, NULL);

        jclass c = (*eparam)->GetObjectClass(eparam, obje);

        //jint e = getJParameter(event);
        jint t = getJParameter(thread);
        jobject cac = getCacheObject(cache, eparam);

        if(event == HL_EVENT_MOTION)
        {
            jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_EVENT_MOTION",
                                        "(IILcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
            (*eparam)->CallVoidMethod(eparam, obje, con, object, t, cac, dat);
        }
        else if(event == HL_EVENT_1BUTTONDOWN)
        {
            jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_EVENT_1BUTTONDOWN",
                                        "(IILcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
            (*eparam)->CallVoidMethod(eparam, obje, con, object, t, cac, dat);
        }
        else if(event == HL_EVENT_1BUTTONUP)
        {
            jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_EVENT_1BUTTONUP",
                                        "(IILcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
            (*eparam)->CallVoidMethod(eparam, obje, con, object, t, cac, dat);
        }
        else if(event == HL_EVENT_2BUTTONDOWN)
        {
            jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_EVENT_2BUTTONDOWN",
                                        "(IILcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
            (*eparam)->CallVoidMethod(eparam, obje, con, object, t, cac, dat);
        }
        else if(event == HL_EVENT_2BUTTONUP)
        {
            jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_EVENT_2BUTTONUP",
                                        "(IILcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
            (*eparam)->CallVoidMethod(eparam, obje, con, object, t, cac, dat);
        }
        else if(event == HL_EVENT_TOUCH)
        {
            jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_EVENT_TOUCH",
                                        "(IILcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
            (*eparam)->CallVoidMethod(eparam, obje, con, object, t, cac, dat);
        }
        else if(event == HL_EVENT_UNTOUCH)
        {
            jmethodID con = (*eparam)->GetMethodID(eparam, c, "HL_EVENT_UNTOUCH",
                                        "(IILcom/uce/userlab/haptics/HL/HLCache;[Ljava/lang/Object;)V");
            (*eparam)->CallVoidMethod(eparam, obje, con, object, t, cac, dat);
        }
        else
        {
            //printf("Unknown Event");
        }

        (*cached_jvm)->DetachCurrentThread(cached_jvm);
    }
    //else
        //printf("NOOOOOOOOOOOOOOOOOOOOOOOO\n");
}

/*
 * returns the length the array must be for methods
 */
jint getJArrayLength(int param)
{
    switch (param)
    {
        case 65:  return 1;
        case 66:  return 5;
        case 67:  return 5;
        case 68:  return 1;
        case 69:  return 5;
        case 70:  return 5;

        case 1: return 1;
        case 2: return 1;
        case 3: return 1;
        case 4: return 1;

        case 19: return 1;
        case 21: return 3;

        case 30: return 3;
        case 31: return 4;
        case 32: return 16;
        case 33: return 3;
        case 34: return 4;
        case 35: return 16;
        case 36: return 3;
        case 37: return 3;
        case 38: return 1;
        case 39: return 1;

        case 49: return 1;
        case 50: return 1;
        case 26: return 16;
        case 27: return 16;

        default: return 16;
    }
}

/*
 * Translate the parameter values on Java side into the C equiverlant
 */
const HLenum getCParameter(int jpar)
{
    switch(jpar)
    {
        case 1: return HL_STIFFNESS;
        case 2: return HL_DAMPING;
        case 3: return HL_STATIC_FRICTION;
        case 4: return HL_DYNAMIC_FRICTION;
        case 5: return HL_TOUCHABLE_FACE;
        case 6: return HL_FRONT;
        case 7: return HL_BACK;
        case 8: return HL_FRONT_AND_BACK;
        case 9: return HL_TOUCH_MODEL;
        case 10: return HL_CONTACT;
        case 11: return HL_CONSTRAINT;
        case 12: return HL_SNAP_DISTANCE;
        case 13: return HL_PROXY_RESOLUTION;
        case 14: return HL_HAPTIC_CAMERA_VIEW;
        case 15: return HL_ADAPTIVE_VIEWPORT;
        case 16: return HL_SHAPE_FEEDBACK_BUFFER;
        case 17: return HL_SHAPE_DEPTH_BUFFER;
        case 18: return HL_SHAPE_CALLBACK;
        case 19: return HL_PROXY_IS_TOUCHING;
        case 20: return HL_PROXY_TOUCH_NORMAL;
        case 21: return HL_REACTION_FORCE;
        case 22: return HL_SHAPE_FEEDBACK_BUFFER_VERTICES;
        case 23: return HL_SHAPE_DYNAMIC_SURFACE_CHANGE;
        case 24: return HL_VIEWTOUCH;
        case 25: return HL_TOUCHWORKSPACE;
        case 26: return HL_VIEWTOUCH_MATRIX;
        case 27: return HL_TOUCHWORKSPACE_MATRIX;
        case 28: return HL_WORKSPACE;
        case 29: return HL_MAX_WORKSPACE_DIMS;
        case 30: return HL_PROXY_POSITION;
        case 31: return HL_PROXY_ROTATION;
        case 32: return HL_PROXY_TRANSFORM;
        case 33: return HL_DEVICE_POSITION;
        case 34: return HL_DEVICE_ROTATION;
        case 35: return HL_DEVICE_TRANSFORM;
        case 36: return HL_DEVICE_FORCE;
        case 37: return HL_DEVICE_TORQUE;
        case 38: return HL_BUTTON1_STATE;
        case 39: return HL_BUTTON2_STATE;
        case 40: return HL_EVENT_MOTION;
        case 41: return HL_EVENT_1BUTTONDOWN;
        case 42: return HL_EVENT_1BUTTONUP;
        case 43: return HL_EVENT_2BUTTONDOWN;
        case 44: return HL_EVENT_2BUTTONUP;
        case 45: return HL_EVENT_TOUCH;
        case 46: return HL_EVENT_UNTOUCH;
        case 47: return HL_EVENT_CALIBRATION_UPDATE;
        case 48: return HL_EVENT_CALIBRATION_INPUT;
        case 49: return HL_EVENT_MOTION_LINEAR_TOLERANCE;
        case 50: return HL_EVENT_MOTION_ANGULAR_TOLERANCE;
        //case 51: return HL_OBJECT_ANY;
        case 52: return HL_SHAPE_INTERSECT_LS;
        case 53: return HL_SHAPE_CLOSEST_FEATURES;
        case 54: return HL_LOCAL_FEATURE_POINT;
        case 55: return HL_LOCAL_FEATURE_LINE;
        case 56: return HL_LOCAL_FEATURE_PLANE;
        case 57: return HL_CLIENT_THREAD;
        case 58: return HL_COLLISION_THREAD;
        case 59: return HL_EFFECT_CALLBACK;
        case 60: return HL_EFFECT_CONSTANT;
        case 61: return HL_EFFECT_SPRING;
        case 62: return HL_EFFECT_VISCOUS;
        case 63: return HL_EFFECT_FRICTION;
        case 64: return HL_EFFECT_PROPERTY_TYPE;
        case 65: return HL_EFFECT_PROPERTY_GAIN;
        case 66: return HL_EFFECT_PROPERTY_MAGNITUDE;
        case 67: return HL_EFFECT_PROPERTY_FREQUENCY;
        case 68: return HL_EFFECT_PROPERTY_DURATION;
        case 69: return HL_EFFECT_PROPERTY_POSITION;
        case 70: return HL_EFFECT_PROPERTY_DIRECTION;
        case 71: return HL_EFFECT_COMPUTE_FORCE;
        case 72: return HL_EFFECT_START;
        case 73: return HL_EFFECT_STOP;
        case 74: return HL_VERSION;
        case 75: return HL_VENDOR;
        //case 76: return HL_VERSION_MAJOR_NUMBER;
        //case 77: return HL_VERSION_MINOR_NUMBER;

        default: return HL_INVALID_VALUE;
    }
}

/*
 * Translate the parameter values on C side into the Java equiverlant
 */
int getJParameter(HLenum cpar)
{
    if(cpar == HL_FRONT)
        return 6;
    else if(cpar == HL_BACK)
        return 7;
    else if(cpar == HL_FRONT_AND_BACK)
        return 8;
    else if(cpar == HL_CLIENT_THREAD)
        return 57;
    else if(cpar == HL_COLLISION_THREAD)
        return 58;
    else
        return -1;
}
/*
 * Sends Exception out depending on the exception that has occured
 */
const void sendException(HLerror error, JNIEnv *eparam)
{
    (*eparam)->ExceptionClear(eparam);
    jclass excCls = (*eparam)->FindClass(eparam, "com/uce/userlab/haptics/HL/HLException");
    jmethodID con = (*eparam)->GetMethodID(eparam, excCls, "<init>", "(Ljava/lang/String;III)V");

    jstring mess = (*eparam)->NewStringUTF(eparam, error.errorCode);
    jint errorCode = (int)error.errorInfo.errorCode;
    jint internalErrorCode = (int)error.errorInfo.internalErrorCode;
    jint device = (int)error.errorInfo.hHD;

    jobject hdExc = (*eparam)->NewObject(eparam, excCls, con, mess, errorCode, internalErrorCode, device);
    (*eparam)->Throw(eparam, (jthrowable)hdExc);
}


//********************Array management Methods (start)************************//
const int addHHLRC(HHLRC hHLRC)
{
    if(arrayItem < arraySize)
    {
        arrayHHLRC[arrayItem] = hHLRC;
        arrayItem++;
        return arrayItem-1;
    }
    else
        return -1;
}
/*
const char removeHHLRC(int pos)
{
    if(pos < arrayItem)
    {
        arrayHHLRC[pos] = NULL;
        int i;
        for(i=pos; i<arrayItem; i++)
        {
            arrayHHLRC[i] = arrayHHLRC[i+1];
        }
        return TRUE;
    }
    else
        return FALSE;
}
*/
const HHLRC getHHLRC(int pos)
{
    if(pos < arrayItem)
        return arrayHHLRC[pos];
    else
        return NULL;
}

const int findHHLRC(HHLRC hHLRC)
{
    int pos = -1;
    int i;
    for(i=0; i<arrayItem; i++)
    {
        if(arrayHHLRC[i] == hHLRC)
            pos = i;
    }
    return pos;
}
//**********************Array managment methods (end)*************************//

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlBeginFrame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlBeginFrame
  (JNIEnv *eparam, jclass cparam)
{
	hlBeginFrame();
    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlContextDevice
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlContextDevice
  (JNIEnv *eparam, jclass cparam, jint hHD)
{
	hlContextDevice(hHD);
	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCreateContext
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCreateContext
  (JNIEnv *eparam, jclass cparam, jint hHD)
{
    HHLRC hHLRC = hlCreateContext(hHD);
    int i = addHHLRC(hHLRC);
    return i;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlDeleteContext
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlDeleteContext
  (JNIEnv *eparam, jclass cparam, jint hHLRC)
{
	hlDeleteContext(getHHLRC(hHLRC));
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEndFrame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEndFrame
  (JNIEnv *eparam, jclass cparam)
{
	hlEndFrame();
	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetCurrentContext
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetCurrentContext
  (JNIEnv *eparam, jclass cparam)
{
    HHLRC it = hlGetCurrentContext();
    return findHHLRC(it);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetCurrentDevice
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetCurrentDevice
  (JNIEnv *eparam, jclass cparam)
{
	HHD hHD = hlGetCurrentDevice();
    return hHD;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMakeCurrent
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMakeCurrent
  (JNIEnv *eparam, jclass cparam, jint hHLRC)
{
	hlMakeCurrent(getHHLRC(hHLRC));
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEnable
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEnable
  (JNIEnv *eparam, jclass cparam, jint cap)
{
	hlEnable(getCParameter(cap));
	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlDisable
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlDisable
  (JNIEnv *eparam, jclass cparam, jint cap)
{
	hlDisable(getCParameter(cap));
	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetBooleanv
 * Signature: (I)[Z
 */
JNIEXPORT jbooleanArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetBooleanv
  (JNIEnv *eparam, jclass cparam, jint pname)
{
	int num = getJArrayLength(pname);

    jbooleanArray result = (*eparam)->NewBooleanArray(eparam, num);
    HLboolean value[num];
    hlGetBooleanv((HLenum)getCParameter(pname), value);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
        (*eparam)->SetBooleanArrayRegion(eparam, result, 0, num, value);

    return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetDoublev
 * Signature: (I)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetDoublev
  (JNIEnv *eparam, jclass cparam, jint pname)
{
    int num = getJArrayLength(pname);

    jdoubleArray result = (*eparam)->NewDoubleArray(eparam, num);
    HLdouble value[num];
    hlGetDoublev(getCParameter(pname), value);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
        (*eparam)->SetDoubleArrayRegion(eparam, result, 0, num, value);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetIntegerv
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetIntegerv
  (JNIEnv *eparam, jclass cparam, jint pname)
{
    int num = getJArrayLength(pname);

    jintArray result = (*eparam)->NewIntArray(eparam, num);
    HLint value[num];
    hlGetIntegerv(getCParameter(pname), value);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
        (*eparam)->SetIntArrayRegion(eparam, result, 0, num, (jint *)value);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetString
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetString
  (JNIEnv *eparam, jclass cparam, jint name)
{
	char* str = (char *)hlGetString(getCParameter(name));

    jstring result = (*eparam)->NewStringUTF(eparam, str);
    return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlHinti
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlHinti
  (JNIEnv *eparam, jclass cparam, jint target, jint value)
{
	hlHinti(getCParameter(target), value);
	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlHintb
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlHintb
  (JNIEnv *eparam, jclass cparam, jint target, jboolean value)
{
	hlHintb(getCParameter(target), value);
	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlIsEnabled
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlIsEnabled
  (JNIEnv *eparam, jclass cparam, jint cap)
{
	HLboolean result = hlIsEnabled(getCParameter(cap));
    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCacheGetBooleanv
 * Signature: (Ljava/lang/Object;I)[Z
 */
JNIEXPORT jbooleanArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCacheGetBooleanv
  (JNIEnv *eparam, jclass cparam, jobject cache, jint pname)
{
	jbooleanArray result = (*eparam)->NewBooleanArray(eparam, 10);
    return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCacheGetDoublev
 * Signature: (Ljava/lang/Object;I)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCacheGetDoublev
  (JNIEnv *eparam, jclass cparam, jobject cache, jint pname)
{
	jdoubleArray result = (*eparam)->NewDoubleArray(eparam, 10);
    return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlBeginShape
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlBeginShape
  (JNIEnv *eparam, jclass cparam, jint type, jint shape)
{
	hlBeginShape(getCParameter(type), shape);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlDeleteShapes
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlDeleteShapes
  (JNIEnv *eparam, jclass cparam, jint shape, jint range)
{
	hlDeleteShapes(shape, range);
	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEndShape
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEndShape
  (JNIEnv *eparam, jclass cparam)
{
	hlEndShape();
	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGenShape
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGenShape
  (JNIEnv *eparam, jclass cparam)
{
	HLuint shape = hlGenShapes(1);
	return shape;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGenShapes
 * Signature: (I)[I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGenShapes
  (JNIEnv *eparam, jclass cparam, jint range)
{
	HLuint shape = hlGenShapes(range);
	return shape;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLocalFeature1fv
 * Signature: ([II[F)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLocalFeature1fv
  (JNIEnv *eparam, jclass cparam, jint type, jfloatArray v)
{
	if(tmpFeatureStore != NULL)
	{
	    jfloat *farray = (*eparam)->GetFloatArrayElements(eparam, v, 0);
        hlLocalFeature1fv(tmpFeatureStore, getCParameter(type), farray);
	}

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLocalFeature1dv
 * Signature: ([II[D)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLocalFeature1dv
  (JNIEnv *eparam, jclass cparam, jint type, jdoubleArray v)
{
	if(tmpFeatureStore != NULL)
	{
	    jdouble *darray = (*eparam)->GetDoubleArrayElements(eparam, v, 0);
        hlLocalFeature1dv(tmpFeatureStore, getCParameter(type), darray);
	}

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLocalFeature2fv
 * Signature: ([II[F[F)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLocalFeature2fv
  (JNIEnv *eparam, jclass cparam, jint type, jfloatArray v1, jfloatArray v2)
{
	if(tmpFeatureStore != NULL)
	{
	    jfloat *farray1 = (*eparam)->GetFloatArrayElements(eparam, v1, 0);
	    jfloat *farray2 = (*eparam)->GetFloatArrayElements(eparam, v2, 0);
        hlLocalFeature2fv(tmpFeatureStore, getCParameter(type), farray1, farray2);
	}

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLocalFeature2dv
 * Signature: ([II[D[D)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLocalFeature2dv
  (JNIEnv *eparam, jclass cparam, jint type, jdoubleArray v1, jdoubleArray v2)
{
	if(tmpFeatureStore != NULL)
	{
	    jdouble *darray1 = (*eparam)->GetDoubleArrayElements(eparam, v1, 0);
	    jdouble *darray2 = (*eparam)->GetDoubleArrayElements(eparam, v2, 0);
        hlLocalFeature2dv(tmpFeatureStore, getCParameter(type), darray1, darray2);
	}

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlIsShape
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlIsShape
  (JNIEnv *eparam, jclass cparam, jint shape)
{
	jboolean result = hlIsShape(shape);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetShapeBooleanv
 * Signature: (II)[Z
 */
JNIEXPORT jbooleanArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetShapeBooleanv
  (JNIEnv *eparam, jclass cparam, jint shapeId, jint pname)
{
	int num = getJArrayLength(pname);

    jbooleanArray result = (*eparam)->NewBooleanArray(eparam, num);
    HLboolean value[num];
    hlGetShapeBooleanv(shapeId, getCParameter(pname), value);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
        (*eparam)->SetBooleanArrayRegion(eparam, result, 0, num, value);

    return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetShapeDoublev
 * Signature: (II)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetShapeDoublev
  (JNIEnv *eparam, jclass cparam, jint shapeId, jint pname)
{
    int num = getJArrayLength(pname);

    jdoubleArray result = (*eparam)->NewDoubleArray(eparam, num);
    HLdouble value[num];
    hlGetShapeDoublev(shapeId, getCParameter(pname), value);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
        (*eparam)->SetDoubleArrayRegion(eparam, result, 0, num, value);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetMaterialfv
 * Signature: (II)[F
 */
JNIEXPORT jfloatArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetMaterialfv
  (JNIEnv *eparam, jclass cparam, jint face, jint pname)
{
	int num = getJArrayLength(pname);

    jfloatArray result = (*eparam)->NewFloatArray(eparam, num);
    HLfloat value[num];
    hlGetMaterialfv(getCParameter(face), getCParameter(pname), value);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
        (*eparam)->SetFloatArrayRegion(eparam, result, 0, num, value);

    return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMaterialf
 * Signature: (IIF)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMaterialf
  (JNIEnv *eparam, jclass cparam, jint face, jint pname, jfloat param)
{
	hlMaterialf(getCParameter(face), getCParameter(pname), param);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTouchableFace
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTouchableFace
  (JNIEnv *eparam, jclass cparam, jint mode)
{
	hlTouchableFace(getCParameter(mode));

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTouchModel
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTouchModel
  (JNIEnv *eparam, jclass cparam, jint mode)
{
	hlTouchModel(getCParameter(mode));

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTouchModelf
 * Signature: (IF)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTouchModelf
  (JNIEnv *eparam, jclass cparam, jint mode, jfloat param)
{
	hlTouchModelf(getCParameter(mode), param);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlDeleteEffects
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlDeleteEffects
  (JNIEnv *eparam, jclass cparam, jint effect, jint range)
{
	hlDeleteEffects(effect, range);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEffectd
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEffectd
  (JNIEnv *eparam, jclass cparam, jint pname, jdouble param)
{
	hlEffectd(getCParameter(pname), param);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEffecti
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEffecti
  (JNIEnv *eparam, jclass cparam, jint pname, jint param)
{
	hlEffecti(getCParameter(pname), param);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEffectdv
 * Signature: (I[D)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEffectdv
  (JNIEnv *eparam, jclass cparam, jint pname, jdoubleArray params)
{
    jdouble *darray = (*eparam)->GetDoubleArrayElements(eparam, params, 0);
    hlEffectdv(getCParameter(pname), darray);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEffectiv
 * Signature: (I[I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEffectiv
  (JNIEnv *eparam, jclass cparam, jint pname, jintArray params)
{
	jint *iarray = (*eparam)->GetIntArrayElements(eparam, params, 0);
    hlEffectiv(getCParameter(pname), (HLint *)iarray);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGenEffect
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGenEffect
  (JNIEnv *eparam, jclass cparam)
{
	int result = hlGenEffects(1);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGenEffects
 * Signature: (I)[I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGenEffects
  (JNIEnv *eparam, jclass cparam, jint range)
{
	int result = hlGenEffects(range);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);

    return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetEffectdv
 * Signature: (II)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetEffectdv
  (JNIEnv *eparam, jclass cparam, jint effect, jint pname)
{
	int num = getJArrayLength(pname);

    jdoubleArray result = (*eparam)->NewDoubleArray(eparam, num);
    HLdouble value[num];
    hlGetEffectdv(effect, getCParameter(pname), value);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
        (*eparam)->SetDoubleArrayRegion(eparam, result, 0, num, value);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetEffectiv
 * Signature: (II)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetEffectiv
  (JNIEnv *eparam, jclass cparam, jint effect, jint pname)
{
	int num = getJArrayLength(pname);

    jintArray result = (*eparam)->NewDoubleArray(eparam, num);
    HLint value[num];
    hlGetEffectiv(effect, getCParameter(pname), value);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
        (*eparam)->SetIntArrayRegion(eparam, result, 0, num, (jint *)value);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlIsEffect
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlIsEffect
  (JNIEnv *eparam, jclass cparam, jint effect)
{
	char result = hlIsEffect(effect);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlStartEffect
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlStartEffect
  (JNIEnv *eparam, jclass cparam, jint type, jint effect)
{
	hlStartEffect(getCParameter(type), effect);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlStopEffect
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlStopEffect
  (JNIEnv *eparam, jclass cparam, jint effect)
{
	hlStopEffect(effect);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTriggerEffect
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTriggerEffect
  (JNIEnv *eparam, jclass cparam, jint type)
{
	hlTriggerEffect(getCParameter(type));

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlProxydv
 * Signature: (I[D)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlProxydv
  (JNIEnv *eparam, jclass cparam, jint pname, jdoubleArray params)
{
	jdouble *darray = (*eparam)->GetDoubleArrayElements(eparam, params, 0);
	hlProxydv(getCParameter(pname), darray);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLoadIdentity
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLoadIdentity
  (JNIEnv *eparam, jclass cparam)
{
	hlLoadIdentity();

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLoadMatrixd
 * Signature: ([D)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLoadMatrixd
  (JNIEnv *eparam, jclass cparam, jdoubleArray m)
{
	jdouble *darray = (*eparam)->GetDoubleArrayElements(eparam, m, 0);

	hlLoadMatrixd(darray);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLoadMatrixf
 * Signature: ([F)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLoadMatrixf
  (JNIEnv *eparam, jclass cparam, jfloatArray m)
{
	jfloat *farray = (*eparam)->GetFloatArrayElements(eparam, m, 0);

	hlLoadMatrixf(farray);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMultMatrixd
 * Signature: ([D)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMultMatrixd
  (JNIEnv *eparam, jclass cparam, jdoubleArray m)
{
	jdouble *darray = (*eparam)->GetDoubleArrayElements(eparam, m, 0);

	hlMultMatrixd(darray);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMultMatrixf
 * Signature: ([F)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMultMatrixf
  (JNIEnv *eparam, jclass cparam, jfloatArray m)
{
	jfloat *farray = (*eparam)->GetFloatArrayElements(eparam, m, 0);

	hlMultMatrixf(farray);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMatrixMode
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMatrixMode
  (JNIEnv *eparam, jclass cparam, jint mode)
{
	hlMatrixMode(getCParameter(mode));

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlOrtho
 * Signature: (DDDDDD)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlOrtho
  (JNIEnv *eparam, jclass cparam, jdouble left, jdouble right, jdouble bottom, jdouble top, jdouble zNear, jdouble zFar)
{
	hlOrtho(left, right, bottom, top, zNear, zFar);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlPushMatrix
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlPushMatrix
  (JNIEnv *eparam, jclass cparam)
{
	hlPushMatrix();

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlPopMatrix
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlPopMatrix
  (JNIEnv *eparam, jclass cparam)
{
	hlPopMatrix();

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlRotated
 * Signature: (DDDD)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlRotated
  (JNIEnv *eparam, jclass cparam, jdouble angle, jdouble x, jdouble y, jdouble z)
{
	hlRotated(angle, x, y, z);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlRotatef
 * Signature: (FFFF)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlRotatef
  (JNIEnv *eparam, jclass cparam, jfloat angle, jfloat x, jfloat y, jfloat z)
{
	hlRotatef(angle, x, y, z);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlScaled
 * Signature: (DDD)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlScaled
  (JNIEnv *eparam, jclass cparam, jdouble x, jdouble y, jdouble z)
{
	hlScaled(x, y, z);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlScalef
 * Signature: (FFF)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlScalef
  (JNIEnv *eparam, jclass cparam, jfloat x, jfloat y, jfloat z)
{
	hlScalef(x, y, z);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTranslated
 * Signature: (DDD)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTranslated
  (JNIEnv *eparam, jclass cparam, jdouble x, jdouble y, jdouble z)
{
	hlTranslated(x, y, z);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTranslatef
 * Signature: (FFF)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTranslatef
  (JNIEnv *eparam, jclass cparam, jfloat x, jfloat y, jfloat z)
{
	hlTranslatef(x, y, z);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlWorkspace
 * Signature: (DDDDDD)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlWorkspace
  (JNIEnv *eparam, jclass cparam, jdouble left, jdouble bottom, jdouble back, jdouble right, jdouble top, jdouble front)
{
	hlWorkspace(left, bottom, back, right, top, front);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlUpdateCalibration
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlUpdateCalibration
  (JNIEnv *eparam, jclass cparam)
{
	hlUpdateCalibration();

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCallback
 * Signature: (Lcom/uce/userlab/haptics/HL/HLCallback;[Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCallback
  (JNIEnv *eparam, jclass cparam, jint type,  jobject ob, jobjectArray usedata)
{
    if(callobjPt < callobjSize)
    {
    HLenum answer = getCParameter(type);

    CallObject tyobj;
        tyobj.obj = (*eparam)->NewGlobalRef(eparam, ob);
        tyobj.data = (*eparam)->NewGlobalRef(eparam, usedata);

    callobj[callobjPt] = tyobj;

    if(answer == HL_SHAPE_INTERSECT_LS)
    {
        hlCallback(answer, (HLcallbackProc)intersectCallback, &callobj[callobjPt]);
    }
    else if(answer == HL_SHAPE_CLOSEST_FEATURES)
    {
        hlCallback(answer, (HLcallbackProc)closestFeatureCallback, &callobj[callobjPt]);
    }
    else if(answer == HL_EFFECT_COMPUTE_FORCE)
    {
        hlCallback(answer, (HLcallbackProc)computeForceCallback,  &callobj[callobjPt]);
    }
    else if(answer == HL_EFFECT_START)
    {
        hlCallback(answer, (HLcallbackProc)startForceCallback, &callobj[callobjPt]);
    }
    else if(answer == HL_EFFECT_STOP)
    {
        hlCallback(answer, (HLcallbackProc)stopForceCallback, &callobj[callobjPt]);
    }
    //intersectCallback
    //closestFeatureCallback
    //computeForceCallback
    //startForceCallback
    //stopForceCallback

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
		callobjPt++;

    }
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlAddEventCallback
 * Signature: (ILcom/uce/userlab/haptics/HL/HLCallback;[Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlAddEventCallback
  (JNIEnv *eparam, jclass cparam, jint shape, jint thread, jobject obj, jobjectArray userdata)
{
	//HLenum answer = getCParameter(type);

    EventCallbackObject tyobj;
        tyobj.obj = (*eparam)->NewGlobalRef(eparam, obj);
        tyobj.data = (*eparam)->NewGlobalRef(eparam, userdata);
        tyobj.shape = shape;
        tyobj.thread = thread;

    eventcallobj[eventcallobjPt] = tyobj;

    HLenum t = getCParameter(thread);

    hlAddEventCallback(HL_EVENT_MOTION, shape, t, eventCallback, &eventcallobj[eventcallobjPt]);
    hlAddEventCallback(HL_EVENT_1BUTTONDOWN, shape, t, eventCallback, &eventcallobj[eventcallobjPt]);
    hlAddEventCallback(HL_EVENT_1BUTTONUP, shape, t, eventCallback, &eventcallobj[eventcallobjPt]);
    hlAddEventCallback(HL_EVENT_2BUTTONDOWN, shape, t, eventCallback, &eventcallobj[eventcallobjPt]);
    hlAddEventCallback(HL_EVENT_2BUTTONUP, shape, t, eventCallback, &eventcallobj[eventcallobjPt]);
    hlAddEventCallback(HL_EVENT_TOUCH, shape, t, eventCallback, &eventcallobj[eventcallobjPt]);
    hlAddEventCallback(HL_EVENT_UNTOUCH, shape, t, eventCallback, &eventcallobj[eventcallobjPt]);

    HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
    else
		eventcallobjPt++;
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlRemoveEventCallback
 * Signature: (ILcom/uce/userlab/haptics/HL/HLEventCallback;)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlRemoveEventCallback
  (JNIEnv *eparam, jclass cparam, jint shape, jint thread, jobject obj)
{
    int i;
    for(i=0; i<eventcallobjPt; i++)
    {
        EventCallbackObject tmp = eventcallobj[i];

        if(tmp.obj != NULL)
        {
            if((*eparam)->IsSameObject(eparam, tmp.obj, obj) == JNI_TRUE)
            {
                if(tmp.shape == shape)
                {
                    if(tmp.thread == thread)
                    {
                        tmp.obj = NULL;
                    }
                }
            }
        }
    }
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCheckEvents
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCheckEvents
  (JNIEnv *eparam, jclass cparam)
{
	hlCheckEvents();

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEventd
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEventd
  (JNIEnv *eparam, jclass cparam, jint pname, jdouble param)
{
	hlEventd(getCParameter(pname), param);

	HLerror error = hlGetError();
	if (error.errorCode != HL_NO_ERROR)
		sendException(error, eparam);
}

#ifdef __cplusplus
}
#endif
#endif
