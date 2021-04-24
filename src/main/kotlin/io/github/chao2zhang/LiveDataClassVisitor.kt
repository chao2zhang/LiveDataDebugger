package io.github.chao2zhang

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*


class LiveDataClassVisitor(
    private val classVisitor: ClassVisitor
) : ClassVisitor(ASM9, classVisitor) {

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        visitLogValueMethod(classVisitor.visitMethod(
            ACC_PRIVATE,
            "logValue",
            "(Ljava/lang/String;Ljava/lang/Object;)V",
            "(Ljava/lang/String;TT;)V",
            null
        ))
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = classVisitor.visitMethod(access, name, descriptor, signature, exceptions)
        return when (name) {
            "considerNotify" -> ConsiderNotifyMethodVisitor(methodVisitor)
            "postValue" -> PostValueMethodVisitor(methodVisitor)
            "setValue" -> SetValueMethodVisitor(methodVisitor)
            else -> methodVisitor
        }
    }
}