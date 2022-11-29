package com.tencent.mobileqq.pb;

public final class PBUInt32Field extends PBPrimitiveField<Integer> {
    public static final PBUInt32Field __repeatHelper__ = new PBUInt32Field(0, false);
    private int value = 0;

    public PBUInt32Field(int i, boolean z) {
        set(i, z);
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void clear(Object obj) {
        if (obj instanceof Integer) {
            this.value = ((Integer) obj).intValue();
        } else {
            this.value = 0;
        }
        setHasFlag(false);
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public int computeSize(int i) {
        if (has()) {
            return CodedOutputStreamMicro.computeUInt32Size(i, this.value);
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int computeSizeDirectly(int i, Integer num) {
        return CodedOutputStreamMicro.computeUInt32Size(i, num.intValue());
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public void copyFrom(PBField<Integer> pBField) {
        PBUInt32Field pBUInt32Field = (PBUInt32Field) pBField;
        set(pBUInt32Field.value, pBUInt32Field.has());
    }

    public int get() {
        return this.value;
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
        this.value = codedInputStreamMicro.readUInt32();
        setHasFlag(true);
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public Integer readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
        return Integer.valueOf(codedInputStreamMicro.readUInt32());
    }

    public void set(int i) {
        set(i, true);
    }

    public void set(int i, boolean z) {
        this.value = i;
        setHasFlag(z);
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
        if (has()) {
            codedOutputStreamMicro.writeUInt32(i, this.value);
        }
    }

    /* access modifiers changed from: protected */
    public void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Integer num) {
        codedOutputStreamMicro.writeUInt32(i, num.intValue());
    }
}