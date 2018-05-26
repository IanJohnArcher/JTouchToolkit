
#include "stdafx.h"
#include "JHDAPI.h"
#include <jni.h>
#include <HD/hd.h>
#include <HDU/hduVector.h>
#include <stdio.h>

typedef struct
{
    jobject obj;
    jobjectArray data;
}HDcallbackObj;

HDSchedulerHandle handler[10000];
JavaVM *cached_jvm;
HDcallbackObj callobj[10000];
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

const char *getErrorCodeName(HDerror errorCode)
{
    switch (errorCode)
    {
        case HD_SUCCESS: return "HD_SUCCESS";

        /* Function errors */
        case HD_INVALID_ENUM: return "HD_INVALID_ENUM";
        case HD_INVALID_VALUE: return "HD_INVALID_VALUE";
        case HD_INVALID_OPERATION: return "HD_INVALID_OPERATION";
        case HD_INVALID_INPUT_TYPE: return "HD_INVALID_INPUT_TYPE";
        case HD_BAD_HANDLE: return "HD_BAD_HANDLE";

        /* Force errors */
        case HD_WARM_MOTORS: return "HD_WARM_MOTORS";
        case HD_EXCEEDED_MAX_FORCE: return "HD_EXCEEDED_MAX_FORCE";
        case HD_EXCEEDED_MAX_FORCE_IMPULSE: return "HD_EXCEEDED_MAX_FORCE_IMPULSE";
        case HD_EXCEEDED_MAX_VELOCITY: return "HD_EXCEEDED_MAX_VELOCITY";
        case HD_FORCE_ERROR: return "HD_FORCE_ERROR";

        /* Device errors */
        case HD_DEVICE_FAULT: return "HD_DEVICE_FAULT";
        case HD_DEVICE_ALREADY_INITIATED: return "HD_DEVICE_ALREADY_INITIATED";
        case HD_COMM_ERROR: return "HD_COMM_ERROR";
        case HD_COMM_CONFIG_ERROR: return "HD_COMM_CONFIG_ERROR";
        case HD_TIMER_ERROR: return "HD_TIMER_ERROR";

        /* Haptic rendering context */
        case HD_ILLEGAL_BEGIN: return "HD_ILLEGAL_BEGIN";
        case HD_ILLEGAL_END: return "HD_ILLEGAL_END";
        case HD_FRAME_ERROR: return "HD_FRAME_ERROR";

        /* Scheduler errors */
        case HD_INVALID_PRIORITY: return "HD_INVALID_PRIORITY";
        case HD_SCHEDULER_FULL: return "HD_SCHEDULER_FULL";

        default: return "UNKOWN_ERROR";
    }
}

void sendException(HDErrorInfo error, JNIEnv *eparam)
{
    eparam->ExceptionClear();
    jclass excCls = eparam->FindClass("com/uce/userlab/haptics/HD/HDException");
    jmethodID con = eparam->GetMethodID(excCls, "<init>", "(Ljava/lang/String;III)V");

    jstring mess = eparam->NewStringUTF(getErrorCodeName(error.errorCode));
    jint errorCode = error.errorCode;
    jint internalErrorCode = error.internalErrorCode;
    jint device = error.hHD;

    jobject hdExc = eparam->NewObject(excCls, con, mess, errorCode, internalErrorCode, device);
    eparam->Throw((jthrowable)hdExc);
}

/*
 * Map return value in C to the equiverlant in Java for Buttons
 */
const jint getJArrayLength(int param)
{
    int para = param;

    switch (para)
    {
        case HD_CURRENT_BUTTONS:                return 1;
        case HD_CURRENT_SAFETY_SWITCH:          return 1;
        case HD_CURRENT_ENCODER_VALUES:         return 6;
        case HD_CURRENT_POSITION:               return 3;
        case HD_CURRENT_VELOCITY:               return 3;
        case HD_CURRENT_TRANSFORM:              return 16;
        case HD_CURRENT_ANGULAR_VELOCITY:       return 3;
        case HD_CURRENT_JOINT_ANGLES:           return 3;
        case HD_CURRENT_GIMBAL_ANGLES:          return 3;

        case HD_CURRENT_FORCE:                  return 3;
        case HD_CURRENT_TORQUE:                 return 3;
        case HD_CURRENT_MOTOR_DAC_VALUES:       return 6;

		case HD_LAST_FORCE:						return 3;
		case HD_LAST_TORQUE:					return 3;
		case HD_LAST_MOTOR_DAC_VALUES:			return 6;

        case HD_VERSION:                        return 1;
        case HD_DEVICE_MODEL_TYPE:              return 1;
        case HD_DEVICE_DRIVER_VERSION:          return 1;
        case HD_DEVICE_VENDOR:                  return 1;
        case HD_DEVICE_SERIAL_NUMBER:           return 1;
        case HD_MAX_WORKSPACE_DIMENSIONS:       return 6;
        case HD_USABLE_WORKSPACE_DIMENSIONS:    return 6;
        case HD_TABLETOP_OFFSET:                return 1;
        case HD_INPUT_DOF:                      return 1;
        case HD_OUTPUT_DOF:                     return 1;
        case HD_CALIBRATION_STYLE:              return 1;

        case HD_LAST_BUTTONS:                   return 1;
        case HD_LAST_SAFETY_SWITCH:             return 1;
        case HD_LAST_ENCODER_VALUES:            return 6;
        case HD_LAST_POSITION:                  return 3;
        case HD_LAST_VELOCITY:                  return 3;
        case HD_LAST_TRANSFORM:                 return 16;
        case HD_LAST_ANGULAR_VELOCITY:          return 3;
        case HD_LAST_JOINT_ANGLES:              return 3;
        case HD_LAST_GIMBAL_ANGLES:             return 3;

        case HD_NOMINAL_MAX_STIFFNESS:          return 1;
        case HD_NOMINAL_MAX_DAMPING:            return 1;
        case HD_NOMINAL_MAX_FORCE:              return 1;
        case HD_NOMINAL_MAX_CONTINUOUS_FORCE:   return 1;
        case HD_MOTOR_TEMPERATURE:              return 6;
        case HD_FORCE_RAMPING_RATE:             return 1;

        case HD_UPDATE_RATE:                    return 1;
        case HD_INSTANTANEOUS_UPDATE_RATE:      return 1;

        default:                                return 16;
    }
}
/*
 * The callback proceedure
 */
