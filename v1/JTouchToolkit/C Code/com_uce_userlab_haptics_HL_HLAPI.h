/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_uce_userlab_haptics_HL_HLAPI */

#ifndef _Included_com_uce_userlab_haptics_HL_HLAPI
#define _Included_com_uce_userlab_haptics_HL_HLAPI
#ifdef __cplusplus
extern "C" {
#endif
	/*
#undef com_uce_userlab_haptics_HL_HLAPI_HL_STIFFNESS
#define com_uce_userlab_haptics_HL_HLAPI_HL_STIFFNESS 1L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_DAMPING
#define com_uce_userlab_haptics_HL_HLAPI_HL_DAMPING 2L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_STATIC_FRICTION
#define com_uce_userlab_haptics_HL_HLAPI_HL_STATIC_FRICTION 3L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_DYNAMIC_FRICTION
#define com_uce_userlab_haptics_HL_HLAPI_HL_DYNAMIC_FRICTION 4L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_TOUCHABLE_FACE
#define com_uce_userlab_haptics_HL_HLAPI_HL_TOUCHABLE_FACE 5L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_FRONT
#define com_uce_userlab_haptics_HL_HLAPI_HL_FRONT 6L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_BACK
#define com_uce_userlab_haptics_HL_HLAPI_HL_BACK 7L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_FRONT_AND_BACK
#define com_uce_userlab_haptics_HL_HLAPI_HL_FRONT_AND_BACK 8L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_TOUCH_MODEL
#define com_uce_userlab_haptics_HL_HLAPI_HL_TOUCH_MODEL 9L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_CONTACT
#define com_uce_userlab_haptics_HL_HLAPI_HL_CONTACT 10L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_CONSTRAINT
#define com_uce_userlab_haptics_HL_HLAPI_HL_CONSTRAINT 11L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_SNAP_DISTANCE
#define com_uce_userlab_haptics_HL_HLAPI_HL_SNAP_DISTANCE 12L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_RESOLUTION
#define com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_RESOLUTION 13L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_HAPTIC_CAMERA_VIEW
#define com_uce_userlab_haptics_HL_HLAPI_HL_HAPTIC_CAMERA_VIEW 14L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_ADAPTIVE_VIEWPORT
#define com_uce_userlab_haptics_HL_HLAPI_HL_ADAPTIVE_VIEWPORT 15L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_FEEDBACK_BUFFER
#define com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_FEEDBACK_BUFFER 16L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_DEPTH_BUFFER
#define com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_DEPTH_BUFFER 17L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_CALLBACK
#define com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_CALLBACK 18L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_IS_TOUCHING
#define com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_IS_TOUCHING 19L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_TOUCH_NORMAL
#define com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_TOUCH_NORMAL 20L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_REACTION_FORCE
#define com_uce_userlab_haptics_HL_HLAPI_HL_REACTION_FORCE 21L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_FEEDBACk_BUFFER_VERTICES
#define com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_FEEDBACk_BUFFER_VERTICES 22L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_DYNAMIC_SURFACE_CHANGE
#define com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_DYNAMIC_SURFACE_CHANGE 23L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_VIEWTOUCH
#define com_uce_userlab_haptics_HL_HLAPI_HL_VIEWTOUCH 24L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_TOUCHWORKSPACE
#define com_uce_userlab_haptics_HL_HLAPI_HL_TOUCHWORKSPACE 25L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_VIEWTOUCH_MATRIX
#define com_uce_userlab_haptics_HL_HLAPI_HL_VIEWTOUCH_MATRIX 26L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_TOUCHWORKSPACE_MATRIX
#define com_uce_userlab_haptics_HL_HLAPI_HL_TOUCHWORKSPACE_MATRIX 27L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_WORKSPACE
#define com_uce_userlab_haptics_HL_HLAPI_HL_WORKSPACE 28L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_MAX_WORKSPACE_DIMS
#define com_uce_userlab_haptics_HL_HLAPI_HL_MAX_WORKSPACE_DIMS 29L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_POSITION
#define com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_POSITION 30L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_ROTATION
#define com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_ROTATION 31L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_TRANSFORM
#define com_uce_userlab_haptics_HL_HLAPI_HL_PROXY_TRANSFORM 32L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_POSITION
#define com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_POSITION 33L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_ROTATION
#define com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_ROTATION 34L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_TRANSFORM
#define com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_TRANSFORM 35L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_FORCE
#define com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_FORCE 36L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_TORQUE
#define com_uce_userlab_haptics_HL_HLAPI_HL_DEVICE_TORQUE 37L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_BUTTON1_STATE
#define com_uce_userlab_haptics_HL_HLAPI_HL_BUTTON1_STATE 38L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_BUTTON2_STATE
#define com_uce_userlab_haptics_HL_HLAPI_HL_BUTTON2_STATE 39L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_MOTION
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_MOTION 40L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_1BUTTONDOWN
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_1BUTTONDOWN 41L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_1BUTTONUP
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_1BUTTONUP 42L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_2BUTTONDOWN
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_2BUTTONDOWN 43L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_2BUTTONUP
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_2BUTTONUP 44L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_TOUCH
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_TOUCH 45L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_UNTOUCH
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_UNTOUCH 46L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_CALIBRATION_UPDATE
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_CALIBRATION_UPDATE 47L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_CALIBRATION_INPUT
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_CALIBRATION_INPUT 48L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_MOTION_LINEAR_TOLERANCE
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_MOTION_LINEAR_TOLERANCE 49L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_MOTION_ANGULAR_TOLERANCE
#define com_uce_userlab_haptics_HL_HLAPI_HL_EVENT_MOTION_ANGULAR_TOLERANCE 50L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_OBJECT_ANY
#define com_uce_userlab_haptics_HL_HLAPI_HL_OBJECT_ANY 51L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_INTERSECT_LS
#define com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_INTERSECT_LS 52L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_CLOSEST_FEATURES
#define com_uce_userlab_haptics_HL_HLAPI_HL_SHAPE_CLOSEST_FEATURES 53L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_LOCAL_FEATURE_POINT
#define com_uce_userlab_haptics_HL_HLAPI_HL_LOCAL_FEATURE_POINT 54L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_LOCAL_FEATURE_LINE
#define com_uce_userlab_haptics_HL_HLAPI_HL_LOCAL_FEATURE_LINE 55L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_LOCAL_FEATURE_PLANE
#define com_uce_userlab_haptics_HL_HLAPI_HL_LOCAL_FEATURE_PLANE 56L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_CLIENT_THREAD
#define com_uce_userlab_haptics_HL_HLAPI_HL_CLIENT_THREAD 57L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_COLLISION_THREAD
#define com_uce_userlab_haptics_HL_HLAPI_HL_COLLISION_THREAD 58L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_CALLBACK
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_CALLBACK 59L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_CONSTANT
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_CONSTANT 60L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_SPRING
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_SPRING 61L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_VISCOUS
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_VISCOUS 62L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_FRICTION
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_FRICTION 63L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_TYPE
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_TYPE 64L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_GAIN
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_GAIN 65L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_MAGNITUDE
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_MAGNITUDE 66L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_FREQUENCY
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_FREQUENCY 67L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_DURATION
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_DURATION 68L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_POSITION
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_POSITION 69L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_DIRECTION
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_PROPERTY_DIRECTION 70L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_COMPUTE_FORCE
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_COMPUTE_FORCE 71L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_START
#define com_uce_userlab_haptics_HL_HLAPI_HL_EFFECT_START 72L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_STOP
#define com_uce_userlab_haptics_HL_HLAPI_HL_STOP 73L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_VERSION
#define com_uce_userlab_haptics_HL_HLAPI_HL_VERSION 74L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_VENDOR
#define com_uce_userlab_haptics_HL_HLAPI_HL_VENDOR 75L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_VERSION_MAJOR_NUMBER
#define com_uce_userlab_haptics_HL_HLAPI_HL_VERSION_MAJOR_NUMBER 76L
#undef com_uce_userlab_haptics_HL_HLAPI_HL_VERSION_MINOR_NUMBER
#define com_uce_userlab_haptics_HL_HLAPI_HL_VERSION_MINOR_NUMBER 77L
*/
/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlBeginFrame
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlBeginFrame
  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlContextDevice
 * Signature: (I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlContextDevice
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCreateContext
 * Signature: (I)I
 */
//JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCreateContext
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlDeleteContext
 * Signature: (I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlDeleteContext
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEndFrame
 * Signature: ()V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEndFrame
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetCurrentContext
 * Signature: ()I
 */
//JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetCurrentContext
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetCurrentDevice
 * Signature: ()I
 */
//JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetCurrentDevice
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMakeCurrent
 * Signature: (II)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMakeCurrent
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEnable
 * Signature: (I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEnable
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlDisable
 * Signature: (I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlDisable
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetBooleanv
 * Signature: (I)[Z
 */
//JNIEXPORT jbooleanArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetBooleanv
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetDoublev
 * Signature: (I)[D
 */
//JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetDoublev
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetIntegerv
 * Signature: (I)[I
 */
//JNIEXPORT jintArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetIntegerv
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetString
 * Signature: (I)Ljava/lang/String;
 */
//JNIEXPORT jstring JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetString
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlHinti
 * Signature: (II)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlHinti
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlHintb
 * Signature: (IZ)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlHintb
//  (JNIEnv *, jclass, jint, jboolean);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlIsEnabled
 * Signature: (I)Z
 */
//JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlIsEnabled
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCacheGetBooleanv
 * Signature: (Ljava/lang/Object;I)[Z
 */
//JNIEXPORT jbooleanArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCacheGetBooleanv
//  (JNIEnv *, jclass, jobject, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCacheGetDoublev
 * Signature: (Ljava/lang/Object;I)[D
 */
//JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCacheGetDoublev
//  (JNIEnv *, jclass, jobject, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlBeginShape
 * Signature: (II)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlBeginShape
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlDeleteShapes
 * Signature: (II)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlDeleteShapes
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEndShape
 * Signature: ()V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEndShape
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGenShape
 * Signature: ()I
 */
//JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGenShape
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGenShapes
 * Signature: (I)[I
 */
//JNIEXPORT jintArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGenShapes
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLocalFeature1fv
 * Signature: ([II[F)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLocalFeature1fv
//  (JNIEnv *, jclass, jintArray, jint, jfloatArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLocalFeature1dv
 * Signature: ([II[D)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLocalFeature1dv
//  (JNIEnv *, jclass, jintArray, jint, jdoubleArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLocalFeature2fv
 * Signature: ([II[F[F)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLocalFeature2fv
//  (JNIEnv *, jclass, jintArray, jint, jfloatArray, jfloatArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLocalFeature2dv
 * Signature: ([II[D[D)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLocalFeature2dv
//  (JNIEnv *, jclass, jintArray, jint, jdoubleArray, jdoubleArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlIsShape
 * Signature: (I)Z
 */
//JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlIsShape
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetShapeBooleanv
 * Signature: (II)[Z
 */
//JNIEXPORT jbooleanArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetShapeBooleanv
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetShapeDoublev
 * Signature: (II)[D
 */
//JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetShapeDoublev
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetMaterialfv
 * Signature: (II)[F
 */
//JNIEXPORT jfloatArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetMaterialfv
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMaterialf
 * Signature: (IIF)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMaterialf
//  (JNIEnv *, jclass, jint, jint, jfloat);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTouchableFace
 * Signature: (I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTouchableFace
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTouchModel
 * Signature: (I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTouchModel
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTouchModelf
 * Signature: (IF)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTouchModelf
//  (JNIEnv *, jclass, jint, jfloat);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlDeleteEffects
 * Signature: (II)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlDeleteEffects
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEffectd
 * Signature: (ID)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEffectd
//  (JNIEnv *, jclass, jint, jdouble);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEffecti
 * Signature: (II)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEffecti
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEffectdv
 * Signature: (I[D)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEffectdv
//  (JNIEnv *, jclass, jint, jdoubleArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEffectiv
 * Signature: (I[I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEffectiv
//  (JNIEnv *, jclass, jint, jintArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGenEffect
 * Signature: ()I
 */
//JNIEXPORT jint JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGenEffect
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGenEffects
 * Signature: (I)[I
 */
//JNIEXPORT jintArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGenEffects
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetEffectdv
 * Signature: (II)[D
 */
//JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetEffectdv
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlGetEffectiv
 * Signature: (II)[D
 */
//JNIEXPORT jdoubleArray JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlGetEffectiv
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlIsEffect
 * Signature: (I)Z
 */
//JNIEXPORT jboolean JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlIsEffect
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlStartEffect
 * Signature: (II)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlStartEffect
//  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlStopEffect
 * Signature: (I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlStopEffect
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTriggerEffect
 * Signature: (I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTriggerEffect
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlProxydv
 * Signature: (I[D)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlProxydv
//  (JNIEnv *, jclass, jint, jdoubleArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLoadIdentity
 * Signature: ()V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLoadIdentity
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLoadMatrixd
 * Signature: ([D)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLoadMatrixd
//  (JNIEnv *, jclass, jdoubleArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlLoadMatrixf
 * Signature: ([F)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlLoadMatrixf
//  (JNIEnv *, jclass, jfloatArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMultMatrixd
 * Signature: ([D)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMultMatrixd
//  (JNIEnv *, jclass, jdoubleArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMultMatrixf
 * Signature: ([F)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMultMatrixf
//  (JNIEnv *, jclass, jfloatArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlMatrixMode
 * Signature: (I)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlMatrixMode
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlOrtho
 * Signature: (DDDDDD)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlOrtho
//  (JNIEnv *, jclass, jdouble, jdouble, jdouble, jdouble, jdouble, jdouble);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlPushMatrix
 * Signature: ()V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlPushMatrix
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlPopMatrix
 * Signature: ()V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlPopMatrix
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlRotated
 * Signature: (DDDD)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlRotated
//  (JNIEnv *, jclass, jdouble, jdouble, jdouble, jdouble);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlRotatef
 * Signature: (FFFF)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlRotatef
//  (JNIEnv *, jclass, jfloat, jfloat, jfloat, jfloat);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlScaled
 * Signature: (DDD)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlScaled
//  (JNIEnv *, jclass, jdouble, jdouble, jdouble);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlScalef
 * Signature: (FFF)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlScalef
//  (JNIEnv *, jclass, jfloat, jfloat, jfloat);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTranslated
 * Signature: (DDD)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTranslated
//  (JNIEnv *, jclass, jdouble, jdouble, jdouble);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlTranslatef
 * Signature: (FFF)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlTranslatef
//  (JNIEnv *, jclass, jfloat, jfloat, jfloat);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlWorkspace
 * Signature: (DDDDDD)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlWorkspace
//  (JNIEnv *, jclass, jdouble, jdouble, jdouble, jdouble, jdouble, jdouble);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlUpdateCalibration
 * Signature: ()V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlUpdateCalibration
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCallback
 * Signature: (Lcom/uce/userlab/haptics/HL/HLCallback;[Ljava/lang/Object;)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCallback
//  (JNIEnv *, jclass, jobject, jobjectArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlAddEventCallback
 * Signature: (ILcom/uce/userlab/haptics/HL/HLCallback;[Ljava/lang/Object;)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlAddEventCallback
//  (JNIEnv *, jclass, jint, jobject, jobjectArray);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlRemoveEventCallback
 * Signature: (ILcom/uce/userlab/haptics/HL/HLEventCallback;)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlRemoveEventCallback
//  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlCheckEvents
 * Signature: ()V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlCheckEvents
//  (JNIEnv *, jclass);

/*
 * Class:     com_uce_userlab_haptics_HL_HLAPI
 * Method:    hlEventd
 * Signature: (ID)V
 */
//JNIEXPORT void JNICALL Java_com_uce_userlab_haptics_HL_HLAPI_hlEventd
//  (JNIEnv *, jclass, jint, jdouble);

#ifdef __cplusplus
}
#endif
#endif
