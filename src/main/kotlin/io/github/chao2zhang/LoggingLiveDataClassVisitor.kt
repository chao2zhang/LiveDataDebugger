package io.github.chao2zhang

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ASM9

class LoggingLiveDataClassVisitor(
    private val classVisitor: ClassVisitor
) : ClassVisitor(ASM9, classVisitor) {

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = classVisitor.visitMethod(access, name, descriptor, signature, exceptions)
        return if (name == "considerNotify") {
            LoggingLiveDataMethodVisitor(methodVisitor)
        } else {
            methodVisitor
        }
    }
}