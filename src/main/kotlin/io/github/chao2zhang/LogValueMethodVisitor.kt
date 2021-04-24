package io.github.chao2zhang

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * private void logValue(String methodName, T value) {
 *   StackTraceElement[] traces = Thread.currentThread().getStackTrace();
 *   if (traces.length > 3) {
 *     Log.i("LiveData", methodName + "() called with LiveData = " + this + " Value = " + value + " Caller = " + traces[3].toString());
 *   }
 * }
 */
fun visitLogValueMethod(methodVisitor: MethodVisitor) {
    methodVisitor.visitCode()
    val label0 = Label()
    methodVisitor.visitLabel(label0)
    methodVisitor.visitLineNumber(302, label0)
    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Thread", "currentThread", "()Ljava/lang/Thread;", false)
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/Thread",
        "getStackTrace",
        "()[Ljava/lang/StackTraceElement;",
        false
    )
    methodVisitor.visitVarInsn(Opcodes.ASTORE, 3)
    val label1 = Label()
    methodVisitor.visitLabel(label1)
    methodVisitor.visitLineNumber(303, label1)
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 3)
    methodVisitor.visitInsn(Opcodes.ARRAYLENGTH)
    methodVisitor.visitInsn(Opcodes.ICONST_5)
    val label2 = Label()
    methodVisitor.visitJumpInsn(Opcodes.IF_ICMPLE, label2)
    val label3 = Label()
    methodVisitor.visitLabel(label3)
    methodVisitor.visitLineNumber(304, label3)
    methodVisitor.visitLdcInsn("LiveData")
    methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder")
    methodVisitor.visitInsn(Opcodes.DUP)
    methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 1)
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/StringBuilder",
        "append",
        "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
        false
    )
    methodVisitor.visitLdcInsn("() called with LiveData = ")
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/StringBuilder",
        "append",
        "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
        false
    )
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/StringBuilder",
        "append",
        "(Ljava/lang/Object;)Ljava/lang/StringBuilder;",
        false
    )
    methodVisitor.visitLdcInsn(" Value = ")
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/StringBuilder",
        "append",
        "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
        false
    )
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 2)
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/StringBuilder",
        "append",
        "(Ljava/lang/Object;)Ljava/lang/StringBuilder;",
        false
    )
    methodVisitor.visitLdcInsn(" Caller = ")
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/StringBuilder",
        "append",
        "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
        false
    )
    methodVisitor.visitVarInsn(Opcodes.ALOAD, 3)
    methodVisitor.visitInsn(Opcodes.ICONST_5)
    methodVisitor.visitInsn(Opcodes.AALOAD)
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/StackTraceElement",
        "toString",
        "()Ljava/lang/String;",
        false
    )
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/StringBuilder",
        "append",
        "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
        false
    )
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKEVIRTUAL,
        "java/lang/StringBuilder",
        "toString",
        "()Ljava/lang/String;",
        false
    )
    methodVisitor.visitMethodInsn(
        Opcodes.INVOKESTATIC,
        "android/util/Log",
        "i",
        "(Ljava/lang/String;Ljava/lang/String;)I",
        false
    )
    methodVisitor.visitInsn(Opcodes.POP)
    methodVisitor.visitLabel(label2)
    methodVisitor.visitLineNumber(306, label2)
    methodVisitor.visitFrame(Opcodes.F_APPEND, 1, arrayOf<Any>("[Ljava/lang/StackTraceElement;"), 0, null)
    methodVisitor.visitInsn(Opcodes.RETURN)
    val label4 = Label()
    methodVisitor.visitLabel(label4)
    methodVisitor.visitLocalVariable(
        "this",
        "Landroidx/lifecycle/LiveData;",
        "Landroidx/lifecycle/LiveData<TT;>;",
        label0,
        label4,
        0
    )
    methodVisitor.visitLocalVariable("methodName", "Ljava/lang/String;", null, label0, label4, 1)
    methodVisitor.visitLocalVariable("value", "Ljava/lang/Object;", "TT;", label0, label4, 2)
    methodVisitor.visitLocalVariable("traces", "[Ljava/lang/StackTraceElement;", null, label1, label4, 3)
    methodVisitor.visitMaxs(4, 4)
    methodVisitor.visitEnd()
}