HDCallbackCode HDCALLBACK myCallback(void *data)
{
    HDcallbackObj* object = (HDcallbackObj *)data;

    if(object[0].obj != NULL)
    {
        JNIEnv *eparam;// = JNU_GetEnv();
        cached_jvm->AttachCurrentThread((void **)&eparam, NULL);

        jclass c = eparam->GetObjectClass(object[0].obj);
        jmethodID con = eparam->GetMethodID(c, "HD_Callback", "([Ljava/lang/Object;)I");
        jint param = eparam->CallIntMethod(object[0].obj, con, object[0].data);

        cached_jvm->DetachCurrentThread();

        return param;
    }
    else
        return HD_CALLBACK_CONTINUE;//HD_CALLBACK_DONE;
}
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdBeginFrame
  (JNIEnv *eparam, jclass cparam, jint hHD) {
	hdBeginFrame(hHD);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdDisable
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdDisable
  (JNIEnv *eparam, jclass cparam, jint cap){
	hdDisable(cap);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdDisableDevice
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdDisableDevice
  (JNIEnv *eparam, jclass cparam, jint hHD){
	hdDisableDevice(hHD);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdEnable
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdEnable
  (JNIEnv *eparam, jclass cparam, jint cap){
	hdEnable(cap);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdEndFrame
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdEndFrame
  (JNIEnv *eparam, jclass cparam, jint hHD){
	hdEndFrame(hHD);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdGetBooleanv
 * Signature: (I)[Z
 */
JNIEXPORT jbooleanArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetBooleanv
  (JNIEnv *eparam, jclass cparam, jint cap){
	const int num = getJArrayLength(cap);

    HDboolean *value = new HDboolean[num];
    hdGetBooleanv(cap, value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jbooleanArray result = eparam->NewBooleanArray(num);
    eparam->SetBooleanArrayRegion(result, 0, num, value);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdGetIntegerv
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetIntegerv
  (JNIEnv *eparam, jclass cparam, jint cap){
	const int num = getJArrayLength(cap);
	
    HDint *value = new HDint[num];
    hdGetIntegerv(cap, value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jintArray result = eparam->NewIntArray(num);
    eparam->SetIntArrayRegion(result, 0, num, (jint *)value);

    return result;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdGetFloatv
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetFloatv
  (JNIEnv *eparam, jclass cparam, jint cap){
	int num = getJArrayLength(cap);

    HDfloat *value = new HDfloat[num];
    hdGetFloatv(cap, value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jfloatArray result = eparam->NewFloatArray(num);
    eparam->SetFloatArrayRegion(result, 0, num, value);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdGetDoublev
 * Signature: (I)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetDoublev
  (JNIEnv *eparam, jclass cparam, jint cap){
    int num = getJArrayLength(cap);

    HDdouble *value = new HDdouble[num];
    hdGetDoublev(cap, value);

    HDErrorInfo error;
    if(HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jdoubleArray result = eparam->NewDoubleArray(num);
    eparam->SetDoubleArrayRegion(result, 0, num, value);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdGetLongv
 * Signature: (I)[J
 */
JNIEXPORT jlongArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetLongv
  (JNIEnv *eparam, jclass cparam, jint cap){
    int num = getJArrayLength(cap);

    HDlong *value = new HDlong[num];
    hdGetLongv(cap, value);

    HDErrorInfo error;
    if(HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jlongArray result = eparam->NewLongArray(num);
    eparam->SetLongArrayRegion(result, 0, num, (jlong *)value);

	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdGetCurrentDevice
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetCurrentDevice
  (JNIEnv *eparam, jclass cparam){
	HHD hHD = hdGetCurrentDevice();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

	return hHD;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdGetString
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetString
  (JNIEnv *eparam, jclass cparam, jint cap){
	const char *value = hdGetString(cap);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jstring result = eparam->NewStringUTF((char *)value);
	return result;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdInitDevice
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdInitDevice
  (JNIEnv *eparam, jclass cparam, jstring configName){
	//return -1;
	const char *str = eparam->GetStringUTFChars(configName, 0 );

    HHD hHD = hdInitDevice(str);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

	return hHD;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdIsEnabled
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdIsEnabled
  (JNIEnv *eparam, jclass cparam, jint cap){
	HDboolean value = hdIsEnabled(cap);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

	return value;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdMakeCurrentDevice
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdMakeCurrentDevice
  (JNIEnv *eparam, jclass cparam, jint hHD){
    hdMakeCurrentDevice(hHD);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdSetBooleanv
 * Signature: (I[Z)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetBooleanv
  (JNIEnv *eparam, jclass cparam, jint cap, jbooleanArray v){
	HDboolean *barray = eparam->GetBooleanArrayElements(v, 0);

    hdSetBooleanv(cap, barray);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdSetIntegerv
 * Signature: (I[I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetIntegerv
  (JNIEnv *eparam, jclass cparam, jint cap, jintArray v){
	HDint *iarray = (HDint *)eparam->GetIntArrayElements(v, 0);

    hdSetIntegerv(cap, iarray);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdSetFloatv
 * Signature: (I[F)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetFloatv
  (JNIEnv *eparam, jclass cparam, jint cap, jfloatArray v){
    jfloat *farray = eparam->GetFloatArrayElements(v, 0);

    hdSetFloatv(cap, farray);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdSetDoublev
 * Signature: (I[D)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetDoublev
  (JNIEnv *eparam, jclass cparam, jint cap, jdoubleArray v){
    jdouble *darray = eparam->GetDoubleArrayElements(v, 0);

    hdSetDoublev(cap, darray);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdSetLongv
 * Signature: (I[J)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetLongv
  (JNIEnv *eparam, jclass cparam, jint cap, jlongArray v){
    HDlong *larray = (long *)eparam->GetLongArrayElements(v, 0);

    hdSetLongv(cap, larray);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdCheckCalibration
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdCheckCalibration
  (JNIEnv *eparam, jclass cparam){
	int value = hdCheckCalibration();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

	return value;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdUpdateCalibration
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdUpdateCalibration
  (JNIEnv *eparam, jclass cparam, jint cap){
    hdUpdateCalibration(cap);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdGetSchedulerTimeStamp
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetSchedulerTimeStamp
  (JNIEnv *eparam, jclass cparam){
    HDdouble d = hdGetSchedulerTimeStamp();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

	return d;
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdScheduleAsynchronous
 * Signature: (Lcom/uce/userlab/haptics/HD/HDCallback;[Ljava/lang/Object;S)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdScheduleAsynchronous
  (JNIEnv *eparam, jclass cparam, jobject par, jobjectArray obj, jshort cap){
    HDcallbackObj callObject;
        callObject.obj = eparam->NewGlobalRef(par);
        callObject.data = (jobjectArray)eparam->NewGlobalRef(obj);

    if(callobjPt < callobjSize)
    {
        callobj[callobjPt] = callObject;

        handler[callobjPt] = hdScheduleAsynchronous(myCallback, &callobj[callobjPt], cap);
        callobjPt++;
    }

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdScheduleSynchronous
 * Signature: (Lcom/uce/userlab/haptics/HD/HDCallback;[Ljava/lang/Object;S)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdScheduleSynchronous
  (JNIEnv *eparam, jclass cparam, jobject par, jobjectArray obj, jshort cap){
    HDcallbackObj callObject;
        callObject.obj = eparam->NewGlobalRef(par);
        callObject.data = (jobjectArray)eparam->NewGlobalRef(obj);

    if(callobjPt < callobjSize)
    {
        callobj[callobjPt] = callObject;

        hdScheduleSynchronous(myCallback, &callobj[callobjPt], cap);
        callobjPt++;
    }

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdSetSchedulerRate
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetSchedulerRate
  (JNIEnv *eparam, jclass cparam, jlong rate){
    HDulong value = (HDulong)rate;
    hdSetSchedulerRate(value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdStartScheduler
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdStartScheduler
  (JNIEnv *eparam, jclass cparam){
    hdStartScheduler();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdStopScheduler
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdStopScheduler
  (JNIEnv *eparam, jclass cparam){
    hdStopScheduler();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdUnschedule
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdUnschedule
  (JNIEnv *eparam, jclass cparam, jint hand){
    hdUnschedule(handler[hand]);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     com_uce_userlab_haptics_HD_HDAPI
 * Method:    hdWaitForCompletion
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdWaitForCompletion
  (JNIEnv *eparam, jclass cparam, jint hand, jint cap){
    jboolean value = JNI_FALSE;

    if(handler[hand] != -1)
        value = hdWaitForCompletion(handler[hand], cap);

    jboolean result = value;
	return result;
}