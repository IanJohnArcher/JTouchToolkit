#include <HD\hd.h>
#include <HDU/hduVector.h>
#include <jni.h>
//#include "com_uce_userlab_haptics_HD_HDAPI.h"
#include <stdio.h>

#ifndef _Included_Arguments
#define _Included_Arguments
#ifdef __cplusplus
extern "C" {
#endif

/********Helper methods for Interface methods**********/

static jclass cls = 0;

jobject sparam;
HDSchedulerHandle handler[10000];

typedef struct
{
    jobject obj;
    jobjectArray data;
}HDcallbackObj;

HDcallbackObj callobj[10000];
int callobjSize = 10000;
int callobjPt = 0;

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
 * Check the error code and return the error String
 */
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

/*
 * Translate the parameter values on Java side into the C equiverlant
 */
const int getJParameterValue(jint para)
{
    switch (para)
    {
        case 1:     return HD_FORCE_OUTPUT;
        case 2:     return HD_MAX_FORCE_CLAMPING;
        case 3:     return HD_FORCE_RAMPING;
        case 4:     return HD_SOFTWARE_FORCE_LIMIT;
        case 5:     return HD_SOFTWARE_FORCE_IMPULSE_LIMIT;
        case 6:     return HD_SOFTWARE_VELOCITY_LIMIT;
        case 7:     return HD_ONE_FRAME_LIMIT;

        case 8:     return HD_CURRENT_BUTTONS;
        case 9:     return HD_CURRENT_SAFETY_SWITCH;
        case 10:    return HD_CURRENT_ENCODER_VALUES;
        case 11:    return HD_CURRENT_POSITION;
        case 12:    return HD_CURRENT_VELOCITY;
        case 13:    return HD_CURRENT_TRANSFORM;
        case 14:    return HD_CURRENT_ANGULAR_VELOCITY;
        case 15:    return HD_CURRENT_JOINT_ANGLES;
        case 16:    return HD_CURRENT_GIMBAL_ANGLES;

        case 17:    return HD_CURRENT_FORCE;
        case 18:    return HD_CURRENT_TORQUE;
        case 19:    return HD_CURRENT_MOTOR_DAC_VALUES;

        case 20:    return HD_CALIBRATION_OK;
        case 21:    return HD_CALIBRATION_NEEDS_UPDATE;
        case 22:    return HD_CALIBRATION_NEEDS_MANUAL_INPUT;

        //case 23:    return HD_HARDWARE_REVISION;
        case 24:    return HD_VERSION;
        case 25:    return HD_DEVICE_MODEL_TYPE;
        case 26:    return HD_DEVICE_DRIVER_VERSION;
        case 27:    return HD_DEVICE_VENDOR;
        case 28:    return HD_DEVICE_SERIAL_NUMBER;
        case 29:    return HD_MAX_WORKSPACE_DIMENSIONS;
        case 30:    return HD_USABLE_WORKSPACE_DIMENSIONS;
        case 31:    return HD_TABLETOP_OFFSET;
        case 32:    return HD_INPUT_DOF;
        case 33:    return HD_OUTPUT_DOF;
        case 34:    return HD_CALIBRATION_STYLE;

        case 35:    return HD_LAST_BUTTONS;
        case 36:    return HD_LAST_SAFETY_SWITCH;
        case 37:    return HD_LAST_ENCODER_VALUES;
        case 38:    return HD_LAST_POSITION;
        case 39:    return HD_LAST_VELOCITY;
        case 40:    return HD_LAST_TRANSFORM;
        case 41:    return HD_LAST_ANGULAR_VELOCITY;
        case 42:    return HD_LAST_JOINT_ANGLES;
        case 43:    return HD_LAST_GIMBAL_ANGLES;

        case 44:    return HD_NOMINAL_MAX_STIFFNESS;
        case 45:    return HD_NOMINAL_MAX_DAMPING;
        case 46:    return HD_NOMINAL_MAX_FORCE;
        case 47:    return HD_NOMINAL_MAX_CONTINUOUS_FORCE;
        case 48:    return HD_MOTOR_TEMPERATURE;
        //case 49:    return HD_SOFTWARE_MAX_VELOCITY;
        case 50:    return HD_FORCE_RAMPING_RATE;

        case 51:    return HD_UPDATE_RATE;
        case 52:    return HD_INSTANTANEOUS_UPDATE_RATE;

        case 53:    return HD_CALIBRATION_AUTO;
        case 54:    return HD_CALIBRATION_ENCODER_RESET;
        case 55:    return HD_CALIBRATION_INKWELL;

        case 56:    return HD_DEVICE_BUTTON_1;
        case 57:    return HD_DEVICE_BUTTON_2;
        case 58:    return HD_DEVICE_BUTTON_3;
        case 59:    return HD_DEVICE_BUTTON_4;

        case 60:    return HD_MAX_SCHEDULER_PRIORITY;
        case 61:    return HD_MIN_SCHEDULER_PRIORITY;
        case 62:    return HD_DEFAULT_SCHEDULER_PRIORITY;

        case 63:    return HD_CALLBACK_CONTINUE;
        case 64:    return HD_CALLBACK_DONE;

        //case 65:    return HD_SCHEDULER_FULL;
        //case 66:    return HD_INVALID_PRIORITY;
        //case 67:    return HD_INVALID_VALUE;
        //case 68:    return HD_TIMER_ERROR;
        //case 69:    return HD_INVALID_OPERATION;
        //case 70:    return HD_INVALID_HANDLE;

        case 71:    return HD_WAIT_CHECK_STATUS;
        case 72:    return HD_WAIT_INFINITE;

        default:    return 0;
    }
}

/*
 * Map return value in C to the equiverlant in Java for Calibration
 */
jint getJReturnCal(int retu)
{
    switch (retu)
    {
        case HD_CALIBRATION_OK:                     return 20;
        case HD_CALIBRATION_NEEDS_UPDATE:           return 21;
        case HD_CALIBRATION_NEEDS_MANUAL_INPUT:     return 22;

        case HD_CALIBRATION_AUTO:                   return 53;
        case HD_CALIBRATION_ENCODER_RESET:          return 54;
        case HD_CALIBRATION_INKWELL:                return 55;

        default:                                    return retu;
    }
}

/*
 * Map return value in C to the equiverlant in Java for Buttons
 */
jint getJReturnBut(int retu)
{
    switch (retu)
    {
        case HD_DEVICE_BUTTON_1:                        return 56;
        case HD_DEVICE_BUTTON_2:                        return 57;
        case HD_DEVICE_BUTTON_3:                        return 58;
        case HD_DEVICE_BUTTON_4:                        return 59;

        case (HD_DEVICE_BUTTON_1 + HD_DEVICE_BUTTON_2): return (56*57);
        case (HD_DEVICE_BUTTON_1 + HD_DEVICE_BUTTON_3): return (56*58);
        case (HD_DEVICE_BUTTON_1 + HD_DEVICE_BUTTON_4): return (56*59);

        case (HD_DEVICE_BUTTON_2 + HD_DEVICE_BUTTON_3): return (57*58);
        case (HD_DEVICE_BUTTON_2 + HD_DEVICE_BUTTON_4): return (57*59);

        case (HD_DEVICE_BUTTON_3 + HD_DEVICE_BUTTON_4): return (58*59);

        default:                                        return retu;
    }
}

/*
 * Map return value in C to the equiverlant in Java for Buttons
 */
jint getJArrayLength(int param)
{
    int para = getJParameterValue(param);

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

        //case HD_HARDWAVE_REVISION:              return 1;
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
        //case HD_SOFTWARE_MAX_VELOCITY:          return 1;
        case HD_FORCE_RAMPING_RATE:             return 1;

        case HD_UPDATE_RATE:                    return 1;
        case HD_INSTANTANEOUS_UPDATE_RATE:      return 1;

        default:                                return 16;
    }
}

