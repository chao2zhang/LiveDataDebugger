package io.github.chao2zhang

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.ASM9

/**
 * Add the following code after invoking `observer.onChanged`:
 * ```
 *     Log.i("LiveData", "considerNotify() called with LiveData = " + this + " Observer = " + observer.mObserver + " Data = " + this.mData);
 * ```
 */
class LoggingLiveDataMethodVisitor(
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
        if (opcode == Opcodes.INVOKEINTERFACE && owner == "androidx/lifecycle/Observer" && name == "onChanged") {
            methodVisitor.visitLdcInsn("LiveData")
            methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder")
            methodVisitor.visitInsn(Opcodes.DUP)
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
            methodVisitor.visitLdcInsn("considerNotify() called with LiveData = ")
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitLdcInsn(" Observer = ")
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 1)
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "androidx/lifecycle/LiveData\$ObserverWrapper", "mObserver", "Landroidx/lifecycle/Observer;")
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitLdcInsn(" Data = ")
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "androidx/lifecycle/LiveData", "mData", "Ljava/lang/Object;")
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false)
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false)
            methodVisitor.visitInsn(Opcodes.POP)
        }
    }


    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        methodVisitor.visitMaxs(3, 2)
    }
}