package com.tencent.mobileqq.pb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class PBRepeatField<T> extends PBField<List<T>> {
    private final PBField<T> helper;
    private List<T> value = Collections.emptyList();

    public PBRepeatField(PBField<T> pBField) {
        this.helper = pBField;
    }

    public void add(T t) {
        get().add(t);
    }

    public void addAll(Collection<T> collection) {
        get().addAll(collection);
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void clear(Object obj) {
        this.value = Collections.emptyList();
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public int computeSize(int i) {
        return computeSizeDirectly(i, (List) this.value);
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public /* bridge */ /* synthetic */ int computeSizeDirectly(int i, Object obj) {
        return computeSizeDirectly(i, (List) ((List) obj));
    }

    /* access modifiers changed from: protected */
    public int computeSizeDirectly(int i, List<T> list) {
        int i2 = 0;
        for (T t : list) {
            i2 = this.helper.computeSizeDirectly(i, t) + i2;
        }
        return i2;
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public void copyFrom(PBField<List<T>> pBField) {
        PBRepeatField pBRepeatField = (PBRepeatField) pBField;
        if (pBRepeatField.isEmpty()) {
            this.value = Collections.emptyList();
            return;
        }
        List<T> list = get();
        list.clear();
        list.addAll(pBRepeatField.value);
    }

    public T get(int i) {
        return this.value.get(i);
    }

    public List<T> get() {
        if (this.value == Collections.emptyList()) {
            this.value = new ArrayList();
        }
        return this.value;
    }

    public boolean has() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
        add(this.helper.readFromDirectly(codedInputStreamMicro));
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public List<T> readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
        throw new RuntimeException("PBRepeatField not support readFromDirectly method.");
    }

    public void remove(int i) {
        get().remove(i);
    }

    public void set(int i, T t) {
        this.value.set(i, t);
    }

    public void set(List<T> list) {
        this.value = list;
    }

    public int size() {
        return this.value.size();
    }

    @Override // com.tencent.mobileqq.pb.PBField
    public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
        writeToDirectly(codedOutputStreamMicro, i, (List) this.value);
    }

    /* access modifiers changed from: protected */
    @Override // com.tencent.mobileqq.pb.PBField
    public /* bridge */ /* synthetic */ void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, Object obj) {
        writeToDirectly(codedOutputStreamMicro, i, (List) ((List) obj));
    }

    /* access modifiers changed from: protected */
    public void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, List<T> list) {
        for (T t : list) {
            this.helper.writeToDirectly(codedOutputStreamMicro, i, t);
        }
    }
}