#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_tct_mysudoku_sudoku_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Welcome Sudoku";
    return env->NewStringUTF(hello.c_str());
}
