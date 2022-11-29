package com.tencent.mobileqq.pb;

import com.tencent.mobileqq.pb.MessageMicro;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class MessageMicro<T extends MessageMicro<T>> extends PBPrimitiveField<T> {
    private FieldMap _fields = null;
    private int cachedSize = -1;

    public final class FieldMap {
        private Object[] defaultValues;
        private Field[] fields;
        private int[] tags;

        FieldMap(int[] iArr, String[] strArr, Object[] objArr, Class<?> cls) {
            this.tags = iArr;
            this.defaultValues = objArr;
            this.fields = new Field[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                try {
                    this.fields[i] = cls.getField(strArr[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void clear(MessageMicro<?> messageMicro) {
            for (int i = 0; i < this.tags.length; i++) {
                ((PBField) this.fields[i].get(messageMicro)).clear(this.defaultValues[i]);
            }
        }

        /* access modifiers changed from: package-private */
        public <U extends MessageMicro<U>> void copyFields(U u, U u2) {
            for (int i = 0; i < this.tags.length; i++) {
                Field field = this.fields[i];
                ((PBField) field.get(u)).copyFrom((PBField) field.get(u2));
            }
        }

        /* access modifiers changed from: package-private */
        public Field get(int i) {
            int binarySearch = Arrays.binarySearch(this.tags, i);
            if (binarySearch < 0) {
                return null;
            }
            return this.fields[binarySearch];
        }

        /* access modifiers changed from: package-private */
        public int getSerializedSize(MessageMicro<?> messageMicro) {
            int i = 0;
            for (int i2 = 0; i2 < this.tags.length; i2++) {
                i += ((PBField) this.fields[i2].get(messageMicro)).computeSize(WireFormatMicro.getTagFieldNumber(this.tags[i2]));
            }
            return i;
        }

        public boolean readFieldFrom(CodedInputStreamMicro codedInputStreamMicro, int i, MessageMicro<?> messageMicro) {
            int binarySearch = Arrays.binarySearch(this.tags, i);
            if (binarySearch < 0) {
                return false;
            }
            ((PBField) this.fields[binarySearch].get(messageMicro)).readFrom(codedInputStreamMicro);
            return true;
        }

        /* access modifiers changed from: package-private */
        public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, MessageMicro<?> messageMicro) {
            for (int i = 0; i < this.tags.length; i++) {
                ((PBField) this.fields[i].get(messageMicro)).writeTo(codedOutputStreamMicro, WireFormatMicro.getTagFieldNumber(this.tags[i]));
            }
        }
    }

    private final FieldMap getFieldMap() {
        if (this._fields == null) {
            try {
                Field declaredField = getClass().getDeclaredField("__fieldMap__");
                declaredField.setAccessible(true);
                this._fields = (FieldMap) declaredField.get(this);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (SecurityException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            }
        }
        return this._fields;
    }

    public static FieldMap initFieldMap(int[] iArr, String[] strArr, Object[] objArr, Class<?> cls) {
        return new FieldMap(iArr, strArr, objArr, cls);
    }

    public static void main(String[] strArr) {
    }

    public static final <T extends MessageMicro<T>> T mergeFrom(T t, byte[] bArr) {
        return (T) t.mergeFrom(bArr);
    }

    public static final byte[] toByteArray(MessageMicro<?> messageMicro) {
        return messageMicro.toByteArray();
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void clear(Object obj) {
        try {
            getFieldMap().clear(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        setHasFlag(false);
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public int computeSize(int i) {
        if (has()) {
            return CodedOutputStreamMicro.computeMessageSize(i, this);
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int computeSizeDirectly(int i, T t) {
        return CodedOutputStreamMicro.computeMessageSize(i, t);
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public void copyFrom(PBField<T> pBField) {
        try {
            getFieldMap().copyFields(this, (MessageMicro) pBField);
            setHasFlag(((MessageMicro) pBField).has());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    public T get() {
        return this;
    }

    public final int getCachedSize() {
        return getSerializedSize();
    }

    public final int getSerializedSize() {
        int i = -1;
        try {
            i = getFieldMap().getSerializedSize(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        this.cachedSize = i;
        return i;
    }

    public final T mergeFrom(CodedInputStreamMicro codedInputStreamMicro) {
        FieldMap fieldMap = getFieldMap();
        setHasFlag(true);
        while (true) {
            int readTag = codedInputStreamMicro.readTag();
            try {
                if (!fieldMap.readFieldFrom(codedInputStreamMicro, readTag, this) && (readTag == 0 || !parseUnknownField(codedInputStreamMicro, readTag))) {
                    return this;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            }
        }
        return this;
    }

    public final T mergeFrom(byte[] bArr) {
        return mergeFrom(bArr, 0, bArr.length);
    }

    public final T mergeFrom(byte[] bArr, int i, int i2) {
        try {
            CodedInputStreamMicro newInstance = CodedInputStreamMicro.newInstance(bArr, i, i2);
            mergeFrom(newInstance);
            newInstance.checkLastTagWas(0);
            return this;
        } catch (InvalidProtocolBufferMicroException e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    /* access modifiers changed from: protected */
    public boolean parseUnknownField(CodedInputStreamMicro codedInputStreamMicro, int i) {
        return codedInputStreamMicro.skipField(i);
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
        codedInputStreamMicro.readMessage(this);
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public T readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
        try {
            T t = (T) ((MessageMicro) getClass().newInstance());
            codedInputStreamMicro.readMessage(t);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public void set(T t) {
        set(t, true);
    }

    public void set(T t, boolean z) {
        copyFrom(t);
        setHasFlag(z);
        this.cachedSize = -1;
    }

    public final void toByteArray(byte[] bArr, int i, int i2) {
        try {
            CodedOutputStreamMicro newInstance = CodedOutputStreamMicro.newInstance(bArr, i, i2);
            writeTo(newInstance);
            newInstance.checkNoSpaceLeft();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).");
        }
    }

    public final byte[] toByteArray() {
        byte[] bArr = new byte[getSerializedSize()];
        toByteArray(bArr, 0, bArr.length);
        return bArr;
    }

    public final void writeTo(CodedOutputStreamMicro codedOutputStreamMicro) {
        try {
            getFieldMap().writeTo(codedOutputStreamMicro, this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
        if (has()) {
            codedOutputStreamMicro.writeMessage(i, this);
        }
    }

    /* access modifiers changed from: protected */
    public void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, T t) {
        codedOutputStreamMicro.writeMessage(i, t);
    }
}