package ru.liner.decorative.utils;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Quaternion;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GlStateManager {

    
    private static final FloatBuffer BUF_FLOAT_16 = BufferUtils.createFloatBuffer(16);

    
    private static final FloatBuffer BUF_FLOAT_4 = BufferUtils.createFloatBuffer(4);

    
    private static AplhaState alphaState = new AplhaState();

    
    private static LightState lightingState = new LightState(2896);

    
    private static LightState[] lightState = new LightState[8];

    
    private static ColorMaterialState colorMaterialState;

    
    private static BlendState blendState;

    
    private static DepthState depthState;

    
    private static FogState fogState;

    
    private static CullState cullState;

    
    private static PolyOffsetState polygonOffsetState;

    
    private static ColorLogicState colorLogicState;

    
    private static TexGenState texGenState;

    
    private static ClearState clearState;

    
    private static StensilState stencilState;

    
    private static LightState normalizeState;

    
    private static int activeTextureUnit;

    
    private static TextureState[] textureState;

    
    private static int activeShadeModel;

    
    private static LightState rescaleNormalState;

    
    private static ColorMaskState colorMaskState;

    
    private static ColorState colorState;

    
    
    public enum u {
        S,
        T,
        R,
        Q
    }

    static {
        for (int i2 = 0; i2 < 8; i2++) {
            lightState[i2] = new LightState(16384 + i2);
        }
        colorMaterialState = new ColorMaterialState();
        blendState = new BlendState();
        depthState = new DepthState();
        fogState = new FogState();
        cullState = new CullState();
        polygonOffsetState = new PolyOffsetState();
        colorLogicState = new ColorLogicState();
        texGenState = new TexGenState();
        clearState = new ClearState();
        stencilState = new StensilState();
        normalizeState = new LightState(2977);
        activeTextureUnit = 0;
        textureState = new TextureState[8];
        for (int i3 = 0; i3 < 8; i3++) {
            textureState[i3] = new TextureState();
        }
        activeShadeModel = 7425;
        rescaleNormalState = new LightState(32826);
        colorMaskState = new ColorMaskState();
        colorState = new ColorState();
    }

    
    public static void pushAttrib() {
        GL11.glPushAttrib(8256);
    }

    
    public static void popAttrib() {
        GL11.glPopAttrib();
    }

    
    public static void disableAlpha() {
        alphaState.alphaTest.setDisabled();
    }

    
    public static void enableAlpha() {
        alphaState.alphaTest.setEnabled();
    }

    
    public static void alphaFunc(int i2, float f2) {
        if (i2 != alphaState.func || f2 != alphaState.ref) {
            alphaState.func = i2;
            alphaState.ref = f2;
            GL11.glAlphaFunc(i2, f2);
        }
    }

    
    public static void enableLighting() {
        lightingState.setEnabled();
    }

    
    public static void disableLighting() {
        lightingState.setDisabled();
    }

    
    public static void enableLight(int i2) {
        lightState[i2].setEnabled();
    }

    
    public static void disableLight(int i2) {
        lightState[i2].setDisabled();
    }

    
    public static void enableColorMaterial() {
        colorMaterialState.colorMaterial.setEnabled();
    }

    
    public static void disableColorMaterial() {
        colorMaterialState.colorMaterial.setDisabled();
    }

    
    public static void colorMaterial(int i2, int i3) {
        if (i2 != colorMaterialState.face || i3 != colorMaterialState.mode) {
            colorMaterialState.face = i2;
            colorMaterialState.mode = i3;
            GL11.glColorMaterial(i2, i3);
        }
    }

    
    public static void glLight(int i2, int i3, FloatBuffer floatBuffer) {
        GL11.glLight(i2, i3, floatBuffer);
    }

    
    public static void glLightModel(int i2, FloatBuffer floatBuffer) {
        GL11.glLightModel(i2, floatBuffer);
    }

    
    public static void glNormal3f(float f2, float f3, float f4) {
        GL11.glNormal3f(f2, f3, f4);
    }

    
    public static void disableDepth() {
        depthState.depthTest.setDisabled();
    }

    
    public static void enableDepth() {
        depthState.depthTest.setEnabled();
    }

    
    public static void depthFunc(int i2) {
        if (i2 != depthState.depthFunc) {
            depthState.depthFunc = i2;
            GL11.glDepthFunc(i2);
        }
    }

    
    public static void depthMask(boolean z) {
        if (z != depthState.maskEnabled) {
            depthState.maskEnabled = z;
            GL11.glDepthMask(z);
        }
    }

    
    public static void disableBlend() {
        blendState.blend.setDisabled();
    }

    
    public static void enableBlend() {
        blendState.blend.setEnabled();
    }

    
    public static void blendFunc(r rVar, l lVar) {
        blendFunc(rVar.field_187395_p, lVar.field_187345_o);
    }

    
    public static void blendFunc(int i2, int i3) {
        if (i2 != blendState.srcFactor || i3 != blendState.dstFactor) {
            blendState.srcFactor = i2;
            blendState.dstFactor = i3;
            GL11.glBlendFunc(i2, i3);
        }
    }

    
    public static void tryBlendFuncSeparate(r rVar, l lVar, r rVar2, l lVar2) {
        tryBlendFuncSeparate(rVar.field_187395_p, lVar.field_187345_o, rVar2.field_187395_p, lVar2.field_187345_o);
    }

    
    public static void tryBlendFuncSeparate(int i2, int i3, int i4, int i5) {
        if (i2 != blendState.srcFactor || i3 != blendState.dstFactor || i4 != blendState.srcFactorAlpha || i5 != blendState.dstFactorAlpha) {
            blendState.srcFactor = i2;
            blendState.dstFactor = i3;
            blendState.srcFactorAlpha = i4;
            blendState.dstFactorAlpha = i5;
            glBlendFunc(i2, i3, i4, i5);
        }
    }


    public static void glBlendFunc(int i, int i2, int i3, int i4) {
        GL11.glBlendFunc(i, i2);
    }


    public static void glBlendEquation(int i2) {
        GL14.glBlendEquation(i2);
    }

    
    public static void func_187431_e(int i2) {
        BUF_FLOAT_4.put(0, ((i2 >> 16) & 255) / 255.0f);
        BUF_FLOAT_4.put(1, ((i2 >> 8) & 255) / 255.0f);
        BUF_FLOAT_4.put(2, ((i2 >> 0) & 255) / 255.0f);
        BUF_FLOAT_4.put(3, ((i2 >> 24) & 255) / 255.0f);
        glTexEnv(8960, 8705, BUF_FLOAT_4);
        glTexEnvi(8960, 8704, 34160);
        glTexEnvi(8960, 34161, 7681);
        glTexEnvi(8960, 34176, 34166);
        glTexEnvi(8960, 34192, 768);
        glTexEnvi(8960, 34162, 7681);
        glTexEnvi(8960, 34184, 5890);
        glTexEnvi(8960, 34200, 770);
    }

    
    public static void func_187417_n() {
        glTexEnvi(8960, 8704, 8448);
        glTexEnvi(8960, 34161, 8448);
        glTexEnvi(8960, 34162, 8448);
        glTexEnvi(8960, 34176, 5890);
        glTexEnvi(8960, 34184, 5890);
        glTexEnvi(8960, 34192, 768);
        glTexEnvi(8960, 34200, 770);
    }

    
    
    public enum m {
        LINEAR(9729),
        EXP(2048),
        EXP2(2049);


        
        public final int field_187351_d;

        m(int i) {
            this.field_187351_d = i;
        }
    }

    
    public static void enableFog() {
        fogState.fog.setEnabled();
    }

    
    public static void disableFog() {
        fogState.fog.setDisabled();
    }

    
    public static void setFog(m mVar) {
        setFog(mVar.field_187351_d);
    }

    
    private static void setFog(int i2) {
        if (i2 != fogState.mode) {
            fogState.mode = i2;
            GL11.glFogi(2917, i2);
        }
    }

    
    public static void setFogDensity(float f2) {
        if (f2 != fogState.density) {
            fogState.density = f2;
            GL11.glFogf(2914, f2);
        }
    }

    
    public static void setFogStart(float f2) {
        if (f2 != fogState.start) {
            fogState.start = f2;
            GL11.glFogf(2915, f2);
        }
    }

    
    public static void setFogEnd(float f2) {
        if (f2 != fogState.end) {
            fogState.end = f2;
            GL11.glFogf(2916, f2);
        }
    }

    
    public static void glFog(int i2, FloatBuffer floatBuffer) {
        GL11.glFog(i2, floatBuffer);
    }

    
    public static void glFogi(int i2, int i3) {
        GL11.glFogi(i2, i3);
    }

    
    
    public enum i {
        FRONT(1028),
        BACK(1029),
        FRONT_AND_BACK(1032);


        
        public final int field_187328_d;

        i(int i) {
            this.field_187328_d = i;
        }
    }

    
    public static void enableCull() {
        cullState.cullFace.setEnabled();
    }

    
    public static void disableCull() {
        cullState.cullFace.setDisabled();
    }

    
    public static void cullFace(i iVar) {
        cullFace(iVar.field_187328_d);
    }

    
    private static void cullFace(int i2) {
        if (i2 != cullState.mode) {
            cullState.mode = i2;
            GL11.glCullFace(i2);
        }
    }

    
    public static void glPolygonMode(int i2, int i3) {
        GL11.glPolygonMode(i2, i3);
    }

    
    public static void enablePolygonOffset() {
        polygonOffsetState.polygonOffsetFill.setEnabled();
    }

    
    public static void disablePolygonOffset() {
        polygonOffsetState.polygonOffsetFill.setDisabled();
    }

    
    public static void doPolygonOffset(float f2, float f3) {
        if (f2 != polygonOffsetState.factor || f3 != polygonOffsetState.units) {
            polygonOffsetState.factor = f2;
            polygonOffsetState.units = f3;
            GL11.glPolygonOffset(f2, f3);
        }
    }

    
    
    public enum o {
        AND(5377),
        AND_INVERTED(5380),
        AND_REVERSE(5378),
        CLEAR(5376),
        COPY(5379),
        COPY_INVERTED(5388),
        EQUIV(5385),
        INVERT(5386),
        NAND(5390),
        NOOP(5381),
        NOR(5384),
        OR(5383),
        OR_INVERTED(5389),
        OR_REVERSE(5387),
        SET(5391),
        XOR(5382);


        
        public final int field_187370_q;

        o(int i) {
            this.field_187370_q = i;
        }
    }

    
    public static void enableColorLogic() {
        colorLogicState.colorLogicOp.setEnabled();
    }

    
    public static void disableColorLogic() {
        colorLogicState.colorLogicOp.setDisabled();
    }

    
    public static void colorLogicOp(o oVar) {
        colorLogicOp(oVar.field_187370_q);
    }

    
    public static void colorLogicOp(int i2) {
        if (i2 != colorLogicState.opcode) {
            colorLogicState.opcode = i2;
            GL11.glLogicOp(i2);
        }
    }

    
    public static void enableTexGenCoord(u uVar) {
        texGenCoord(uVar).textureGen.setEnabled();
    }

    
    public static void disableTexGenCoord(u uVar) {
        texGenCoord(uVar).textureGen.setDisabled();
    }

    
    public static void texGen(u uVar, int i2) {
        v texGenCoord = texGenCoord(uVar);
        if (i2 != texGenCoord.param) {
            texGenCoord.param = i2;
            GL11.glTexGeni(texGenCoord.coord, 9472, i2);
        }
    }

    
    public static void texGen(u uVar, int i2, FloatBuffer floatBuffer) {
        GL11.glTexGen(texGenCoord(uVar).coord, i2, floatBuffer);
    }

    
    private static v texGenCoord(u uVar) {
        switch (uVar) {
            case S:
                return texGenState.s;
            case T:
                return texGenState.t;
            case R:
                return texGenState.r;
            case Q:
                return texGenState.q;
            default:
                return texGenState.s;
        }
    }

    
    public static void setActiveTexture(int i2) {
        if (activeTextureUnit != i2 - OpenGlHelper.defaultTexUnit) {
            activeTextureUnit = i2 - OpenGlHelper.defaultTexUnit;
            OpenGlHelper.setActiveTexture(i2);
        }
    }

    
    public static void enableTexture2D() {
        textureState[activeTextureUnit].texture2DState.setEnabled();
    }

    
    public static void disableTexture2D() {
        textureState[activeTextureUnit].texture2DState.setDisabled();
    }

    
    public static void glTexEnv(int i2, int i3, FloatBuffer floatBuffer) {
        GL11.glTexEnv(i2, i3, floatBuffer);
    }

    
    public static void glTexEnvi(int i2, int i3, int i4) {
        GL11.glTexEnvi(i2, i3, i4);
    }

    
    public static void glTexEnvf(int i2, int i3, float f2) {
        GL11.glTexEnvf(i2, i3, f2);
    }

    
    public static void glTexParameterf(int i2, int i3, float f2) {
        GL11.glTexParameterf(i2, i3, f2);
    }

    
    public static void glTexParameteri(int i2, int i3, int i4) {
        GL11.glTexParameteri(i2, i3, i4);
    }

    
    public static int glGetTexLevelParameteri(int i2, int i3, int i4) {
        return GL11.glGetTexLevelParameteri(i2, i3, i4);
    }

    
    public static int generateTexture() {
        return GL11.glGenTextures();
    }

    
    public static void deleteTexture(int i2) {
        GL11.glDeleteTextures(i2);
        for (TextureState textureStateVar : textureState) {
            if (textureStateVar.textureName == i2) {
                textureStateVar.textureName = -1;
            }
        }
    }

    
    public static void bindTexture(int i2) {
        if (i2 != textureState[activeTextureUnit].textureName) {
            textureState[activeTextureUnit].textureName = i2;
            GL11.glBindTexture(3553, i2);
        }
    }

    
    public static void glTexImage2D(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, IntBuffer intBuffer) {
        GL11.glTexImage2D(i2, i3, i4, i5, i6, i7, i8, i9, intBuffer);
    }

    
    public static void glTexSubImage2D(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, IntBuffer intBuffer) {
        GL11.glTexSubImage2D(i2, i3, i4, i5, i6, i7, i8, i9, intBuffer);
    }

    
    public static void glCopyTexSubImage2D(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        GL11.glCopyTexSubImage2D(i2, i3, i4, i5, i6, i7, i8, i9);
    }

    
    public static void glGetTexImage(int i2, int i3, int i4, int i5, IntBuffer intBuffer) {
        GL11.glGetTexImage(i2, i3, i4, i5, intBuffer);
    }

    
    public static void enableNormalize() {
        normalizeState.setEnabled();
    }

    
    public static void disableNormalize() {
        normalizeState.setDisabled();
    }

    
    public static void shadeModel(int i2) {
        if (i2 != activeShadeModel) {
            activeShadeModel = i2;
            GL11.glShadeModel(i2);
        }
    }

    
    public static void enableRescaleNormal() {
        rescaleNormalState.setEnabled();
    }

    
    public static void disableRescaleNormal() {
        rescaleNormalState.setDisabled();
    }

    
    public static void viewport(int i2, int i3, int i4, int i5) {
        GL11.glViewport(i2, i3, i4, i5);
    }

    
    public static void colorMask(boolean z, boolean z2, boolean z3, boolean z4) {
        if (z != colorMaskState.red || z2 != colorMaskState.green || z3 != colorMaskState.blue || z4 != colorMaskState.alpha) {
            colorMaskState.red = z;
            colorMaskState.green = z2;
            colorMaskState.blue = z3;
            colorMaskState.alpha = z4;
            GL11.glColorMask(z, z2, z3, z4);
        }
    }

    
    public static void clearDepth(double d2) {
        if (d2 != clearState.depth) {
            clearState.depth = d2;
            GL11.glClearDepth(d2);
        }
    }

    
    public static void clearColor(float f2, float f3, float f4, float f5) {
        if (f2 != clearState.color.red || f3 != clearState.color.green || f4 != clearState.color.blue || f5 != clearState.color.alpha) {
            clearState.color.red = f2;
            clearState.color.green = f3;
            clearState.color.blue = f4;
            clearState.color.alpha = f5;
            GL11.glClearColor(f2, f3, f4, f5);
        }
    }

    
    public static void clear(int i2) {
        GL11.glClear(i2);
    }

    
    public static void matrixMode(int i2) {
        GL11.glMatrixMode(i2);
    }

    
    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }

    
    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    
    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    
    public static void getFloat(int i2, FloatBuffer floatBuffer) {
        GL11.glGetFloat(i2, floatBuffer);
    }

    
    public static void ortho(double d2, double d3, double d4, double d5, double d6, double d7) {
        GL11.glOrtho(d2, d3, d4, d5, d6, d7);
    }

    
    public static void rotate(float f2, float f3, float f4, float f5) {
        GL11.glRotatef(f2, f3, f4, f5);
    }

    
    public static void scale(float f2, float f3, float f4) {
        GL11.glScalef(f2, f3, f4);
    }

    
    public static void scale(double d2, double d3, double d4) {
        GL11.glScaled(d2, d3, d4);
    }

    
    public static void translate(float f2, float f3, float f4) {
        GL11.glTranslatef(f2, f3, f4);
    }

    
    public static void translate(double d2, double d3, double d4) {
        GL11.glTranslated(d2, d3, d4);
    }

    
    public static void multMatrix(FloatBuffer floatBuffer) {
        GL11.glMultMatrix(floatBuffer);
    }

    
    public static void rotate(Quaternion quaternion) {
        multMatrix(quatToGlMatrix(BUF_FLOAT_16, quaternion));
    }

    
    public static FloatBuffer quatToGlMatrix(FloatBuffer floatBuffer, Quaternion quaternion) {
        floatBuffer.clear();
        float f2 = quaternion.x * quaternion.x;
        float f3 = quaternion.x * quaternion.y;
        float f4 = quaternion.x * quaternion.z;
        float f5 = quaternion.x * quaternion.w;
        float f6 = quaternion.y * quaternion.y;
        float f7 = quaternion.y * quaternion.z;
        float f8 = quaternion.y * quaternion.w;
        float f9 = quaternion.z * quaternion.z;
        float f10 = quaternion.z * quaternion.w;
        floatBuffer.put(1.0f - (2.0f * (f6 + f9)));
        floatBuffer.put(2.0f * (f3 + f10));
        floatBuffer.put(2.0f * (f4 - f8));
        floatBuffer.put(0.0f);
        floatBuffer.put(2.0f * (f3 - f10));
        floatBuffer.put(1.0f - (2.0f * (f2 + f9)));
        floatBuffer.put(2.0f * (f7 + f5));
        floatBuffer.put(0.0f);
        floatBuffer.put(2.0f * (f4 + f8));
        floatBuffer.put(2.0f * (f7 - f5));
        floatBuffer.put(1.0f - (2.0f * (f2 + f6)));
        floatBuffer.put(0.0f);
        floatBuffer.put(0.0f);
        floatBuffer.put(0.0f);
        floatBuffer.put(0.0f);
        floatBuffer.put(1.0f);
        floatBuffer.rewind();
        return floatBuffer;
    }

    
    public static void color(float f2, float f3, float f4, float f5) {
        if (f2 != colorState.red || f3 != colorState.green || f4 != colorState.blue || f5 != colorState.alpha) {
            colorState.red = f2;
            colorState.green = f3;
            colorState.blue = f4;
            colorState.alpha = f5;
            GL11.glColor4f(f2, f3, f4, f5);
        }
    }

    
    public static void color(float f2, float f3, float f4) {
        color(f2, f3, f4, 1.0f);
    }

    
    public static void glTexCoord2f(float f2, float f3) {
        GL11.glTexCoord2f(f2, f3);
    }

    
    public static void glVertex3f(float f2, float f3, float f4) {
        GL11.glVertex3f(f2, f3, f4);
    }

    
    public static void resetColor() {
        ColorState colorStateVar = colorState;
        ColorState colorStateVar2 = colorState;
        ColorState colorStateVar3 = colorState;
        colorState.alpha = -1.0f;
        colorStateVar3.blue = -1.0f;
        colorStateVar2.green = -1.0f;
        colorStateVar.red = -1.0f;
    }

    
    public static void glNormalPointer(int i2, int i3, ByteBuffer byteBuffer) {
        GL11.glNormalPointer(i2, i3, byteBuffer);
    }

    
    public static void glTexCoordPointer(int i2, int i3, int i4, int i5) {
        GL11.glTexCoordPointer(i2, i3, i4, i5);
    }

    
    public static void glTexCoordPointer(int i2, int i3, int i4, ByteBuffer byteBuffer) {
        GL11.glTexCoordPointer(i2, i3, i4, byteBuffer);
    }

    
    public static void glVertexPointer(int i2, int i3, int i4, int i5) {
        GL11.glVertexPointer(i2, i3, i4, i5);
    }

    
    public static void glVertexPointer(int i2, int i3, int i4, ByteBuffer byteBuffer) {
        GL11.glVertexPointer(i2, i3, i4, byteBuffer);
    }

    
    public static void glColorPointer(int i2, int i3, int i4, int i5) {
        GL11.glColorPointer(i2, i3, i4, i5);
    }

    
    public static void glColorPointer(int i2, int i3, int i4, ByteBuffer byteBuffer) {
        GL11.glColorPointer(i2, i3, i4, byteBuffer);
    }

    
    public static void glDisableClientState(int i2) {
        GL11.glDisableClientState(i2);
    }

    
    public static void glEnableClientState(int i2) {
        GL11.glEnableClientState(i2);
    }

    
    public static void glBegin(int i2) {
        GL11.glBegin(i2);
    }

    
    public static void glEnd() {
        GL11.glEnd();
    }

    
    public static void glDrawArrays(int i2, int i3, int i4) {
        GL11.glDrawArrays(i2, i3, i4);
    }

    
    public static void glLineWidth(float f2) {
        GL11.glLineWidth(f2);
    }

    
    public static void callList(int i2) {
        GL11.glCallList(i2);
    }

    
    public static void glDeleteLists(int i2, int i3) {
        GL11.glDeleteLists(i2, i3);
    }

    
    public static void glNewList(int i2, int i3) {
        GL11.glNewList(i2, i3);
    }

    
    public static void glEndList() {
        GL11.glEndList();
    }

    
    public static int glGenLists(int i2) {
        return GL11.glGenLists(i2);
    }

    
    public static void glPixelStorei(int i2, int i3) {
        GL11.glPixelStorei(i2, i3);
    }

    
    public static void glReadPixels(int i2, int i3, int i4, int i5, int i6, int i7, IntBuffer intBuffer) {
        GL11.glReadPixels(i2, i3, i4, i5, i6, i7, intBuffer);
    }

    
    public static int glGetError() {
        return GL11.glGetError();
    }

    
    public static String glGetString(int i2) {
        return GL11.glGetString(i2);
    }

    
    public static void glGetInteger(int i2, IntBuffer intBuffer) {
        GL11.glGetInteger(i2, intBuffer);
    }

    
    public static int glGetInteger(int i2) {
        return GL11.glGetInteger(i2);
    }

    
    
    public static class TextureState {

        
        public LightState texture2DState;

        
        public int textureName;

        private TextureState() {
            this.texture2DState = new LightState(3553);
            this.textureName = 0;
        }
    }

    
    
    public static class AplhaState {

        
        public LightState alphaTest;

        
        public int func;

        
        public float ref;

        private AplhaState() {
            this.alphaTest = new LightState(3008);
            this.func = 519;
            this.ref = -1.0f;
        }
    }

    
    
    public static class ColorMaterialState {

        
        public LightState colorMaterial;

        
        public int face;

        
        public int mode;

        private ColorMaterialState() {
            this.colorMaterial = new LightState(2903);
            this.face = 1032;
            this.mode = 5634;
        }


    }

    
    
    public static class BlendState {

        
        public LightState blend;

        
        public int srcFactor;

        
        public int dstFactor;

        
        public int srcFactorAlpha;

        
        public int dstFactorAlpha;

        private BlendState() {
            this.blend = new LightState(3042);
            this.srcFactor = 1;
            this.dstFactor = 0;
            this.srcFactorAlpha = 1;
            this.dstFactorAlpha = 0;
        }


    }

    
    
    public static class DepthState {

        
        public LightState depthTest;

        
        public boolean maskEnabled;

        
        public int depthFunc;

        private DepthState() {
            this.depthTest = new LightState(2929);
            this.maskEnabled = true;
            this.depthFunc = 513;
        }


    }

    
    
    public static class FogState {

        
        public LightState fog;

        
        public int mode;

        
        public float density;

        
        public float start;

        
        public float end;

        private FogState() {
            this.fog = new LightState(2912);
            this.mode = 2048;
            this.density = 1.0f;
            this.start = 0.0f;
            this.end = 1.0f;
        }


    }

    
    
    public static class CullState {

        
        public LightState cullFace;

        
        public int mode;

        private CullState() {
            this.cullFace = new LightState(2884);
            this.mode = 1029;
        }


    }

    
    
    public static class PolyOffsetState {

        
        public LightState polygonOffsetFill;

        
        public LightState polygonOffsetLine;

        
        public float factor;

        
        public float units;

        private PolyOffsetState() {
            this.polygonOffsetFill = new LightState(32823);
            this.polygonOffsetLine = new LightState(10754);
            this.factor = 0.0f;
            this.units = 0.0f;
        }

        

    }

    
    
    public static class ColorLogicState {

        
        public LightState colorLogicOp;

        
        public int opcode;

        private ColorLogicState() {
            this.colorLogicOp = new LightState(3058);
            this.opcode = 5379;
        }

        

    }

    
    
    public static class ClearState {

        
        public double depth;

        
        public ColorState color;

        
        public int field_179204_c;

        private ClearState() {
            this.depth = 1.0d;
            this.color = new ColorState(0.0f, 0.0f, 0.0f, 0.0f);
            this.field_179204_c = 0;
        }

        

    }

    
    
    public static class s {

        
        public int field_179081_a;

        
        public int field_179079_b;

        
        public int field_179080_c;

        private s() {
            this.field_179081_a = 519;
            this.field_179079_b = 0;
            this.field_179080_c = -1;
        }

        

    }

    
    
    static class StensilState {

        
        public s field_179078_a;

        
        public int field_179076_b;

        
        public int field_179077_c;

        
        public int field_179074_d;

        
        public int field_179075_e;

        private StensilState() {
            this.field_179078_a = new s();
            this.field_179076_b = -1;
            this.field_179077_c = 7680;
            this.field_179074_d = 7680;
            this.field_179075_e = 7680;
        }

        

    }

    
    
    public static class TexGenState {

        
        public v s;

        
        public v t;

        
        public v r;

        
        public v q;

        private TexGenState() {
            this.s = new v(8192, 3168);
            this.t = new v(8193, 3169);
            this.r = new v(8194, 3170);
            this.q = new v(8195, 3171);
        }

        

    }

    
    
    public static class v {

        
        public LightState textureGen;

        
        public int coord;

        
        public int param = -1;

        public v(int i, int i2) {
            this.coord = i;
            this.textureGen = new LightState(i2);
        }
    }

    
    
    public static class ColorMaskState {

        
        public boolean red;

        
        public boolean green;

        
        public boolean blue;

        
        public boolean alpha;

        private ColorMaskState() {
            this.red = true;
            this.green = true;
            this.blue = true;
            this.alpha = true;
        }

        

    }

    
    
    public static class ColorState {

        
        public float red;

        
        public float green;

        
        public float blue;

        
        public float alpha;

        public ColorState() {
            this.red = 1.0f;
            this.green = 1.0f;
            this.blue = 1.0f;
            this.alpha = 1.0f;
        }

        public ColorState(float f, float f2, float f3, float f4) {
            this.red = 1.0f;
            this.green = 1.0f;
            this.blue = 1.0f;
            this.alpha = 1.0f;
            this.red = f;
            this.green = f2;
            this.blue = f3;
            this.alpha = f4;
        }
    }

    
    
    public static class LightState {

        
        private final int capability;

        
        private boolean currentState = false;

        public LightState(int i) {
            this.capability = i;
        }

        
        public void setDisabled() {
            setState(false);
        }

        
        public void setEnabled() {
            setState(true);
        }

        
        public void setState(boolean z) {
            if (z != this.currentState) {
                this.currentState = z;
                if (z) {
                    GL11.glEnable(this.capability);
                } else {
                    GL11.glDisable(this.capability);
                }
            }
        }
    }

    
    
    public enum r {
        CONSTANT_ALPHA(32771),
        CONSTANT_COLOR(32769),
        DST_ALPHA(772),
        DST_COLOR(774),
        ONE(1),
        ONE_MINUS_CONSTANT_ALPHA(32772),
        ONE_MINUS_CONSTANT_COLOR(32770),
        ONE_MINUS_DST_ALPHA(773),
        ONE_MINUS_DST_COLOR(775),
        ONE_MINUS_SRC_ALPHA(771),
        ONE_MINUS_SRC_COLOR(769),
        SRC_ALPHA(770),
        SRC_ALPHA_SATURATE(776),
        SRC_COLOR(768),
        ZERO(0);


        
        public final int field_187395_p;

        r(int i) {
            this.field_187395_p = i;
        }
    }

    
    
    public enum l {
        CONSTANT_ALPHA(32771),
        CONSTANT_COLOR(32769),
        DST_ALPHA(772),
        DST_COLOR(774),
        ONE(1),
        ONE_MINUS_CONSTANT_ALPHA(32772),
        ONE_MINUS_CONSTANT_COLOR(32770),
        ONE_MINUS_DST_ALPHA(773),
        ONE_MINUS_DST_COLOR(775),
        ONE_MINUS_SRC_ALPHA(771),
        ONE_MINUS_SRC_COLOR(769),
        SRC_ALPHA(770),
        SRC_COLOR(768),
        ZERO(0);


        
        public final int field_187345_o;

        l(int i) {
            this.field_187345_o = i;
        }
    }

    
    public static void enableBlendProfile(BlendProfile blendProfileVar) {
        blendProfileVar.func_187373_a();
    }

    
    public static void disableBlendProfile(BlendProfile blendProfileVar) {
        blendProfileVar.func_187374_b();
    }

    
    
    public enum BlendProfile {
        DEFAULT { 

            @Override 
            
            public void func_187373_a() {
                GlStateManager.disableAlpha();
                GlStateManager.alphaFunc(519, 0.0f);
                GlStateManager.disableLighting();
                GlStateManager.glLightModel(2899, RenderHelper.setColorBuffer(0.2f, 0.2f, 0.2f, 1.0f));
                for (int i = 0; i < 8; i++) {
                    GlStateManager.disableLight(i);
                    GlStateManager.glLight(16384 + i, 4608, RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
                    GlStateManager.glLight(16384 + i, 4611, RenderHelper.setColorBuffer(0.0f, 0.0f, 1.0f, 0.0f));
                    if (i == 0) {
                        GlStateManager.glLight(16384 + i, 4609, RenderHelper.setColorBuffer(1.0f, 1.0f, 1.0f, 1.0f));
                        GlStateManager.glLight(16384 + i, 4610, RenderHelper.setColorBuffer(1.0f, 1.0f, 1.0f, 1.0f));
                    } else {
                        GlStateManager.glLight(16384 + i, 4609, RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
                        GlStateManager.glLight(16384 + i, 4610, RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
                    }
                }
                GlStateManager.disableColorMaterial();
                GlStateManager.colorMaterial(1032, 5634);
                GlStateManager.disableDepth();
                GlStateManager.depthFunc(513);
                GlStateManager.depthMask(true);
                GlStateManager.disableBlend();
                GlStateManager.blendFunc(r.ONE, l.ZERO);
                GlStateManager.tryBlendFuncSeparate(r.ONE, l.ZERO, r.ONE, l.ZERO);
                GlStateManager.glBlendEquation(32774);
                GlStateManager.disableFog();
                GlStateManager.glFogi(2917, 2048);
                GlStateManager.setFogDensity(1.0f);
                GlStateManager.setFogStart(0.0f);
                GlStateManager.setFogEnd(1.0f);
                GlStateManager.glFog(2918, RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 0.0f));
                if (GLContext.getCapabilities().GL_NV_fog_distance) {
                    GlStateManager.glFogi(2917, 34140);
                }
                GlStateManager.doPolygonOffset(0.0f, 0.0f);
                GlStateManager.disableColorLogic();
                GlStateManager.colorLogicOp(5379);
                GlStateManager.disableTexGenCoord(u.S);
                GlStateManager.texGen(u.S, 9216);
                GlStateManager.texGen(u.S, 9474, RenderHelper.setColorBuffer(1.0f, 0.0f, 0.0f, 0.0f));
                GlStateManager.texGen(u.S, 9217, RenderHelper.setColorBuffer(1.0f, 0.0f, 0.0f, 0.0f));
                GlStateManager.disableTexGenCoord(u.T);
                GlStateManager.texGen(u.T, 9216);
                GlStateManager.texGen(u.T, 9474, RenderHelper.setColorBuffer(0.0f, 1.0f, 0.0f, 0.0f));
                GlStateManager.texGen(u.T, 9217, RenderHelper.setColorBuffer(0.0f, 1.0f, 0.0f, 0.0f));
                GlStateManager.disableTexGenCoord(u.R);
                GlStateManager.texGen(u.R, 9216);
                GlStateManager.texGen(u.R, 9474, RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 0.0f));
                GlStateManager.texGen(u.R, 9217, RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 0.0f));
                GlStateManager.disableTexGenCoord(u.Q);
                GlStateManager.texGen(u.Q, 9216);
                GlStateManager.texGen(u.Q, 9474, RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 0.0f));
                GlStateManager.texGen(u.Q, 9217, RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 0.0f));
                GlStateManager.setActiveTexture(0);
                GlStateManager.glTexParameteri(3553, 10240, 9729);
                GlStateManager.glTexParameteri(3553, 10241, 9986);
                GlStateManager.glTexParameteri(3553, 10242, 10497);
                GlStateManager.glTexParameteri(3553, 10243, 10497);
                GlStateManager.glTexParameteri(3553, 33085, 1000);
                GlStateManager.glTexParameteri(3553, 33083, 1000);
                GlStateManager.glTexParameteri(3553, 33082, -1000);
                GlStateManager.glTexParameterf(3553, 34049, 0.0f);
                GlStateManager.glTexEnvi(8960, 8704, 8448);
                GlStateManager.glTexEnv(8960, 8705, RenderHelper.setColorBuffer(0.0f, 0.0f, 0.0f, 0.0f));
                GlStateManager.glTexEnvi(8960, 34161, 8448);
                GlStateManager.glTexEnvi(8960, 34162, 8448);
                GlStateManager.glTexEnvi(8960, 34176, 5890);
                GlStateManager.glTexEnvi(8960, 34177, 34168);
                GlStateManager.glTexEnvi(8960, 34178, 34166);
                GlStateManager.glTexEnvi(8960, 34184, 5890);
                GlStateManager.glTexEnvi(8960, 34185, 34168);
                GlStateManager.glTexEnvi(8960, 34186, 34166);
                GlStateManager.glTexEnvi(8960, 34192, 768);
                GlStateManager.glTexEnvi(8960, 34193, 768);
                GlStateManager.glTexEnvi(8960, 34194, 770);
                GlStateManager.glTexEnvi(8960, 34200, 770);
                GlStateManager.glTexEnvi(8960, 34201, 770);
                GlStateManager.glTexEnvi(8960, 34202, 770);
                GlStateManager.glTexEnvf(8960, 34163, 1.0f);
                GlStateManager.glTexEnvf(8960, 3356, 1.0f);
                GlStateManager.disableNormalize();
                GlStateManager.shadeModel(7425);
                GlStateManager.disableRescaleNormal();
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.clearDepth(1.0d);
                GlStateManager.glLineWidth(1.0f);
                GlStateManager.glNormal3f(0.0f, 0.0f, 1.0f);
                GlStateManager.glPolygonMode(1028, 6914);
                GlStateManager.glPolygonMode(1029, 6914);
            }

            @Override 
            
            public void func_187374_b() {
            }
        },
        PLAYER_SKIN { 

            @Override 
            
            public void func_187373_a() {
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            }

            @Override 
            
            public void func_187374_b() {
                GlStateManager.disableBlend();
            }
        },
        TRANSPARENT_MODEL { 

            @Override 
            
            public void func_187373_a() {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 0.15f);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(r.SRC_ALPHA, l.ONE_MINUS_SRC_ALPHA);
                GlStateManager.alphaFunc(516, 0.003921569f);
            }

            @Override 
            
            public void func_187374_b() {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1f);
                GlStateManager.depthMask(true);
            }
        };

        
        public abstract void func_187373_a();

        
        public abstract void func_187374_b();
    }

}