/*
 * Sends Exception out depending on the exception that has occured
 */
void sendException(HDErrorInfo error, JNIEnv *eparam)
{
    (*eparam)->ExceptionClear(eparam);
    jclass excCls = (*eparam)->FindClass(eparam, "com/uce/userlab/haptics/HD/HDException");
    jmethodID con = (*eparam)->GetMethodID(eparam, excCls, "<init>", "(Ljava/lang/String;III)V");

    jstring mess = (*eparam)->NewStringUTF(eparam, getErrorCodeName(error.errorCode));
    jint errorCode = error.errorCode;
    jint internalErrorCode = error.internalErrorCode;
    jint device = error.hHD;

    jobject hdExc = (*eparam)->NewObject(eparam, excCls, con, mess, errorCode, internalErrorCode, device);
    (*eparam)->Throw(eparam, (jthrowable)hdExc);
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
        (*cached_jvm)->AttachCurrentThread(cached_jvm, (void **)&eparam, NULL);

        jclass c = (*eparam)->GetObjectClass(eparam, object[0].obj);
        jmethodID con = (*eparam)->GetMethodID(eparam, c, "HD_Callback", "([Ljava/lang/Object;)I");
        jint param = (*eparam)->CallIntMethod(eparam, object[0].obj, con, object[0].data);

        (*cached_jvm)->DetachCurrentThread(cached_jvm);

        return getJParameterValue(param);
    }
    else
        return HD_CALLBACK_CONTINUE;//HD_CALLBACK_DONE;
}

