#include "stdafx.h"

#include <jni.h>
#include "JHDLU.h"
#include <stdio.h>
#include <hdlu/hdlu.h>
/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDLU
 * Method:    hdluGenerateHapticToAppWorkspaceTransform
 * Signature: ([D[DZ)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_com_bcu_userlab_haptics_HDAL_HDLU_hdluGenerateHapticToAppWorkspaceTransform
(JNIEnv *eparam, jclass cparam, jdoubleArray hapticWorkspace, jdoubleArray gameWorkspace, jboolean useUniformScale) {
	double *hWorksp = eparam->GetDoubleArrayElements(hapticWorkspace, 0);;
	double *gWorksp = eparam->GetDoubleArrayElements(gameWorkspace, 0);;
	
	double trans[16];
	hdluGenerateHapticToAppWorkspaceTransform(hWorksp, gWorksp, useUniformScale, trans);

	jdoubleArray result = eparam->NewDoubleArray(16);
    eparam->SetDoubleArrayRegion(result, 0, 16, trans);

	return result;
}

/*
 * Class:     com_bcu_userlab_haptics_HDAL_HDLU
 * Method:    hdluGetSystemTime
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_bcu_userlab_haptics_HDAL_HDLU_hdluGetSystemTime
(JNIEnv *eparam, jclass cparam){
	return hdluGetSystemTime();
}