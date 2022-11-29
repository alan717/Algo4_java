package com.tencent.mobileqq.pb;

public final class PBUInt64Field extends PBPrimitiveField<Long> {
    public static final PBUInt64Field __repeatHelper__ = new PBUInt64Field(0, false);
    private long value = 0;

    public PBUInt64Field(long j, boolean z) {
        set(j, z);
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void clear(Object obj) {
        if (obj instanceof Long) {
            this.value = ((Long) obj).longValue();
        } else {
            this.value = 0;
        }
        setHasFlag(false);
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public int computeSize(int i) {
        if (has()) {
            return CodedOutputStreamMicro.computeUInt64Size(i, this.value);
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int computeSizeDirectly(int i, Long l) {
        return CodedOutputStreamMicro.computeUInt64Size(i, l.longValue());
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public void copyFrom(PBField<Long> pBField) {
        PBUInt64Field pBUInt64Field = (PBUInt64Field) pBField;
        set(pBUInt64Field.value, pBUInt64Field.has());
    }

    public long get() {
        return this.value;
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
        this.value = codedInputStreamMicro.readUInt64();
        setHasFlag(true);
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public Long readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
        return Long.valueOf(codedInputStreamMicro.readUInt64());
    }

    public void set(long j) {
        set(j, true);
    }

    public void set(long j, boolean z) {
        this.value = j;
        setHasFlag(z);
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
        if (has()) {
            codedOutputStreamMicro.writeUInt64(i, this.value);
        }
    }

    /* access modifiers changed from: protected */
    public void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Long l) {
        codedOutputStreamMicro.writeUInt64(i, l.longValue());
    }
}