/***External Methods for interfacing with Java****/

JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdBeginFrame
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    HHD hHD = iparam;
    hdBeginFrame(hHD);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdDisable
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdDisable
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    hdDisable(getJParameterValue(iparam));

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdDisableDevice
 * Signature: (LHDAPI/HHD;)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdDisableDevice
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    hdDisableDevice(iparam);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdEnable
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdEnable
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    hdEnable(getJParameterValue(iparam));

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdEndFrame
 * Signature: (LHDAPI/HHD;)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdEndFrame
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    HHD hHD = hdGetCurrentDevice();
    hdEndFrame(hHD);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdGetBooleanv
 * Signature: (I)[Z
 */
JNIEXPORT jbooleanArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetBooleanv
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    int num = getJArrayLength(iparam);

    HDboolean value[num];
    hdGetBooleanv(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jbooleanArray result = (*eparam)->NewBooleanArray(eparam, num);
    (*eparam)->SetBooleanArrayRegion(eparam, result, 0, num, value);

	return result;
}

/*
 * Class:     HDAPI
 * Method:    hdGetIntegerv
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetIntegerv
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    int num = getJArrayLength(iparam);

    HDint value[num];
    hdGetIntegerv(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    if(getJParameterValue(iparam) == HD_CURRENT_BUTTONS)
        value[0] = getJReturnBut(value[0]);
    if(getJParameterValue(iparam) == HD_LAST_BUTTONS)
        value[0] = getJReturnBut(value[0]);
    if(getJParameterValue(iparam) == HD_CALIBRATION_STYLE)
        value[0] = getJReturnCal(value[0]);

    jintArray result = (*eparam)->NewIntArray(eparam, num);
    (*eparam)->SetIntArrayRegion(eparam, result, 0, num, (jint *)value);

    return result;
}

/*
 * Class:     HDAPI
 * Method:    hdGetFloatv
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetFloatv
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    int num = getJArrayLength(iparam);

    HDfloat value[num];
    hdGetFloatv(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jfloatArray result = (*eparam)->NewFloatArray(eparam, num);
    (*eparam)->SetFloatArrayRegion(eparam, result, 0, num, value);

	return result;
}

/*
 * Class:     HDAPI
 * Method:    hdGetDoublev
 * Signature: (I)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetDoublev
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    int num = getJArrayLength(iparam);

    HDdouble value[num];
    hdGetDoublev(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if(HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jdoubleArray result = (*eparam)->NewDoubleArray(eparam, num);
    (*eparam)->SetDoubleArrayRegion(eparam, result, 0, num, value);

	return result;
}

/*
 * Class:     HDAPI
 * Method:    hdGetLongv
 * Signature: (I)[J
 */
JNIEXPORT jlongArray JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetLongv
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    int num = getJArrayLength(iparam);

    HDulong value[num];
    hdGetLongv(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if(HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jlongArray result = (*eparam)->NewLongArray(eparam, num);
    (*eparam)->SetLongArrayRegion(eparam, result, 0, num, (jlong *)value);

	return result;
}

/*
 * Class:     HDAPI
 * Method:    hdGetCurrentDevice
 * Signature: ()LHDAPI/HHD;
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetCurrentDevice
  (JNIEnv *eparam, jclass cparam)
{
    HHD hHD = hdGetCurrentDevice();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jint result = hHD;
	return result;
}

/*
 * Class:     HDAPI
 * Method:    hdGetString
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetString
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    const char *value = hdGetString(getJParameterValue(iparam));

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jstring result = (*eparam)->NewStringUTF(eparam, (char *)value);
	return result;
}

/*
 * Class:     HDAPI
 * Method:    hdInitDevice
 * Signature: (Ljava/lang/String;)LHDAPI/HHD;
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdInitDevice
  (JNIEnv *eparam, jclass cparam, jstring sparam)
{
	const char *str = (*eparam)->GetStringUTFChars( eparam, sparam, 0 );

    HHD hHD = hdInitDevice(str);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

	return hHD;
}

/*
 * Class:     HDAPI
 * Method:    hdIsDevice
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdIsEnabled
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    HDboolean value = hdIsEnabled(getJParameterValue(iparam));

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jboolean result = value;
	return result;
}

/*
 * Class:     HDAPI
 * Method:    hdMakeCurrentDevice
 * Signature: (LHDAPI/HHD;)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdMakeCurrentDevice
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    hdMakeCurrentDevice(iparam);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdSetBooleanv
 * Signature: (I[Z)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetBooleanv
  (JNIEnv *eparam, jclass cparam, jint iparam, jbooleanArray baparam)
{
    char *barray = (*eparam)->GetBooleanArrayElements(eparam, baparam, 0);

    HDboolean *value = barray;
    hdSetBooleanv(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdSetIntegerv
 * Signature: (I[I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetIntegerv
  (JNIEnv *eparam, jclass cparam, jint iparam, jintArray iaparam)
{
    jint *iarray = (*eparam)->GetIntArrayElements(eparam, iaparam, 0);

    HDuint *value = (int *)iarray;
    hdSetIntegerv(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdSetFloatv
 * Signature: (I[F)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetFloatv
  (JNIEnv *eparam, jclass cparam, jint iparam, jfloatArray faparam)
{
    jfloat *farray = (*eparam)->GetFloatArrayElements(eparam, faparam, 0);

    HDfloat *value = farray;
    hdSetFloatv(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdSetDoublev
 * Signature: (I[D)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetDoublev
  (JNIEnv *eparam, jclass cparam, jint iparam, jdoubleArray daparam)
{
    jdouble *darray = (*eparam)->GetDoubleArrayElements(eparam, daparam, 0);

    HDdouble *value = darray;
    hdSetDoublev(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdSetLongv
 * Signature: (I[J)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetLongv
  (JNIEnv *eparam, jclass cparam, jint iparam, jlongArray laparam)
{
    jlong *larray = (*eparam)->GetLongArrayElements(eparam, laparam, 0);

    HDlong *value = (long *)larray;
    hdSetLongv(getJParameterValue(iparam), value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdCheckCalibration
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdCheckCalibration
  (JNIEnv *eparam, jclass cparam)
{
    int value = hdCheckCalibration();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jint result = -1;

    result = getJReturnCal(value);

    /*
    if(value == HD_CALIBRATION_OK)
        result = 20;
    else if(value == HD_CALIBRATION_NEEDS_UPDATE)
        result = 21;
    else if(value == HD_CALIBRATION_NEEDS_MANUAL_INPUT)
        result = 22;
    */

	return result;
}

/*
 * Class:     HDAPI
 * Method:    hdUpdateCalibration
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdUpdateCalibration
  (JNIEnv *eparam, jclass cparam, jint iparam)
{
    HDint value = iparam;
    hdUpdateCalibration(iparam);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdGetSchedulerTimeStamp
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdGetSchedulerTimeStamp
  (JNIEnv *eparam, jclass cparam)
{
    HDdouble d = hdGetSchedulerTimeStamp();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

    jdouble result = d;
	return result;
}

/*
 * Class:     HDAPI
 * Method:    hdScheduleAsynchronous
 * Signature: (Ljava/lang/String;[Ljava/lang/Object;I)V
 */
JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdScheduleAsynchronous
  (JNIEnv *eparam, jclass cparam, jobject sparam, jobjectArray oaparam, jshort iparam)
{
    HDushort priority= iparam;

    HDcallbackObj callObject;
        callObject.obj = (*eparam)->NewGlobalRef(eparam,sparam);
        callObject.data = (*eparam)->NewGlobalRef(eparam,oaparam);

    if(callobjPt < callobjSize)
    {
        callobj[callobjPt] = callObject;

    //jclass c = (*eparam)->GetObjectClass(eparam, sparam);
    //jmethodID con = (*eparam)->GetMethodID(eparam, c, "HD_Callback", "([Ljava/lang/Object;)I");
    //jint param = (*eparam)->CallIntMethod(eparam, sparam, con, oaparam);

        handler[callobjPt] = hdScheduleAsynchronous(myCallback, &callobj[callobjPt], priority);
        callobjPt++;
    }

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);

        return callobjPt-1;
}

