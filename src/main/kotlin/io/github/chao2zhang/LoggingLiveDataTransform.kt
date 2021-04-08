package io.github.chao2zhang

import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Status
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.variant.VariantInfo
import org.gradle.api.logging.Logger
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.util.TraceClassVisitor
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

@Suppress("UnstableApiUsage")
class LoggingLiveDataTransform(private val logger: Logger) : Transform() {

    override fun getName(): String = javaClass.simpleName

    override fun applyToVariant(variant: VariantInfo) = variant.isDebuggable

    override fun getInputTypes(): Set<QualifiedContent.ContentType> =
        setOf(QualifiedContent.DefaultContentType.CLASSES)

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> =
        mutableSetOf(QualifiedContent.Scope.EXTERNAL_LIBRARIES)

    override fun isIncremental(): Boolean = true

    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)
        transformInvocation.inputs.forEach {
            transform(transformInvocation, it)
        }
    }

    private fun transform(transformInvocation: TransformInvocation, transformInput: TransformInput) {
        transformInput.jarInputs.forEach { jarInput ->
            val inputJar = jarInput.file
            val outputJar = transformInvocation.outputProvider.getContentLocation(
                jarInput.name,
                jarInput.contentTypes,
                jarInput.scopes,
                Format.JAR
            )
            if (transformInvocation.isIncremental) {
                when (jarInput.status) {
                    Status.ADDED, Status.CHANGED -> transformJarInput(jarInput, inputJar, outputJar)
                    Status.REMOVED -> outputJar.delete()
                    else -> { }
                }
            } else {
                transformJarInput(jarInput, inputJar, outputJar)
            }
        }
    }

    private fun transformJarInput(jarInput: JarInput, inputJar: File, outputJar: File) {
        if (jarInput.isLiveDataJarInput()) {
            transformLiveDataJar(inputJar, outputJar)
        } else {
            inputJar.copyTo(outputJar, overwrite = true)
        }
    }

    private fun transformLiveDataJar(inputJar: File, outputJar: File) {
        val inputZip = ZipFile(inputJar)
        val outputZip = ZipOutputStream(BufferedOutputStream(FileOutputStream(outputJar)))
        val inputEntries = inputZip.entries()
        while (inputEntries.hasMoreElements()) {
            val inputEntry = inputEntries.nextElement()
            val inputBytes = inputZip.getInputStream(inputEntry).readAllBytes()
            var outputBytes = inputBytes
            if (inputEntry.isLiveDataClass()) {
                logger.lifecycle("Transforming $inputEntry to add logging statements in LiveData")
                val classReader = ClassReader(inputBytes)
                val classWriter = ClassWriter(classReader, 0)
                val classVisitor = LoggingLiveDataClassVisitor(classWriter)
                classReader.accept(classVisitor, 0)
                outputBytes = classWriter.toByteArray()
            }
            val outputEntry = ZipEntry(inputEntry.name)
            with(outputZip) {
                putNextEntry(outputEntry)
                write(outputBytes)
                closeEntry()
            }
        }
        outputZip.flush()
        outputZip.close()
    }

    private fun JarInput.isLiveDataJarInput(): Boolean =
        name.startsWith("androidx.lifecycle:lifecycle-livedata-core:")

    private fun ZipEntry.isLiveDataClass(): Boolean =
        name == "androidx/lifecycle/LiveData.class"
}