package io.github.chao2zhang

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*

/**
 * Add the following code after invoking `observer.onChanged`:
 * ```
 *     Log.i("LiveData", "considerNotify() called with LiveData = " + this + " Observer = " + observer.mObserver + " Data = " + this.mData);
 * ```
 */
class ConsiderNotifyMethodVisitor(
    private val methodVisitor: MethodVisitor
) : MethodVisitor(ASM9, methodVisitor) {

    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        name: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {
        methodVisitor.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
        if (opcode == INVOKEINTERFACE && owner == "androidx/lifecycle/Observer" && name == "onChanged") {
            methodVisitor.visitLdcInsn("LiveData")
            methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder")
            methodVisitor.visitInsn(DUP)
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
            methodVisitor.visitLdcInsn("considerNotify() called with LiveData = ")
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitVarInsn(ALOAD, 0)
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitLdcInsn(" Observer = ")
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitVarInsn(ALOAD, 1)
            methodVisitor.visitFieldInsn(GETFIELD, "androidx/lifecycle/LiveData\$ObserverWrapper", "mObserver", "Landroidx/lifecycle/Observer;")
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitLdcInsn(" Data = ")
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitVarInsn(ALOAD, 0)
            methodVisitor.visitFieldInsn(GETFIELD, "androidx/lifecycle/LiveData", "mData", "Ljava/lang/Object;")
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
            methodVisitor.visitMethodInsn(INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false)
            methodVisitor.visitInsn(POP)
        }
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        methodVisitor.visitMaxs(3, 2)
    }
}