/*
 * Class:     HDAPI
 * Method:    hdScheduleSynchronous
 * Signature: (Ljava/lang/String;[Ljava/lang/Object;I)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdScheduleSynchronous
  (JNIEnv *eparam, jclass cparam, jobject sparam, jobjectArray oaparam, jshort iparam)
{
    HDushort priority= iparam;

    HDcallbackObj callObject;
        callObject.obj = (*eparam)->NewGlobalRef(eparam,sparam);
        callObject.data = (*eparam)->NewGlobalRef(eparam,oaparam);

    if(callobjPt < callobjSize)
    {
        callobj[callobjPt] = callObject;

        hdScheduleSynchronous(myCallback, &callobj[callobjPt], priority);
        callobjPt++;
    }

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdSetSchedulerRate
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdSetSchedulerRate
  (JNIEnv *eparam, jclass cparam, jlong lparam)
{
    HDulong value = lparam;
    hdSetSchedulerRate(value);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdStartScheduler
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdStartScheduler
  (JNIEnv *eparam, jclass cparam)
{
    hdStartScheduler();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdStopScheduler
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdStopScheduler
  (JNIEnv *eparam, jclass cparam)
{
    hdStopScheduler();

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdUnschedule
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdUnschedule
  (JNIEnv *eparam, jclass cparam, jint hand)
{
    printf("sdfasgdshghjyhjkjhklkjl;lk;'gnsxdfghsdfgfhjghjhgkhjkfkdfjh");

    hdUnschedule(handler[hand]);

    HDErrorInfo error;
    if (HD_DEVICE_ERROR(error = hdGetError()))
        sendException(error, eparam);
}

/*
 * Class:     HDAPI
 * Method:    hdWaitForCompletion
 * Signature: (Ljava/lang/String;I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HD_HDAPI_hdWaitForCompletion
  (JNIEnv *eparam, jclass cparam, jint hand, jint iparam)
{
    jboolean value = JNI_FALSE;
    int par = getJParameterValue(iparam);

    if(handler[hand] != -1)
        value = hdWaitForCompletion(handler[hand], par);

    jboolean result = value;
	return result;
}

#ifdef __cplusplus
}
#endif
#endif

