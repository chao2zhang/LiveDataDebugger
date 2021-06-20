package io.github.chao2zhang

import com.android.build.api.instrumentation.*
import org.objectweb.asm.ClassVisitor

@Suppress("UnstableApiUsage")
abstract class LiveDataClassVisitorFactory : AsmClassVisitorFactory<InstrumentationParameters> {

    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor) =
        LiveDataClassVisitor(nextClassVisitor)

    override fun isInstrumentable(classData: ClassData) =
        classData.className == "androidx.lifecycle.LiveData"
}