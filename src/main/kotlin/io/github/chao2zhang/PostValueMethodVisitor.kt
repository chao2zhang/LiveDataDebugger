package io.github.chao2zhang

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*


/**
 *  logValue("postValue", value);
 */
class PostValueMethodVisitor(
    private val methodVisitor: MethodVisitor
) : MethodVisitor(ASM9, methodVisitor) {

    var tryCatchBlockCount = 0

    override fun visitTryCatchBlock(start: Label?, end: Label?, handler: Label?, type: String?) {
        super.visitTryCatchBlock(start, end, handler, type)
        ++tryCatchBlockCount
        if (tryCatchBlockCount == 2) {
            methodVisitor.visitVarInsn(ALOAD, 0)
            methodVisitor.visitLdcInsn("postValue")
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitMethodInsn(
                INVOKESPECIAL,
                "androidx/lifecycle/LiveData",
                "logValue",
                "(Ljava/lang/String;Ljava/lang/Object;)V",
                false
            )
        }
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        methodVisitor.visitMaxs(3, 5)
    }
}