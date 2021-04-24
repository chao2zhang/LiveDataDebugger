package io.github.chao2zhang

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*


/**
 *  logValue("setValue", value);
 */
class SetValueMethodVisitor(
    private val methodVisitor: MethodVisitor
) : MethodVisitor(ASM9, methodVisitor) {

    override fun visitCode() {
        super.visitCode()
        methodVisitor.visitVarInsn(ALOAD, 0)
        methodVisitor.visitLdcInsn("setValue")
        methodVisitor.visitVarInsn(ALOAD, 1)
        methodVisitor.visitMethodInsn(
            INVOKESPECIAL,
            "androidx/lifecycle/LiveData",
            "logValue",
            "(Ljava/lang/String;Ljava/lang/Object;)V",
            false
        )
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        methodVisitor.visitMaxs(3, 2)
    }
}